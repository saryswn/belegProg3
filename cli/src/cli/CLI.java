package cli;

import administration.Storable;
import cli.events.EventBus;
import domainLogic.AdministrationImpl;
import domainLogic.CustomerImpl;

import java.util.Scanner;

// Quellen: Claude (claude.ai) 23.05.2026
// https://www.baeldung.com/java-observer-pattern
public class CLI {
    private final AdministrationImpl admin;
    private final Scanner scanner;
    private final EventBus eventBus;
    private final boolean deleteCustomerEnabled;
    private final boolean hazardsEnabled;
    private String modus = "";

    public CLI(AdministrationImpl admin, EventBus eventBus) {
        this(admin, eventBus, true, true);
    }

    public CLI(AdministrationImpl admin, EventBus eventBus, boolean deleteCustomerEnabled, boolean hazardsEnabled) {
        this.admin = admin;
        this.scanner = new Scanner(System.in);
        this.eventBus = eventBus;
        this.deleteCustomerEnabled = deleteCustomerEnabled;
        this.hazardsEnabled = hazardsEnabled;
    }

    public void start() {
        System.out.println("CLI gestartet. :x zum Beenden");
        String input = "";

        while (!input.equals(":x")) {
            System.out.print("> ");
            input = scanner.nextLine().trim();
            process(input);
        }
    }

    private void process(String input) {
        switch (input) {
            case ":x":
                System.out.println("tschüss!");
                break;
            case ":c":
                modus = "einfuegen";
                System.out.println("-- Einfügemodus --");
                break;
            case ":r":
                modus = "anzeigen";
                System.out.println("-- Anzeigemodus --");
                break;
            case ":u":
                modus = "aendern";
                System.out.println("-- Änderungsmodus --");
                break;
            case ":d":
                modus = "loeschen";
                System.out.println("-- Löschmodus --");
                break;
            default:
                handleModus(input);
        }
    }

    private void handleModus(String input) {
        switch (modus) {
            case "einfuegen":
                handleEinfuegen(input);
                break;
            case "anzeigen":
                handleAnzeigen(input);
                break;
            case "aendern":
                handleAendern(input);
                break;
            case "loeschen":
                handleLoeschen(input);
                break;
            default:
                System.out.println("erst einen modus waehlen! (:c :r :u :d)");
        }
    }

    private void handleEinfuegen(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            eventBus.publish("addCustomer", parts[0]);
        } else if (parts[0].equals("DryBulkCargo")) {
            eventBus.publish("addDryBulkCargo", parts);
        } else {
            System.out.println("unbekannter befehl");
        }
    }

    private void handleAnzeigen(String input) {
        if (input.equals("cargos")) {
            for (Storable s : admin.listCargo()) {
                System.out.println(s);
            }
        } else if (input.equals("customers")) {
            admin.getCustomersWithCargoCount().forEach((name, count) ->
                    System.out.println(name + ": " + count + " Frachtstuecke")
            );
        } else if (input.equals("hazards i") && hazardsEnabled) {
            System.out.println(admin.getPresentHazards());
        } else if (input.equals("hazards e") && hazardsEnabled) {
            System.out.println(admin.getAbsentHazards());
        } else if (input.startsWith("hazards") && !hazardsEnabled) {
            System.out.println("funktion deaktiviert!");
        } else {
            System.out.println("unbekannter befehl");
        }
    }

    private void handleAendern(String input) {
        try {
            int location = Integer.parseInt(input);
            boolean result = admin.updateInspectionDate(location, new java.util.Date());
            System.out.println(result ? "inspektionsdatum gesetzt!" : "nicht gefunden!");
        } catch (NumberFormatException e) {
            System.out.println("bitte lagerplatz eingeben!");
        }
    }

    private void handleLoeschen(String input) {
        try {
            int location = Integer.parseInt(input);
            boolean result = admin.removeCargo(location);
            System.out.println(result ? "frachtstueck entfernt!" : "nicht gefunden!");
        } catch (NumberFormatException e) {
            if (deleteCustomerEnabled) {
                CustomerImpl customer = new CustomerImpl(input);
                boolean result = admin.removeCustomer(customer);
                System.out.println(result ? "kunde geloescht!" : "kunde nicht gefunden!");
            } else {
                System.out.println("funktion deaktiviert!");
            }
        }
    }
}