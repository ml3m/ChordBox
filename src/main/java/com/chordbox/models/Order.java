package com.chordbox.models;

import java.util.ArrayList;
import java.util.List;
import com.chordbox.utils.Discountable;

public class Order implements Discountable {
    private Item item;
    private List<Discount> discounts;

    public Order(Item item, List<Discount> discounts) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        this.item = item;
        this.discounts = discounts != null ? discounts : new ArrayList<>();
    }

    @Override
    public double applyDiscount() {
        double totalDiscount = 0;
        for (Discount discount : discounts) {
            totalDiscount += discount.calculateDiscount(item.getPrice());
        }

        // Ensure the final price does not go below zero
        double finalPrice = item.getPrice() - totalDiscount;
        return Math.max(finalPrice, 0);
    }

    public Item getItem() {
        return item;
    }

    public List<Discount> getDiscounts() {
        return discounts;  // Added a getter for discounts
    }
}
