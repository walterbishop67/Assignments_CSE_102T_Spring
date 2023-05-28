public class FoodProduct extends Product{
    private int calories;
    private boolean dairy;//
    private boolean eggs;
    private boolean peanuts;
    private boolean gluten;

    //Constructor that takes the ID, name, quantity, price, calories, dairy, peanuts,eggs and gluten as parameters
    FoodProduct(long Id, String name,
                double price, int calories, boolean dairy,
                boolean eggs, boolean peanuts, boolean gluten){
        super(Id, name, price);
        this.calories = calories;
        this.dairy = dairy;
        this.eggs = eggs;
        this.peanuts = peanuts;
        this.gluten = gluten;
    }

    //accessor and mutator methods of private fields
    public int getCalories() {
        return calories;
    }

    //Actually we don't need the throws but, we use like this in lecture.
    public void setCalories(int calories) throws InvalidAmountException{
        if(calories < 0)
            throw new InvalidAmountException(calories);

        this.calories = calories;
    }
    public boolean containsDairy(){
        return dairy;
    }

    public boolean containsEggs(){
        return eggs;
    }
    public boolean containsPeanuts(){
        return peanuts;
    }
    public boolean containsGluten(){
        return gluten;
    }

}
