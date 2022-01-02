package com.spring.api.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



@RequestMapping("/driver")
@RestController
public class Driver extends User implements Observer{

    private String nationalID;
    private String drivingLicence;
    private String favoriteArea;
    private String available;
    private String currentLocation;
    public final ArrayList<Notification> requests = new ArrayList<>();
    private final ArrayList<String> areasList = new ArrayList<>();
    private Rider rider;
    public ArrayList<Integer> rateList = new ArrayList<>();

    //Methods
    public Driver(){}
    public Driver(@RequestBody @JsonProperty("username") String username, @JsonProperty("email") String email,
                  @JsonProperty("password") String password, @JsonProperty("phone") String phoneNumber,
                  @JsonProperty("licence") String drivingLicence,@JsonProperty("nationalID") String nationalID) {
        super(username, email, password, phoneNumber, "Driver");
        this.drivingLicence = drivingLicence;
        this.nationalID = nationalID;
        Database.drivers.add(this);
    }
    public void addArea(String area) {
        this.areasList.add(area);
    }
    public void setFavoriteArea(String favoriteArea) {
        this.favoriteArea = favoriteArea;
    }
    public void approveRequest(String source, String destination, Rider rider, double price, int index) {
        this.rider = rider;
        available = "0";
        Notification n = new Notification(source, destination, rider, price);
        this.getRequests().remove(index);
        Date date = new Date();
        String time = new SimpleDateFormat("HH:mm").format(date);
        String e = "Putting Price, Time: " + time + ", Captain Name: " + this.getUsername() + ", price: " + price;
        Event event = new Event(e);
        Database.events.add(event);
        rider.notificationsList.add(n);
    }
    public void notifyIfDriverArrived(){

        Date date = new Date();
        String time = new SimpleDateFormat("HH:mm").format(date);

        String e = "Captain Arrived To the Location, Time: " + time + ", Captain Name: " + this.getUsername() +
                ", Rider Name: " + rider.getUsername();

        String e2 = "Captain Arrived To the Location, Time: " + time + ", Captain Name: " + this.getUsername();
        Notification n = new Notification(e2);

        Event event = new Event(e);
        rider.notificationsList.add(n);
        Database.events.add(event);
    }
    public void endRide(){
        available = "1";
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        String e = "Captain Arrived To the Destination, Time: " + time + ", Captain Name: " + this.getUsername() +
                ", Rider Name: " + rider.getUsername();

        Database.events.add(new Event(e));
    }
    @Override
    public void update(Notification n) {
        //if(this.available.equals("1") && getCurrentLocation().equals(n.getSource())){
              requests.add(n);
        //}
    }

    //Getter
    public ArrayList<String> getAreasList() {
        return this.areasList;
    }
    @GetMapping("getAVGRates")
    public int getAvgRating() {
        int sum = 0;
        int numberOFRatings = this.listRates().size();
        for (int i = 0; i < this.listRates().size(); ++i) {
            sum += this.listRates().get(i);
        }
        if (numberOFRatings == 0) {
            return 0;
        }
        return sum / numberOFRatings;
    }
    public String getFavoriteArea() {
        return this.favoriteArea;
    }
    public ArrayList<Integer> listRates() {
        return this.rateList;
    }
    @GetMapping("/getRequests/{id}")
    public ArrayList<Notification> getRequests(@PathVariable("id") String id) {
        int i = Integer.parseInt(id);
        return Database.availableDrivers.get(i).requests;
    }
    public ArrayList<Notification> getRequests() {
        return requests;
    }
    public String toString() {
        return "Driver name: " + this.getUsername() + ", email: " + this.getEmail() + ", average rating: " + this.getAvgRating();
    }
    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }
    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }
    public String isAvailable() {
        return available;
    }
    public String getDrivingLicence() {
        return drivingLicence;
    }
    public String getNationalID() {
        return nationalID;
    }
    @PostMapping("/setState")
    public void setAvailable(@RequestBody @JsonProperty("state") String available) {
        this.available = available;
    }
    @PostMapping("/setLocation")
    public void setCurrentLocation(@RequestBody @JsonProperty("location") String currentLocation) {
        this.currentLocation = currentLocation;
    }
    public String getCurrentLocation() {
        return currentLocation;
    }
}