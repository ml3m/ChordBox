// Class A - Instrument
public class Instrument implements Sellable {
    private String name;
    private double price;

    // Constructor
    public Instrument(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Implement the sell method from Sellable
    @Override
    public void sell() {
        System.out.println("Selling instrument: " + name + " for $" + price);
    }

    // Getter for price
    public double getPrice() {
        return price;
    }

    // Getter for name
    public String getName() {
        return name;
    }
}
