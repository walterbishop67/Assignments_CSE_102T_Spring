import java.util.HashMap;

public class Store {
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

    public int getCount(){
        return products.size();
    }
    public void addCustomer(Customer customer){
        customer_points.put(customer, 0);
    }
    public int getProductCount(Product product){
        if(!products.containsKey(product))
            throw new ProductNotFoundException(product);
        return products.get(product);

    }
    public int getCustomerPoints (Customer customer){
        if(!customer_points.containsKey(customer))
            throw new CustomerNotFoundException(customer);
        customer_points.put(customer, customer.pointss.get(this));
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
            products.put(product, (amount + a));
        }
    }
    public double purchase(Product product, int amount){
        if (!products.containsKey(product))
            throw new ProductNotFoundException(product);
        if (amount > getProductCount(product) || amount < 0)
            throw new InvalidAmountException(amount);
        //puan sistemi burada olacak

        products.put(product, getProductCount(product) - amount);
        return product.getPrice() * amount;

    }
}

