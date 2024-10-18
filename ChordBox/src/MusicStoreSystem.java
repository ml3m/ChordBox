public class MusicStoreSystem {

    public static void main(String[] args) {
        // Create sample instruments
        Instrument guitar = new Instrument("Guitar", 500);
        Instrument piano = new Instrument("Piano", 1000);

        // Create sample discounts
        Discount[] discounts = {new Discount(10, "percentage"), new Discount(50, "flat")};

        // Create an order
        Order order = new Order(guitar, discounts);

        // Create a customer
        Customer customer = new Customer("John Doe", "john.doe@example.com");

        // Create a payment
        Payment payment = new Payment("Credit Card", 400);

        // Main logic based on program arguments
        if (args.length > 0) {
            if (args[0].equals("total")) {
                // Calculate total price after applying discounts
                System.out.println("Calculating total price...");
                order.applyDiscount();
            } else if (args[0].equals("list")) {
                // List all instruments
                System.out.println("Listing all instruments...");
                listInstruments(guitar, piano);
            } else {
                System.out.println("Invalid option. Use 'total' or 'list'.");
            }
        } else {
            System.out.println("Please provide an argument: 'total' or 'list'.");
        }
    }

    // Function to list all instruments
    private static void listInstruments(Instrument... instruments) {
        System.out.println("Instruments available in the store:");
        for (Instrument instrument : instruments) {
            System.out.println("Instrument: " + instrument.getName() + ", Price: $" + instrument.getPrice());
        }
    }
}
