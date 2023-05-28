import java.util.HashMap;

class Customer {
    //Attribute of Customer
    private String name;
    private HashMap<Product, Integer> cart_customer = new HashMap<>();
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
    public void addToCart(Store store, Product product, int count){
        try{
            if (store.getProductCount(product) < count)
                throw new ProductNotFoundException(product);

            if (store.getProductCount(product) < count)
                throw new InvalidAmountException(count);

            if(cart_customer.containsKey(product)) {
                int a = cart_customer.get(product);
                cart_customer.put(product, count + a);

            }else
                if (store.products.containsKey(product))
                    cart_customer.put(product, count);

            totalDue += store.purchase(product, count);

        }catch (InvalidAmountException | ProductNotFoundException e) {
            System.out.println("ERROR: " + e);
        }
    }

    public String receipt(Store store){
        if (cart_customer.isEmpty()) {
            throw new StoreNotFoundException("Customer does not have a cart , the store: " + name);
        }

        StringBuilder receiptBuilder = new StringBuilder();

        // Header
        receiptBuilder.append("Customer receipt for ").append(store.getName()).append("\n\n");
        int quantity;

        // Products in the cart
        for (HashMap.Entry<Product, Integer> entry : cart_customer.entrySet()) {
            Product product = entry.getKey();
            quantity = entry.getValue();

            receiptBuilder.append(product.getId()).append(" - ").append(product.getName()).append(" @ ")
                    .append(product.getPrice()).append(" X ").append(quantity).append(" = ").append(product.getPrice() * quantity)
                    .append("\n");
        }

        // Total Due
        receiptBuilder.append("--------------------------------------\n").append("Total Due - ").append(getTotalDue(store)).append("\n");

        return receiptBuilder.toString();
    }
    public double getTotalDue(Store store){///////////////////////////////////
        if (cart_customer.isEmpty()) {
            throw new StoreNotFoundException("Customer does not have a cart , the store: " + name);
        }
        return totalDue;
    }
    public int getPoints(Store store){
        if (!store.customer_points.containsKey(this)) {
            throw new StoreNotFoundException("Customer does not have a cart , the store: " + name);
        }
        return store.customer_points.get(this);
    }


    public double pay(Store store, double amount, boolean usePoints) {
        if (cart_customer.isEmpty()) {////////////////
            throw new StoreNotFoundException(store.getName());
        }
        if (amount < getTotalDue(store)) {
            throw new InsufficientFundsException(getTotalDue(store), amount);
        }
        double superPay = amount - getTotalDue(store);//returns amount - getTotalDue(); (change)
        if (store.customer_points.containsKey(this)) {
            double getSuper = getTotalDue(store);

            if (getPoints(store) >= 0) {
                int getPoints = getPoints(store);
                int remaningPoints = 0;
                double pointsToMoney = 0;

                if (usePoints) {

                    if (getPoints(store) * 0.01 > getSuper) {
                        remaningPoints = (int) (getPoints(store) - getSuper * 100);
                        getPoints = getPoints(store) - remaningPoints;
                    }
                    pointsToMoney = getPoints * 0.01;
                    store.customer_points.replace(this, remaningPoints);

                }
                superPay = superPay + pointsToMoney;

                int a = store.customer_points.get(this);
                store.customer_points.replace(this, a + (int) (amount - superPay));
            }
        }
        System.out.println("Thank you for your business");
        return superPay;// change is super pay
    }
    //converts to string
    public String toString() {
        return name;
    }
}
