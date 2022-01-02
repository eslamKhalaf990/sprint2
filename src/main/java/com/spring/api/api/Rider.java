package com.spring.api.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.api.api.discounts.Discount10;
import com.spring.api.api.discounts.Discount5;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RequestMapping("/riders")
@RestController
public class Rider extends User implements Subject {
    public ArrayList<Notification> notificationsList = new ArrayList<>();
    private boolean firstTime = true;
    private String birthDate;
    public Rider(){

    }

    public Rider(@RequestBody @JsonProperty("username") String username, @JsonProperty("email") String email,
                 @JsonProperty("password") String password, @JsonProperty("phone") String phoneNumber) {
        super(username, email, password, phoneNumber, "Rider");
    }
    public void RequestRide(@PathVariable("index") int index, String source, String destination) {
        String n = "Rider Name: "+this.getUsername() + ", Source:  "+source + ", destination: "+destination;
        Notification request = new Notification(n);
        request.setSource(source);
        request.setRiderName(this);
        request.setDestination(destination);
        request.setRequests(Database.availableDrivers.get(index), request);
    }
    @PostMapping("/requestRide")
    public void RequestRide(@RequestBody @JsonProperty("source") String source,
                            @JsonProperty("destination") String destination) {
        String n = "Rider Name: "+this.getUsername() + ", Source:  "+source + ", destination: "+destination;
        Notification request = new Notification(n);
        request.setSource(source);
        request.setRiderName(this);
        request.setDestination(destination);
        notifyObservers(request);
    }
    @PostMapping("/{id}/requestRide")
    public void RequestRide(@PathVariable("id")String id,@RequestBody @JsonProperty("source") String source,
                            @JsonProperty("destination") String destination) {
        int i = Integer.parseInt(id);
        Rider rider = Database.riders.get(i);
        String n = "Rider Name: "+rider.getUsername() + ", Source:  "+source + ", destination: "+destination;
        Notification request = new Notification(source, destination, rider, 0);
        request.setSource(source);
        request.setRiderName(rider);
        request.setDestination(destination);
        notifyObservers(request);
    }
    @PostMapping("/acceptPrice")
    public void acceptPrice(@RequestBody @JsonProperty("price") Notification n){
        Date date = new Date();
        String time = new SimpleDateFormat("HH:mm").format(date);
        String today = new SimpleDateFormat("MM/dd").format(date);

        if(firstTime){
            Discount5 discount = new Discount5(n.getPrice());
            n.setPrice(discount.getPrice());
        }
        else if (Database.areasOnDiscount.contains(n.getDestination())){
            Discount10 discount = new Discount10(n.getPrice());
            n.setPrice(discount.getPrice());
        }
        else if(n.getNumberOfPassengers()==2){
            Discount5 discount = new Discount5(n.getPrice());
            n.setPrice(discount.getPrice());
        }
        else if(this.birthDate.equals(today)){
            Discount10 discount = new Discount10(n.getPrice());
            n.setPrice(discount.getPrice());
        }
        firstTime = false;
        String e = "User Accepted Price, Time: " + time + ", Rider Name: " +
                this.getUsername() + ", price: " + n.getPrice();
        Event event = new Event(e);
        Database.events.add(event);
    }
    public void RateDriver(int stars, Driver driver) {
        driver.rateList.add(stars);
    }
    @GetMapping("/getNotifications")
    public ArrayList<Notification> getNotificationsList() {
        return this.notificationsList;
    }
    public String toString() {
        return "Rider name: " + this.getUsername() + ", email: " + this.getEmail();
    }

    @Override
    public void notifyObservers(Notification n) {
        for(int i = 0; i<Database.drivers.size(); i++)
            Database.drivers.get(i).update(n);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }
}