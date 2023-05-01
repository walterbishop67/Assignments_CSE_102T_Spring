import java.util.ArrayList;
public class Assignment_01_cse_102T_spring {
    /**
     * @since 29.03.2023
     */
    public static void main(String[] args) {
        Store s = new Store("Migros", "www.migros.com");
        Customer c = new Customer("cse 102");
        System.out.println(c);
        ClubCustomer cc = new ClubCustomer("club cse 102", "05445491825");
        cc.addPoints(20);
        cc.addPoints(30);
        System.out.println(cc.getPhone());
        System.out.println(cc);


        Product p = new Product("1234", "Computer", 20, 1000.00);
        FoodProduct fp = new FoodProduct("3456", "Snickers", 100, 2, 250, true, true, true, false);
        CleaningProduct cp = new CleaningProduct("5678", "mop", 28, 99, false, "multi-room");

        s.addProduct(p);
        s.addProduct(fp);

        for (int i = 0; i < s.getInventorySize(); i++)
            System.out.println(s.getProduct(i));
        s.addProduct(cp);

        s.addProduct(new Product("4321", "iphone", 50, 99.00));

        System.out.println(s.getProductIndex(new FoodProduct("8888", "Apples", 500, 1, 50, false, false, false, false)));

        System.out.println(cp.purchase(2));
        if (fp.containsGluten())
            System.out.println("My wife cannot eat or drink " + fp.getName());

        else
            System.out.println("My wife can eat or drink " + fp.getName());

        if (fp.containsPeanuts())
            System.out.println("My friend cannot eat or drink " + fp.getName());
        else
            System.out.println("My friend cannot eat or drink " + fp.getName());
        s.getProduct(0).addToInventory(3);

        for (int i = 0; i < s.getInventorySize(); i++) {
            Product cur = s.getProduct(i);
            System.out.println(cur);
            for (int j = i + 1; j < s.getInventorySize(); j++)
                if (cur.equals(s.getProduct(j))) {
                    System.out.println(cur.getName() + " is the same price as " + s.getProduct(j).getName());
                }
        }
    }
}
class Product {
    //  Attributes of the product
    private String id;
    private String name;
    private int quantity;
    private double price;

    //Constructor that takes the ID, name, quantity, and price as parameters
    Product(String id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    //accessor and mutator methods of private fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPrice(double price) {
        this.price = price;
    }

    //returns the current quantity
    public int remaining() {
        return quantity;
    }

    /*decrease count by amount and return the total price.
        if amount is negative or greater than amount it doesn't change count and returns 0 */
    public int addToInventory(int amount) {//
        if (amount >= 0) {
            quantity = amount + quantity;
            return quantity;
        }
        return quantity;
    }

    //shows the decreasing amount
    public double purchase(int amount) {
        if (amount >= 0 && amount <= quantity) {
            quantity = quantity - amount;
            return price * amount;
        }
        return 0;
    }

    //converts to string
    public String toString() {
        return "Product " + name + " has " + addToInventory(-1) + " remaining.";
    }

    //returns true if the passed object is also, a Product and has the same price
    public boolean equals(Object o) {
        if (o instanceof Product) {
            return Math.abs(price - ((Product) o).price) <= 0.001;
        }
        return false;
    }
}
class FoodProduct extends Product {
    // Attributes of the FoodProduct
    private int calories;
    private boolean dairy;//
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    //Constructor that takes the ID, name, quantity, price, calories, dairy, peanuts,eggs and gluten as parameters
    FoodProduct(String id, String name, int quantity,
                double price, int calories, boolean dairy,
                boolean eggs, boolean peanuts, boolean gluten) {
        super(id, name, quantity, price);
        this.calories = calories;
        this.dairy = dairy;
        this.eggs = eggs;
        this.peanuts = peanuts;
        this.gluten = gluten;
    }

    //accessor and mutator methods of private fields
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public boolean containsDairy() {
        return dairy;
    }

    public boolean containsEggs() {
        return eggs;
    }

    public boolean containsPeanuts() {
        return peanuts;
    }

    public boolean containsGluten() {
        return gluten;
    }
}
class CleaningProduct extends Product {
    //Attributes of the Cleaning product
    private boolean liquid;//
    private String whereToUse;

    //Constructor that takes the ID, name, quantity, price, calories,
    //dairy, peanuts,eggs and gluten as parameters
    CleaningProduct(String id, String name, int quantity,
                    double price, boolean liquid, String whereToUse) {
        super(id, name, quantity, price);
        this.liquid = liquid;
        this.whereToUse = whereToUse;
    }

    //accessor and mutator methods of private fields
    public String getWhereToUse() {
        return whereToUse;
    }

    public void setWhereToUse(String whereToUse) {
        this.whereToUse = whereToUse;
    }

    public boolean isLiquid() {
        return liquid;
    }
}
class  Customer {
    //Attribute of Customer
    private String name;

    // Constructor that takes the name as parameter
    Customer(String name) {
        this.name = name;
    }

    //accessor and mutator methods of private fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //converts to string
    public String toString() {
        return name;
    }
}
class ClubCustomer extends Customer {
    //Attributes of ClubCustomer
    private String phone;
    private int points;

    // Constructor that takes the name and Phone as parameters and sets points to 0
    ClubCustomer(String name, String phone) {
        super(name);
        this.phone = phone;
        points = 0;
    }

    //accessor and mutator methods of private fields
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints() {
        return points;
    }

    // if the parameter is greater than or equal to zero, I have added it to the points field of this class
    public void addPoints(int points) {
        if (points >= 0) {
            this.points += points;
        }
    }

    //converts to string
    public String toString() {
        return getName() + " has" + points + " points.";
    }
}
class Store {
    //Attributes of Store
    private String name;
    private String website;

    ArrayList<Product> products;

    //Constructor.Creates an empty list of products
    Store(String name, String website) {
        this.name = name;
        this.website = website;

        products = new ArrayList<>();
    }

    //accessor and mutator methods of private fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    //Returns the number of products in the ArrayList
    public int getInventorySize() {
        return products.size();
    }

    /*
         Adds the passed product to the index. If the index is negative or greater than the size of the list,
        adds the product to the end of the list
     */
    public void addProduct(Product product, int index) {
        if (index < 0 || index > products.size())
            products.add(products.size(), product);
        else
            products.add(index, product);
    }

    //Adds products to ArrayList
    public void addProduct(Product product) {
        products.add(products.size(), product);
    }

    //Returns the products in the ArrayList of the entered index
    public Product getProduct(int index) {
        if (index < 0 || index >= products.size())
            return null;
        return products.get(index);
    }

    //Returns the index of the products in the ArrayList,f the Product does not exist, return -1
    public int getProductIndex(Product p) {
        if (products.contains(p)) {
            return products.indexOf(p);
        }
        return -1;
    }
}
