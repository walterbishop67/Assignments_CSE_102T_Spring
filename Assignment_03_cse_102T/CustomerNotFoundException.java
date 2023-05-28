public class CustomerNotFoundException extends IllegalArgumentException{
    private Customer customer;

    CustomerNotFoundException(Customer customer){
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException: Name - " + customer.getName();
    }
}
