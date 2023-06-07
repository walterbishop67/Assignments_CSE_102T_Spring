import java.util.HashMap;

/**
 * @author Umut ALTUN
 * @since 01.06.2023
 */

public class Assignment03_20210808053 {
    public static void main(String[] args) {
        Store s1 = new Store("bim", "www.bim.com.tr");
        Store s2 = new Store("migros", "www.migros.com.tr");

        Customer c1 = new Customer("dsrth");
        Customer c2 = new Customer("sdfghs");
        Customer c3 = new Customer("asedfh");

        Product p1 = new Product(123456L, "ton balığı", 30);
        Product p2 = new Product(456789L, "ekmek", 5);
        Product p3 = new Product(123789L, "su", 2);
        Product p4 = new Product(456123L, "çikolata", 6);

        s1.addToInventory(p1, -1);
        s1.addToInventory(p2, 10);
        s2.addToInventory(p3, 5);
        s2.addToInventory(p4, 5);

        s1.addCustomer(c1);
        s1.addCustomer(c2);
        s1.addCustomer(c3);

        s2.addCustomer(c1);
        s2.addCustomer(c2);



       
        c1.addToCart(s1, p1, 4);
        c1.addToCart(s2, p3, 2);
        c1.addToCart(s1, p1, 1);
        c1.addToCart(s1, p1, 6);

        System.out.println(c1.receipt(s1));
        System.out.println(c1.receipt(s2));

        c1.pay(s1, 155, true);
        c1.pay(s2, 5, false);
        System.out.println("------------------");
        System.out.println(s1.getCustomerPoints(c1));
        System.out.println(s2.getCustomerPoints(c1));
        c1.addToCart(s1, p2,2);
        System.out.println(c1.receipt(s1));

        System.out.println(c1.pay(s1, 9, true));
        System.out.println(s1.getCustomerPoints(c1));
        System.out.println(c1.getPoints(s1));


    }
}
class Product {

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
class FoodProduct extends Product{
    private int calories;
    private boolean dairy;//
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    //Constructor that takes the ID, name, quantity, price, calories, dairy, peanuts,eggs and gluten as parameters
    FoodProduct(long Id, String name,
                double price, int calories, boolean dairy,
                boolean eggs, boolean peanuts, boolean gluten) throws InvalidAmountException{
        super(Id, name, price);
        setCalories(calories);
        this.dairy = dairy;
        this.eggs = eggs;
        this.peanuts = peanuts;
        this.gluten = gluten;
    }

    //accessor and mutator methods of private fields
    public int getCalories() {
        return calories;
    }

    //Actually we don't need the throws but, we use like this in lecture.
    public void setCalories(int calories) throws InvalidAmountException{
        if(calories < 0)
            throw new InvalidAmountException(calories);

        this.calories = calories;
    }
    public boolean containsDairy(){
        return dairy;
    }

    public boolean containsEggs(){
        return eggs;
    }
    public boolean containsPeanuts(){
        return peanuts;
    }
    public boolean containsGluten(){
        return gluten;
    }

}
class CleaningProduct extends Product{
    private boolean liquid;//
    private String whereToUse;

    //Constructor that takes the ID, name, quantity, price, calories,
    //dairy, peanuts,eggs and gluten as parameters
    CleaningProduct(long Id, String name,
                    double price, boolean liquid, String whereToUse){
        super(Id, name, price);
        this.liquid = liquid;
        this.whereToUse = whereToUse;
    }

    //accessor and mutator methods of private fields
    public String getWhereToUse(){
        return whereToUse;
    }
    public void setWhereToUse(String whereToUse) {
        this.whereToUse = whereToUse;
    }
    public boolean isLiquid(){
        return liquid;
    }
}
class Customer {
    //Attribute of Customer
    private String name;
    private HashMap<Store, Double> totalDue;
    private HashMap<Store, HashMap<Product, Integer>> storeList;
    HashMap<Store, Integer> pointss;


    // Constructor that takes the name as parameter
    Customer(String name){
        this.name = name;
        totalDue = new HashMap<>();
        storeList = new HashMap<>();
        pointss = new HashMap<>();
    }

    //accessor and mutator methods of private fields
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Adds the passed Product and number to the customer cart
    public void addToCart(Store store, Product product, int count){

        try{
            if(store.getProductCount(product) < count)
                throw new InvalidAmountException(count);

            storeList.computeIfAbsent(store, k -> new HashMap<>());

            if (storeList.get(store).containsKey(product)) {
                storeList.get(store).put(product, storeList.get(store).get(product) + count);

            }else
                storeList.get(store).putIfAbsent(product,count);

            if (totalDue.get(store) == null)
                totalDue.putIfAbsent(store, 0.0);

            totalDue.put(store, totalDue.get(store) + store.purchase(product, count));

        }catch (InvalidAmountException | ProductNotFoundException e) {
            System.out.println("ERROR: " + e);
        }
    }
    public String receipt(Store store){
        if (storeList.get(store) == null) {
            throw new StoreNotFoundException(store.getName());
        }

        StringBuilder receiptBuilder = new StringBuilder();

        // Header
        receiptBuilder.append("Customer receipt for ").append(store.getName()).append("\n\n");
        int quantity ;

        // Products in the cart
        for (HashMap.Entry<Product, Integer> entry : storeList.get(store).entrySet()) {
            Product product = entry.getKey();
            quantity = entry.getValue();

            receiptBuilder.append(product.getId()).append(" - ").append(product.getName()).append(" @ ")
                    .append(product.getPrice()).append(" X ").append(quantity).append(" ... ").append(product.getPrice() * quantity)
                    .append("\n");
        }

        // Total Due
        receiptBuilder.append("--------------------------------------\n").append("Total Due - ").append(getTotalDue(store)).append("\n");

        return receiptBuilder.toString();
    }
    public double getTotalDue(Store store){
        if(storeList.get(store) == null)
            throw new StoreNotFoundException(store.getName());
        return totalDue.get(store);
    }
    public int getPoints(Store store){
        try{
            return store.getCustomerPoints(this);

        }catch (StoreNotFoundException e){
            throw new StoreNotFoundException(store.getName());
        }
    }

