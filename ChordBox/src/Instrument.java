// Class A - Instrument
public class Instrument implements Sellable {
    private String name;
    private double price;

    public Instrument(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public void sell() {
        System.out.println("Selling instrument: " + name + " for $" + price);
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
