public class Product {

    private long Id;
    private String name;
    private double price;

    //Constructor that takes the ID, name, quantity, and price as parameters
    Product(long Id, String name, double price)throws InvalidPriceException{
        this.Id = Id;
        this.name = name;
        setPrice(price);
    }

    //accessor and mutator methods of private fields
    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    //throwsInvalidPriceException if price is negative
    public void setPrice(double price) throws InvalidPriceException{
        if(price < 0)
            throw new InvalidPriceException(price);
        this.price = price;
    }

    //converts to string
    public String toString() {
        return Id + " - " + name + " @ " + price;
    }

        //returns true if the passed object is also, a Product and has the same price
    public boolean equals(Object o) {
        if (o instanceof Product) {
            return Math.abs(price - ((Product) o).price) <= 0.001;
        }
        return false;
    }
}
