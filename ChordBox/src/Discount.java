public class Discount {
    private double amount;
    private String type; // "percentage" or "flat"

    public Discount(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public double calculateDiscount(double price) {
        if (type.equals("percentage")) {
            return price * (amount / 100);
        } else if (type.equals("flat")) {
            return amount;
        }
        return 0;
    }

    public String getType() {
        return type;
    }
}
