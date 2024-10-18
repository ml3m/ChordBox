// Class B - Order
public class Order implements Discountable {
    private Instrument instrument;
    private Discount[] discounts;

    // Constructor
    public Order(Instrument instrument, Discount[] discounts) {
        this.instrument = instrument;
        this.discounts = discounts;
    }

    // Implement applyDiscount method from Discountable
    @Override
    public void applyDiscount() {
        double totalDiscount = 0;
        for (Discount discount : discounts) {
            totalDiscount += discount.getDiscountAmount();
        }
        double finalPrice = instrument.getPrice() - totalDiscount;
        System.out.println("Total after discounts: $" + finalPrice);
    }

    // Getter for instrument
    public Instrument getInstrument() {
        return instrument;
    }

    // Getter for discounts
    public Discount[] getDiscounts() {
        return discounts;
    }
}
