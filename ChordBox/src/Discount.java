// Class C - Discount
public class Discount {
    private double amount;
    private String type; // percentage or flat

    public Discount(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public double getDiscountAmount() {
        if (type.equals("percentage")) {
            return amount / 100; 
        } else if (type.equals("flat")) {
            return amount; 
        }
        return 0;
    }

    public String getType() {
        return type;
    }
}
