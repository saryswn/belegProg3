package cli;

import domainLogic.Observer;

// https://www.baeldung.com/java-observer-pattern
public class CLIObserver implements Observer {

    @Override
    public void update(String eventType, Object data) {
        switch (eventType) {
            case "cargoAdded":
                System.out.println("[INFO] frachtstueck wurde eingefuegt!");
                break;
            case "cargoRemoved":
                System.out.println("[INFO] frachtstueck wurde entfernt! platz: " + data);
                break;
            default:
                System.out.println("[INFO] event: " + eventType);
        }
    }
}