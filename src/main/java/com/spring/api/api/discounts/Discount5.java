package com.spring.api.api.discounts;

public class Discount5 {
    double price;
    public Discount5(double price) {
        this.price = price;
    }

    public double getPrice() {
        price = price-(price*0.05);
        return price;
    }
}