    public double pay(Store store, double amount, boolean usePoints) {
        if (storeList.get(store) == null) {
            throw new StoreNotFoundException(store.getName());
        }
        try{
            if (usePoints )
                if (amount + store.getCustomerPoints(this)< getTotalDue(store) ) {
                    throw new InsufficientFundsException(getTotalDue(store), amount);
                }

        }catch(NullPointerException ignored){}
        double superPay = amount - getTotalDue(store);

        pointss.putIfAbsent(store, 0);
        try {
            getPoints(store);
            int getPoints = pointss.get(store);
            int remaningPoints = 0;
            double pointsToMoney = 0;
            if (usePoints) {
                if (pointss.get(store) * 0.01 > getTotalDue(store)) {
                    remaningPoints = (int) (pointss.get(store) - getTotalDue(store) * 100);
                    getPoints = pointss.get(store) - remaningPoints;
                }
                pointsToMoney = getPoints * 0.01;
            }
            superPay = superPay + pointsToMoney;
            pointss.put(store, (remaningPoints + (int) (amount - superPay)));

        } catch (CustomerNotFoundException ignored) {}

        totalDue.put(store, 0.0);
        storeList.get(store).clear();
        System.out.println("Thank you for your business");
        return superPay;// change is super pay
    }
    //converts to string
    public String toString() {
        return name;
    }
}

class Store {
    //Attributes of Store
    private String name;
    private String website;
    private HashMap<Product, Integer> products;
    private HashMap<Customer, Integer> customer_points;

    //Constructor.Creates an empty list of products
    Store(String name, String website) {
        this.name = name;
        this.website = website;
        products = new HashMap<>();
        customer_points = new HashMap<>();
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

    public int getCount() {
        return products.size();
    }

    public void addCustomer(Customer customer) {
        customer_points.put(customer, 0);
    }

    public int getProductCount(Product product) {
        if (!products.containsKey(product))
            throw new ProductNotFoundException(product);
        return products.get(product);

    }

    public int getCustomerPoints(Customer customer) {
        if (!customer_points.containsKey(customer))
            throw new CustomerNotFoundException(customer);
        customer_points.put(customer, customer.pointss.get(this));
        return customer_points.get(customer);
    }

    public void removeProduct(Product product) {
        if (!products.containsKey(product))
            throw new ProductNotFoundException(product);
        products.remove(product);
    }

    public void addToInventory(Product product, int amount) {
        if (!products.containsKey(product))
            products.put(product, amount);
        else {
            if (amount < 0)
                throw new InvalidAmountException(amount);
            int a = products.get(product);
            products.put(product, (amount + a));
        }
    }

    public double purchase(Product product, int amount) {
        if (!products.containsKey(product))
            throw new ProductNotFoundException(product);
        if (amount > getProductCount(product) || amount < 0)
            throw new InvalidAmountException(amount);
        //puan sistemi burada olacak

        products.put(product, getProductCount(product) - amount);
        return product.getPrice() * amount;

    }
}class InvalidAmountException extends RuntimeException {
    private int amount;
    private int quantity;

    InvalidAmountException(int amount) {
        this.amount = amount;
        this.quantity = 0;
    }

    InvalidAmountException(int amount, int quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    public String toString() {
        if (quantity == 0)
            return "InvalidAmountException: " + amount;

        return "InvalidAmountException: " + amount + " was requested, but only " + quantity + " remaining";

    }
}

class InsufficientFundsException extends RuntimeException {
    private double total;
    private double payment;

    InsufficientFundsException(double total, double payment) {
        this.total = total;
        this.payment = payment;
    }

    public String toString() {
        return "InsufficientFundsException: " + total + " due, but only " + payment + " given";
    }
}
class ProductNotFoundException extends IllegalArgumentException{
    private Product product;
    ProductNotFoundException(Product product){
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductNotFoundException: ID - "  + product.getId() + " Name - " + product.getName();
    }
}
class InvalidPriceException extends RuntimeException{
    private double price;
    InvalidPriceException(double price){
        this.price = price;
    }

    public String toString(){
        return  "InvalidPriceException: " + price;
    }
}
class StoreNotFoundException extends IllegalArgumentException{
    private String name;
    StoreNotFoundException(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreNotFoundException: " + name;
    }
}
class CustomerNotFoundException extends IllegalArgumentException{
    private Customer customer;

    CustomerNotFoundException(Customer customer){
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException: Name - " + customer.getName();
    }
}





