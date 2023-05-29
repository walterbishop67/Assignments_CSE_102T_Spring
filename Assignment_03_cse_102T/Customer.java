import java.util.Date;
import java.util.HashMap;

class Customer {
    //Attribute of Customer
    private String name;
    private HashMap<Product, Integer> cart_customer ;
    private HashMap<Store, Double> totalDue = new HashMap<>();
    private HashMap<Store, HashMap<Product, Integer>> store_list = new HashMap<>();
    HashMap<Store, Integer> pointss = new HashMap<>();

    // Constructor that takes the name as parameter
    Customer(String name){
        this.name = name;
        cart_customer = new HashMap<>();
    }

    //accessor and mutator methods of private fields
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Adds the passed Product and number to the customer cart
    HashMap <Product, Integer> hashMap = new HashMap<>();
    public void addToCart(Store store, Product product, int count){

        try{

            if(store.getProductCount(product) < count)
                throw new InvalidAmountException(count);

            if(cart_customer.containsKey(product)) {
                int a = cart_customer.get(product);
                cart_customer.put(product, count + a);

            }else
                if (store.getProductCount(product) >= 0)
                    cart_customer.put(product, count);




            double d;
            if (totalDue.get(store) == null)
                d = 0;
            else
                d = totalDue.get(store);
            totalDue.put(store, d + store.purchase(product, count));

            store_list.put(store, hashMap);

        }catch (InvalidAmountException | ProductNotFoundException e) {
            System.out.println("ERROR: " + e);
        }
    }

    public String receipt(Store store){
        if (store_list.get(store) == null) {
            throw new StoreNotFoundException("Customer does not have a cart , the store: " + name);
        }

        StringBuilder receiptBuilder = new StringBuilder();

        // Header
        receiptBuilder.append("Customer receipt for ").append(store.getName()).append("\n\n");
        int quantity = 0;

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
    public double getTotalDue(Store store){
        if(store_list.get(store) == null)
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
        if (store_list.get(store) == null) {////////////////
            throw new StoreNotFoundException(store.getName());
        }
        if (amount < getTotalDue(store)) {
            throw new InsufficientFundsException(getTotalDue(store), amount);
        }

        double superPay = amount - getTotalDue(store);//returns amount - getTotalDue(); (change)
        try{
            store.getCustomerPoints(this);
            int points = 0;
            double getSuper = getTotalDue(store);
            if (getPoints(store) >= 0) {
                int getPoints = pointss.get(store);
                int remaningPoints = 0;
                double pointsToMoney = 0;

                if (usePoints) {

                    if (getPoints(store) * 0.01 > getSuper) {
                        remaningPoints = (int) (getPoints(store) - getSuper * 100);
                        getPoints = getPoints(store) - remaningPoints;
                    }
                    pointsToMoney = getPoints * 0.01;
                    points = remaningPoints;

                }
                superPay = superPay + pointsToMoney;

                int a = store.getCustomerPoints(this);
                points += (int) (amount - superPay);
                points = 20;

                pointss.put(store, 20);
                System.out.println(pointss.get(store));
            }
        }catch (CustomerNotFoundException ignored){}
        totalDue.clear();
        store_list.clear();
        cart_customer.clear();
        System.out.println("Thank you for your business");
        return superPay;// change is super pay
    }
    //converts to string
    public String toString() {
        return name;
    }
}
