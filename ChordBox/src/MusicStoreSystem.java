public class MusicStoreSystem {

    public static void main(String[] args) {

        OutputDevice output_device = new OutputDevice();


        Instrument guitar = new Instrument("Guitar", 500);
        Instrument piano = new Instrument("Piano", 1000);

        Discount[] discounts = {new Discount(10, "percentage"), new Discount(50, "flat")};

        Order order = new Order(guitar, discounts);
        Customer customer = new Customer("John Doe", "john.doe@example.com");

        Payment payment = new Payment("Credit Card", 400);

        // based on commnad-line for now...
        if (args.length > 0) {
            if (args[0].equals("total")) {
                // total price after discount.
                output_device.writeMessage("Calculating total price...");
                order.applyDiscount();
            } else if (args[0].equals("list")) {
                output_device.writeMessage("Listing all instruments...");
                listInstruments(guitar, piano);
            } else {
                output_device.writeMessage("Invalid option. Use 'total' or 'list'.");
            }
        } else {
            output_device.writeMessage("Please provide an argument: 'total' or 'list'.");
        }
    }

    private static void listInstruments(Instrument... instruments, output_device) {
        output_device.writeMessage("Instruments available in the store:");
        for (Instrument instrument : instruments) {
            System.out.println("Instrument: " + instrument.getName() + ", Price: $" + instrument.getPrice());
        }
    }
}
