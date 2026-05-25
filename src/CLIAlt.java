import cli.CapacityObserver;
import cli.CLIObserver;
import cli.events.EventBus;
import domainLogic.AdministrationImpl;

// alternativ konfiguriertes CLI
// deaktiviert: Löschen von Kunden, Anzeigen von Gefahrenstoffen

public class CLIAlt {
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
        EventBus eventBus = new EventBus();

        eventBus.subscribe("addCustomer", event -> {
            String name = (String) event.getData();
            boolean result = admin.addCustomer(new domainLogic.CustomerImpl(name));
            System.out.println(result ? "kunde hinzugefuegt: " + name : "kunde existiert bereits!");
        });

        eventBus.subscribe("addDryBulkCargo", event -> {
            String[] parts = (String[]) event.getData();
            String ownerName = parts[1];
            java.math.BigDecimal value = new java.math.BigDecimal(parts[2]);
            cargo.Hazard hazard = parts[3].equals(",") ? null : cargo.Hazard.valueOf(parts[3].toUpperCase());
            int grainSize = parts.length > 4 ? Integer.parseInt(parts[4]) : 1;
            domainLogic.CustomerImpl owner = new domainLogic.CustomerImpl(ownerName);
            boolean result = admin.createDryBulkCargo(owner, value, hazard, grainSize);
            System.out.println(result ? "frachtstueck eingefuegt!" : "fehler!");
            if (result) eventBus.publish("cargoAdded", null);
        }); // Quellen: Claude (claude.ai) 23.05.2026

        // nur ein beobachter aktiv (kein HazardObserver)
        new CapacityObserver(admin, eventBus);
        admin.addObserver(new CLIObserver());

        // CLI mit deaktivierten funktionen starten
        cli.CLI myCLI = new cli.CLI(admin, eventBus, false, false);
        myCLI.start();
    }
}