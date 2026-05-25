import cli.CapacityObserver;
import cli.events.EventBus;
import domainLogic.AdministrationImpl;
import cli.CLIObserver;
import cli.HazardObserver;

public class CLIMain {
    public static void main(String[] args) {
        int capacity = 10;
        if (args.length > 0) {
            try {
                capacity = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("ungueltige kapazitaet, nehme 10");
            }
        }

        AdministrationImpl admin = new AdministrationImpl(capacity);
        admin.addObserver(new CLIObserver());
        admin.addObserver(new HazardObserver(admin));
        EventBus eventBus = new EventBus();

        // events registrieren
        eventBus.subscribe("addCustomer", event -> {
            String name = (String) event.getData();
            boolean result = admin.addCustomer(new domainLogic.CustomerImpl(name));
            System.out.println(result ? "kunde hinzugefuegt: " + name : "kunde existiert bereits!");
        });
// Quellen: Claude (claude.ai) 24.05.2026
        eventBus.subscribe("addDryBulkCargo", event -> {
            String[] parts = (String[]) event.getData();
            String ownerName = parts[1];
            java.math.BigDecimal value = new java.math.BigDecimal(parts[2]);
            cargo.Hazard hazard = parts[3].equals(",") ? null : cargo.Hazard.valueOf(parts[3].toUpperCase());
            int grainSize = parts.length > 4 ? Integer.parseInt(parts[4]) : 1;
            domainLogic.CustomerImpl owner = new domainLogic.CustomerImpl(ownerName);
            boolean result = admin.createDryBulkCargo(owner, value, hazard, grainSize);
            System.out.println(result ? "frachtstueck eingefuegt!" : "fehler! kunde existiert nicht oder lager voll");
            if (result) eventBus.publish("cargoAdded", null);
        });

        // observer registrieren
        new CapacityObserver(admin, eventBus);

        cli.CLI myCLI = new cli.CLI(admin, eventBus);
        myCLI.start();
    }
}