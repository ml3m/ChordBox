package com.chordbox.models;

import com.chordbox.utils.Sellable;

public class Disk extends Item implements Sellable {
    public Disk(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling disk: " + getName() + " for $" + getPrice());
    }
}
