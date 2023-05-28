public class InsufficientFundsException extends RuntimeException{
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
