public class MusicStoreSystem {
    public static void main(String[] args) {
        OutputDevice outputDevice = new OutputDevice();

        // Create items
        Item guitar = new Instrument("Guitar", 500);
        Item piano = new Instrument("Piano", 1000);
        Item vinylDisk = new Disk("Vinyl Record", 20);
        Item musicCD = new CD("Greatest Hits CD", 15);
        Item bandPoster = new Poster("Band Poster", 10);

        // Create discounts
        Discount[] discounts = {new Discount(10, "percentage"), new Discount(5, "flat")};

        // Create orders
        Order guitarOrder = new Order(guitar, discounts);
        Order cdOrder = new Order(musicCD, discounts);

        // Display items
        outputDevice.writeMessage("Available Items:");
        listItems(outputDevice, guitar, piano, vinylDisk, musicCD, bandPoster);

        // Calculate discounts
        outputDevice.writeMessage("Calculating discounts for Guitar and CD:");
        guitarOrder.applyDiscount();
        cdOrder.applyDiscount();

        // Process payment example
        Payment payment = new Payment("Credit Card", 500);
        payment.processPayment();
    }

    // Method to list items
    private static void listItems(OutputDevice outputDevice, Item... items) {
        outputDevice.writeMessage("Items in store:");
        for (Item item : items) {
            outputDevice.writeMessage("Item: " + item.getName() + ", Price: $" + item.getPrice());
        }
    }
}
