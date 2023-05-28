public class InvalidPriceException extends RuntimeException{
    private double price;
    InvalidPriceException(double price){
        this.price = price;
    }

    public String toString(){
        return  "InvalidPriceException: " + price;
    }
}

