package com.spring.api.api;
import org.springframework.stereotype.Repository;
import java.lang.String;
import java.util.ArrayList;

@Repository("data")
public class Database {
    public static ArrayList<User> pendingList = new ArrayList<>();
    public static ArrayList<User> usersList = new ArrayList<>();
    public static ArrayList<Driver>availableDrivers = new ArrayList<>();
    public static ArrayList<User>suspendedList = new ArrayList<>();
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<String> areasOnDiscount = new ArrayList<>();
    public static ArrayList<Observer> drivers = new ArrayList<>();
    public static ArrayList<Rider> riders = new ArrayList<>();
}