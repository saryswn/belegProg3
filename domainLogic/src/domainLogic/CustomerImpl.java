package domainLogic;

import administration.Customer;

public class CustomerImpl implements Customer {
    private String name;
    public CustomerImpl( String name){
        this.name=name;
    }
   public String getName(){
       return name;
   }

    @Override
    public String toString() {
        return name;
    }
}

