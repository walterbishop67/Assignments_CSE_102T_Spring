public class CleaningProduct extends Product{
    private boolean liquid;//
    private String whereToUse;

    //Constructor that takes the ID, name, quantity, price, calories,
    //dairy, peanuts,eggs and gluten as parameters
    CleaningProduct(long Id, String name,
                    double price, boolean liquid, String whereToUse){
        super(Id, name, price);
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
