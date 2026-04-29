import administration.Customer;
import domainLogic.AdministrationImpl;
import domainLogic.CustomerImpl;
import cargo.Hazard;
import administration.Storable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GL {
    public static void main(String[] args) {


        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer1 = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer1);
        admin.createDryBulkCargo(customer1, new BigDecimal("100.50"), Hazard.FLAMMABLE, 5);

        CustomerImpl customer2 = new CustomerImpl("Mikasa Ackermann");
        admin.addCustomer(customer2);
        admin.createDryBulkCargo(customer2, new BigDecimal("55.55"), Hazard.TOXIC, 5);

        System.out.println("~~~~~~~~~~~~~~Alle Frachtstücke~~~~~~~~~~~~~~");
        for (Storable a : admin.listCargo()) {
            System.out.println(a);
        }
        // UPDATE
        admin.updateInspectionDate(1, new Date());
        System.out.println("~~~~~~~~~~~~~~Nach Inspektion~~~~~~~~~~~~~~");
        for (Storable s : admin.listCargo()) {
            System.out.println(s);
        }

// DELETE
        admin.removeCargo(2);
        System.out.println("~~~~~~~~~~~~~~Nach Entfernen~~~~~~~~~~~~~~");
        for (Storable s : admin.listCargo()) {
            System.out.println(s);
        }

// Gefahrenstoffe
        System.out.println("~~~~~~~~~~~~~~Vorhandene Gefahrenstoffe~~~~~~~~~~~~~~");
        System.out.println(admin.getPresentHazards());

        System.out.println("~~~~~~~~~~~~~~Nicht vorhandene Gefahrenstoffe~~~~~~~~~~~~~~");
        System.out.println(admin.getAbsentHazards());

// Kunden
        System.out.println("~~~~~~~~~~~~~~Kunden~~~~~~~~~~~~~~");
        admin.getCustomersWithCargoCount().forEach((name, count) ->
                System.out.println(name + ": " + count + " Frachtstücke")
        );



    }
}