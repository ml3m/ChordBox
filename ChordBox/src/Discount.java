// Class C - Discount
public class Discount {
    private double amount;
    private String type; // "percentage" or "flat"

    // Constructor
    public Discount(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    // Getter for discount amount
    public double getDiscountAmount() {
        if (type.equals("percentage")) {
            return amount / 100; // Apply percentage
        } else if (type.equals("flat")) {
            return amount; // Apply flat discount
        }
        return 0;
    }

    // Getter for discount type
    public String getType() {
        return type;
    }
}
