import java.util.HashMap;

public class Store {
    //Attributes of Store
    private String name;
    private String website;
    private HashMap<Product, Integer> products;
    int points;
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
    public int getInventorySize() {
        return products.size();
    }

    public int remaining(Product product){/////////////////
        if(!products.containsKey(product) || products.get(product) == 0)
            throw new ProductNotFoundException(product);
        return products.get(product);

    }
    public void addCustomer(Customer customer){
        customer_points.put(customer, 0);
    }
    public int getProductCount(Product product){///////////////
        if(!products.containsKey(product))
            throw new ProductNotFoundException(product);
        return products.get(product);

    }
    public int getCustomerPoints (Customer customer){
        if(!customer_points.containsKey(customer))
            throw new CustomerNotFoundException(customer);
        return customer_points.get(customer);
    }

    public void removeProduct(Product product){
        if(!products.containsKey(product))
            throw new ProductNotFoundException(product);
        products.remove(product);
    }

    public void addToInventory(Product product, int amount){
        if(!products.containsKey(product))
            products.put(product, amount);
        else{
            if(amount < 0)
                throw new InvalidAmountException(amount);
            int a = products.get(product);
            System.out.println("bu" + a);
            products.put(product, (amount + a));
            System.out.println(amount + a);
        }
    }
    public double purchase(Product product, int amount){
        if (!products.containsKey(product))
            throw new ProductNotFoundException(product);
        if (amount > remaining(product) || amount < 0)
            throw new InvalidAmountException(amount);

        products.put(product, remaining(product) - amount);
        return product.getPrice() * amount;

    }
}

