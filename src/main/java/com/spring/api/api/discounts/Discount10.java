package com.spring.api.api.discounts;

public class Discount10 {
    double price;
    public Discount10(double price) {
        this.price = price;
    }

    public double getPrice() {
        price = price-(price*0.1);
        return price;
    }
}