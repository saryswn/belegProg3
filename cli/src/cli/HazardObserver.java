package cli;

import cargo.Hazard;
import domainLogic.AdministrationImpl;
import domainLogic.Observer;

import java.util.Set;

public class HazardObserver implements Observer {
    private final AdministrationImpl admin;
    private Set<Hazard> lastKnownHazards;

    public HazardObserver(AdministrationImpl admin) {
        this.admin = admin;
        this.lastKnownHazards = admin.getPresentHazards();
    }

    @Override
    // Quelle: Claude (claude.ai) 25.05.2026
    public void update(String eventType, Object data) {
        if (eventType.equals("cargoAdded") || eventType.equals("cargoRemoved")) {
            Set<Hazard> currentHazards = admin.getPresentHazards();
            if (!currentHazards.equals(lastKnownHazards)) {
                System.out.println("[HAZARD] gefahrenstoffe geaendert: " + currentHazards);
                lastKnownHazards = currentHazards;
            }
        }
    }
}