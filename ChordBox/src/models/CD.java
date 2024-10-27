package models;

import utils.Sellable;

public class CD extends Item implements Sellable {
    public CD(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling CD: " + getName() + " for $" + getPrice());
    }
}
