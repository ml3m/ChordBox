// Class B - Order
public class Order implements Discountable {
    private Instrument instrument;
    private Discount[] discounts;

    public Order(Instrument instrument, Discount[] discounts) {
        this.instrument = instrument;
        this.discounts = discounts;
    }

    // from Discountable interface
    @Override
    public void applyDiscount() {
        double totalDiscount = 0;
        for (Discount discount : discounts) {
            totalDiscount += discount.getDiscountAmount();
        }
        double finalPrice = instrument.getPrice() - totalDiscount;
        System.out.println("Total after discounts: $" + finalPrice);
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Discount[] getDiscounts() {
        return discounts;
    }
}
