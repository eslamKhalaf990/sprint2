package com.spring.api.api;

public class Notification {
    private String source;
    private String destination;
    private Rider riderName;
    private double price;
    private final String notification;
    private int numberOfPassengers;
    Notification(String notification){
        this.notification = notification;
    }

    public Notification(String source, String destination, Rider riderName, double price) {
        this.notification = "source: " + source +
                ", destination: " + destination +
                ", rider name: " + riderName.getUsername() +
                ", price: " + price;
        this.source = source;
        this.destination = destination;
        this.riderName = riderName;
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setRiderName(Rider riderName) {
        this.riderName = riderName;
    }
    public String getDestination() {
        return destination;
    }
    public Rider getRider() {
        return riderName;
    }
    public String getSource() {
        return source;
    }
    public void setRequests(Driver driver, Notification requests) {
        driver.getRequests().add(requests);
    }
    @Override
    public String toString() {
        return notification;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
}