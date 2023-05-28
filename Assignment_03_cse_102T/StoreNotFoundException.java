public class StoreNotFoundException extends IllegalArgumentException{
    private String name;
    StoreNotFoundException(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreNotFoundException" + name;
    }
}
