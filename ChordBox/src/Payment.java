// Class E - Payment
public class Payment {
    private String method;
    private double amountPaid;

    // Constructor
    public Payment(String method, double amountPaid) {
        this.method = method;
        this.amountPaid = amountPaid;
    }

    // Method to display payment details
    public void processPayment() {
        System.out.println("Processing payment using " + method + " for $" + amountPaid);
    }
}
