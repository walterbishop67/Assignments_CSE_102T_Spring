public class InvalidAmountException extends RuntimeException{
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
