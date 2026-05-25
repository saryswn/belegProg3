package cli;

import cli.events.EventBus;
import domainLogic.AdministrationImpl;

public class CapacityObserver {
    private final AdministrationImpl admin;

    public CapacityObserver(AdministrationImpl admin, EventBus eventBus) {
        this.admin = admin;

    }

    private void check() {
        int capacity = admin.getCapacity();
        int current = admin.listCargo().size();
        double percent = (double) current / capacity * 100;
        if (percent >= 90) {
            System.out.println("WARNUNG: 90% der kapazität erreicht! (" + current + "/" + capacity + ")");
        }
    }
}
