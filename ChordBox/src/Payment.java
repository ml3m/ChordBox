// Class E - Payment
public class Payment {
    private String method;
    private double amountPaid;

    public Payment(String method, double amountPaid) {
        this.method = method;
        this.amountPaid = amountPaid;
    }

    public void processPayment() {
        System.out.println("Processing payment using " + method + " for $" + amountPaid);
    }
}
