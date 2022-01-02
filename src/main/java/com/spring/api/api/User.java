package com.spring.api.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;
import java.lang.String;

import java.util.ArrayList;

@RequestMapping("/users")
@RestController
public class User {
    private String username;
    private String phoneNumber;
    private String email;
    private String password;
    private String type;
    public User() {
    }

    public User(@JsonProperty("username") String username, @JsonProperty("email") String email,
                @JsonProperty("password") String password, @JsonProperty("phone") String phoneNumber, @JsonProperty("type")String type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }
    @PostMapping("/login")
    public static User checkIn(@RequestBody @JsonProperty("email") String email,
                               @JsonProperty("password") String password) {
        User user = null;
        for (int i = 0; i < User.getUsersList().size(); ++i) {
            if (!User.getUsersList().get(i).getEmail().equals(email) || !User.getUsersList().get(i).getPassword().equals(password)) continue;
            user = User.getUsersList().get(i);
        }
        return user;
    }
    @PostMapping("/register")
    public static void Register(@RequestBody User user) {

        if (user.getType().equals("Driver")) {
            Admin.addToPendingList(user);
        } else {
            boolean found = false;
            for (int i = 0; i < Database.usersList.size(); ++i) {
                if (!(Database.usersList.get(i)).getEmail().equals(user.getEmail())) continue;
                found = true;
                break;
            }
            if (!found) {
                User user1 = new User(user.getUsername(), user.getEmail(), user.getPassword(), user.phoneNumber, user.getType());
                Database.usersList.add(user1);
                Rider rider = new Rider(user1.getUsername(), user1.getEmail(), user1.getPassword(), user1.phoneNumber);
                Database.riders.add(rider);
            } else {
                System.out.println(user.getUsername() + ", You Should Enter A Unique Email!");
            }
        }
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }


    @GetMapping("/getUsers")
    public static ArrayList<User> getUsersList() {
        return Database.usersList;
    }
    @GetMapping("/getRiders")
    public static ArrayList<Rider> getRidersList() {
        return Database.riders;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}