public class Assignment03_20210808053 {
    public static void main(String[] args) {
        Store s1 = new Store("Migros", "www.migros.com");
        Store s2 = new Store("BIM", "www.bim.com");

        Customer c = new Customer("CSE 102");

        Customer cc = new Customer("Club CSE 102");
        s1.addCustomer(cc);
        s2.addCustomer(cc);
        s1.addCustomer(c);
        s2.addCustomer(c);


        Product p = new Product(123456L, "Computer", 1000.00);
        FoodProduct fp = new FoodProduct(456798L, "Snickers", 2,250, true
                ,true, true, false);
        CleaningProduct cp = new CleaningProduct(31654L, "Mop",99,false,"Multi-room");
        System.out.println(cp);

        s1.addToInventory(p, 20);
        s2.addToInventory(p, 10);
        s2.addToInventory(fp,100);
        s1.addToInventory(cp,28);

        System.out.println(s1.getName() + " has "+ s1.getCount() + " products");
        System.out.println(+s1.getProductCount(p));


        System.out.println(s1.purchase(p, 2));
        s1.addToInventory(p, 3);
        System.out.println(s1.getProductCount(p));
        System.out.println(s2.getProductCount(p));
        //System.out.println(s1.getProductCount(fp));    //ex
        //System.out.println(s2.purchase(fp, 200));//ex

        c.addToCart(s1, p, 2);
        c.addToCart(s1, fp, 1);//ex caught
        c.addToCart(s1, cp, 1);
        System.out.println("Total Due - " + c.getTotalDue(s1));
        System.out.println("\n\nReceipt:\n" + c.receipt(s1));
        //System.out.println("\n\nReceipt:\n" + c.receipt(s2));//ex

        //System.out.println("After paying: " + c.pay(s2, 2000, true));//ex

        System.out.println("After paying: " + c.pay(s1, 2100, true));

        //System.out.println("Total Due - " + c.getTotalDue(s1));//ex
        //System.out.println("\n\nReceipt:\n" + c.receipt(s1));//ex

        cc.addToCart(s2, fp, 2);
        cc.addToCart(s2, fp, 1);
        cc.addToCart(s1, p, 1);


        //cc.getTotalDue(s1);
        cc.addToCart(s2, fp, 10);
        System.out.println(cc.receipt(s2));
        System.out.println(cc.receipt(s1));
        System.out.println(cc.receipt(s2));
        System.out.println(cc.pay(s2, 26, false));

        cc.addToCart(s2, fp, 2);
        cc.addToCart(s2, fp, 1);
        System.out.println(cc.receipt(s2));
        System.out.println(cc.pay(s2, 26, true));
        System.out.println(s2.getCustomerPoints(cc));

    }
}
