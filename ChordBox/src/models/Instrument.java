package models;

import utils.Sellable;

public class Instrument extends Item implements Sellable {
    public Instrument(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling instrument: " + getName() + " for $" + getPrice());
    }
}
