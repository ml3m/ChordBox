public class Order implements Discountable {
    private Item item;
    private Discount[] discounts;

    public Order(Item item, Discount[] discounts) {
        this.item = item;
        this.discounts = discounts;
    }

    @Override
    public double applyDiscount() {
        double totalDiscount = 0;
        for (Discount discount : discounts) {
            totalDiscount += discount.calculateDiscount(item.getPrice());
        }
        double finalPrice = item.getPrice() - totalDiscount;
        System.out.println("Total after discounts for " + item.getName() + ": $" + finalPrice);
        return finalPrice;
    }

    public Item getItem() {
        return item;
    }

    public Discount[] getDiscounts() {
        return discounts;
    }
}
