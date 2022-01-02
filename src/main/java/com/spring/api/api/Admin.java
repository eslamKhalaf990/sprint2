package com.spring.api.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
@RequestMapping("/admin")
@RestController
public class Admin extends User {


    public static void addToPendingList(User user) {
        Database.pendingList.add(user);
    }
    @PostMapping("/confirmAccount")
    public void confirmRegistration(@RequestBody User user) {
        boolean found = false;
        for (int i = 0; i < Database.usersList.size(); ++i) {
            if (!(Database.usersList.get(i)).getEmail().equals(user.getEmail())) continue;
            found = true;
            break;
        }
        if (!found) {
            Database.pendingList.remove(user);
            Database.usersList.add(user);
            Driver driver = new Driver(user.getUsername(), user.getEmail(),
                    user.getPassword(), user.getPhoneNumber(), "", "");
            Database.availableDrivers.add(driver);
        }
    }
    public void addAreasOnDiscount(String area){
        Database.areasOnDiscount.add(area);
    }

    public void suspendDriver(User user) {
        Database.usersList.remove(user);
        Database.availableDrivers.remove((user));
        Database.suspendedList.add(user);
    }

    public void activateDriver(User user) {
        if (Database.suspendedList.contains(user)) {
            Database.usersList.add(user);
            Database.suspendedList.remove(user);
        }
    }
    public ArrayList<Event> showEvents(){
        return Database.events;
    }

    public static void setAdmin(Admin admin) {
        admin.setEmail("admin@gmail.com");
        admin.setPassword("111");
        Database.usersList.add(admin);
    }

    public static ArrayList<User> getPendingList() {
        return Database.pendingList;
    }
}