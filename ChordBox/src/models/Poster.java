package models;

import utils.Sellable;

public class Poster extends Item implements Sellable {
    public Poster(String name, double price) {
        super(name, price);
    }

    @Override
    public void sell() {
        System.out.println("Selling poster: " + getName() + " for $" + getPrice());
    }
}
