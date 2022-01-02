package com.spring.api.api;

public class Event {
    private String event;
    Event(String event){
        this.event = event;
    }

    @Override
    public String toString() {
        return "event:" + event;
    }
}
