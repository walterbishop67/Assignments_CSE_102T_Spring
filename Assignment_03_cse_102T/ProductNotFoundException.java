public class ProductNotFoundException extends IllegalArgumentException{
    private Product product;
    ProductNotFoundException(Product product){
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductNotFoundException: ID - "  + product.getId() + " Name - " + product.getName();
    }
}
