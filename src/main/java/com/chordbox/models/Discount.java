package com.chordbox.models;

public class Discount {
    private double amount;
    private String type;

    public Discount(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public double calculateDiscount(double price) {
        if (type.equalsIgnoreCase("percentage")) {
            return price * (amount / 100);
        } else if (type.equalsIgnoreCase("flat")) {
            return amount;
        }
        return 0;
    }

    public String getType() {
        return type;
    }
}
