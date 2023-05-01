/**
 * @since 16.04.2023
 */

import java.util.ArrayList;

public class Assignment_02_102T_Spring{
    public static void main(String[] args) {
        Store s = new Store("Migros", "www.migros.com.tr");

        Customer c = new Customer("CSE 102");

        ClubCustomer cc = new ClubCustomer("Club CSE 102", "05551234567");
//        s.addCustomer(c);
        s.addCustomer(cc);

        Product p = new Product(123456L, "Computer", 20, 1000.00);
        FoodProduct fp = new FoodProduct(456798L, "Snickers", 100, 2, 250, true, true, true, false);
        CleaningProduct cp = new CleaningProduct(31654L, "Mop", 28, 99, false, "Multi-room");

        s.addProduct(p);
        s.addProduct(fp);
        s.addProduct(cp);

        System.out.println(s.getInventorySize());
//        System.out.println(s.getProduct("shoes"));

        System.out.println(cp.purchase(2));
        s.getProduct("Computer").addToInventory(3);
//        System.out.println(fp.purchase(200));
        c.addToCart(p, 2);
        c.addToCart(s.getProduct("snickers"),-2);
        c.addToCart(s.getProduct("snickers"), 1);
        System.out.println("Total due - " + c.getTotalDue());
        System.out.println("\n\nReceipt:\n" + c.receipt());

//        System.out.println("After paying: "+c.pay(2000));

        System.out.println("After paying: " + c.pay(2020));

        System.out.println("Total due - " + c.getTotalDue());
        System.out.println("\n\nReceipt 1:\n" + c.receipt());

//        Customer c2 = s.getCustomer("05551234568");
        cc.addToCart(s.getProduct("snickers"), 2);
        cc.addToCart(s.getProduct("snickers"), 1);
        System.out.println("\n\nReceipt 2:\n" + cc.receipt());

        Customer c3 = s.getCustomer("05551234567");
        c3.addToCart(s.getProduct("snickers"), 10);
        System.out.println("\n\nReceipt 3:\n" + cc.receipt());

        System.out.println(((ClubCustomer) c3).pay(26, false));

        c3.addToCart(s.getProduct(31654L),3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        System.out.println(cc.pay(3 * 99, false));

        c3.addToCart(s.getProduct(31654L), 3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        System.out.println(cc.pay(3 * 99, true));

        System.out.println(s.getProduct("snickers"));



    }
    
}
class Product {

    private long Id;
    private String name;
    private int quantity;
    private double price;

    //Constructor that takes the ID, name, quantity, and price as parameters
    Product(long Id, String name, int quantity, double price)throws InvalidPriceException{
        this.Id = Id;
        this.name = name;
        this.quantity = quantity;
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
            throw new InvalidPriceException();
        this.price = price;
    }

    //returns the current quantity
    public int remaining() {
        return quantity;
    }

    /*decrease count by amount and return the total price.
        if amount is negative or greater than amount it doesn't change count and returns 0 */
    public int addToInventory(int amount) throws InvalidAmountException{
        if(amount < 0)
            throw new InvalidAmountException(amount);

        quantity = amount + quantity;
        return quantity;
    }

    //shows the decreasing amount
    public double purchase(int amount) throws InvalidAmountException{
        if(amount < 0)
            throw new InvalidAmountException(amount);
        else if (amount > quantity)
            throw new InvalidAmountException(amount, remaining());

        quantity = quantity - amount;
        return price * amount;
    }

    //converts to string
    public String toString() {
        return "Product " + name + " has " + quantity + " remaining.";
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
    //Attributes
    private int calories;
    private boolean dairy;//
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    //Constructor that takes the ID, name, quantity, price, calories, dairy, peanuts,eggs and gluten as parameters
    FoodProduct(long Id, String name, int quantity,
                double price, int calories, boolean dairy,
                boolean eggs, boolean peanuts, boolean gluten){
        super(Id, name, quantity, price);
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
    //Attributes of the Cleaning product
    private boolean liquid;//
    private String whereToUse;

    //Constructor that takes the ID, name, quantity, price, calories,
    //dairy, peanuts,eggs and gluten as parameters
    CleaningProduct(long Id, String name, int quantity,
                    double price, boolean liquid, String whereToUse){
        super(Id, name, quantity, price);
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
    private ArrayList<Product> cart_product = new ArrayList<>();
    private ArrayList<Integer> cart_count = new ArrayList<>();
    private double totalDue = 0;



    // Constructor that takes the name as parameter
    Customer(String name){
        this.name = name;
    }

    //accessor and mutator methods of private fields
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Adds the passed Product and number to the customer cart
    public void addToCart(Product product, int count){
        try{
            product.purchase(count);

            cart_product.add(product);
            cart_count.add(count);

            totalDue += product.getPrice() * count;
        }catch (InvalidAmountException e) {
            System.out.println("ERROR: " + e);
        }
    }

    public String receipt(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cart_product.size(); i++) {
            sb.append(cart_product.get(i).getName()).append(" - ").append(cart_product.get(i)
                            .getPrice()).append(" X ").append(cart_count.get(i)).append(" = ")
                    .append(cart_product.get(i).purchase(cart_count.get(i))).append("\n");
        }
        sb.append("\n---------------------" +
                "---------------\n\n").append("Total Due = ").append(getTotalDue());

        return sb.toString();
    }

    public double getTotalDue(){
        return totalDue;
    }

    public double pay(double amount)throws InsufficientFundsException{

        if(amount >= getTotalDue()){
            System.out.println("Thank you for shopping with us.");
            cart_product.clear();
            cart_count.clear();
            double a = getTotalDue();
            totalDue = 0;
            return amount - a;

        }
        throw new InsufficientFundsException(getTotalDue(), amount);
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
    ClubCustomer(String name, String phone){
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
    public void addPoints(int points){
        if(points >= 0){
            this.points += points;
        }
    }

    public double pay(double amount, boolean usePoint)throws InsufficientFundsException{
        double getSuper = getTotalDue();
        double superPay = super.pay(amount);//returns amount - getTotalDue(); (change)

        if(getPoints() >= 0) {
            int getPoints = getPoints();
            int remaningPoints = 0;
            double pointsToMoney =0;
            if(usePoint){
                if(getPoints() * 0.01 > getSuper){
                    remaningPoints = (int)(getPoints() - getSuper * 100);
                    getPoints = getPoints() - remaningPoints;
                }
                pointsToMoney = getPoints * 0.01;
                points = remaningPoints;
            }
            superPay = superPay + pointsToMoney;
            addPoints((int)(amount - superPay));
        }

        return superPay;// change is super pay
    }

    //converts to string
    public String toString() {
        return getName() + " has " + getPoints() + " points.";
    }
}

class Store {
    //Attributes of Store
    private String name;
    private String website;

    ArrayList<ClubCustomer> clubCustomers = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();

    //Constructor.Creates an empty list of products
    Store(String name, String website) {
        this.name = name.toLowerCase();
        this.website = website;
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
    public void addProduct(Product product){
        products.add(product);
    }
    public Product getProduct(long ID) throws ProductNotFoundException{
        for(Product product : products){
            if(product.getId() == ID)
                return product;
        }
        throw new ProductNotFoundException(ID);
    }
    public Product getProduct(String name) throws ProductNotFoundException{
        for(Product product : products){
            if(product.getName().equalsIgnoreCase(name.toLowerCase()))
                return product;
        }
        throw new ProductNotFoundException(name);
    }
    public void addCustomer(ClubCustomer customer){
        clubCustomers.add(customer);
    }
    public ClubCustomer getCustomer(String phone)throws CustomerNotFoundException{
        for(ClubCustomer clubCustomer : clubCustomers)
            if(clubCustomer.getPhone().equals(phone))
                return clubCustomer;

        throw new CustomerNotFoundException(phone);
    }
    public void removeProduct(long ID) throws ProductNotFoundException{
        products.remove(getProduct(ID));
    }
    public void removeProduct(String name) throws ProductNotFoundException{
        products.remove(getProduct(name));
    }
    public void removeCustomer(String phone)throws CustomerNotFoundException{
        clubCustomers.remove(getCustomer(phone));
    }
}

//Custom Exceptions
class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;
    public CustomerNotFoundException(String phone) {
        this.phone = phone;
    }

    public String toString(){
        return  "CustomerNotFoundException: " + phone;
    }
}
class InsufficientFundsException extends RuntimeException{
    private double total;
    private double payment;
    InsufficientFundsException(double total, double payment){
        this.total = total;
        this.payment = payment;
    }

    public String toString(){
        return "InsufficientFundsException: " + total + " due, but only "+ payment + " given";
    }
}
class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity;

    InvalidAmountException(int amount){
        this.amount = amount;
        this.quantity = 0;
    }
    InvalidAmountException(int amount, int quantity){
        this.amount = amount;
        this.quantity = quantity;
    }

    public String toString(){
        if(quantity == 0)
            return "InvalidAmountException: " + amount;

        return "InvalidAmountException: " + amount + " was requested, but only " + quantity + " remaining";

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
class ProductNotFoundException extends IllegalArgumentException{
    private long ID;
    private String name;

    ProductNotFoundException(long ID){
        this.ID = ID;
        this.name = null;
    }

    ProductNotFoundException(String name){
        this.name = name;
        this.ID = 0;
    }

    public String toString(){
        if(name == null)
            return "ProductNotFoundException: ID - " + ID;

        return "ProductNotFoundException: Name - " + name;

    }
}
