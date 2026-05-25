package domainLogic;

import administration.Customer;
import administration.Storable;
import cargo.Hazard;

import java.util.*;
import cargo.Cargo;
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html
// https://www.baeldung.com/java-hashset
// https://stackoverflow.com/questions/18448671/how-to-use-removeif-in-java
public class AdministrationImpl {
    private final int capacity;
    private final List<Customer> customerList = new ArrayList<>();
    private final List<StorableWithInspection> cargoList = new ArrayList<>();


    public AdministrationImpl(int capacity) {
        this.capacity = capacity;
    }
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String eventType, Object data) {
        for (Observer o : observers) {
            o.update(eventType, data);
        }
    }

    // -----------------------CUSTOMER---------------------------
    public boolean addCustomer(Customer customer) {
        for (Customer a : customerList) {
            if (a.getName().equals(customer.getName())) {
                return false; // Name existiert bereits
            }
        }
        customerList.add(customer); // Name existiert nicht → hinzufügen
        return true;
    }

    public boolean removeCustomer(Customer customer) {
        for (Customer a : customerList) {
            if (a.getName().equals(customer.getName())) {
                customerList.remove(a);
                return true; // gefunden und gelöscht
            }
        }
        return false; // nicht gefunden
    }

    public Map<String, Long> getCustomersWithCargoCount() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Customer a : customerList) { // alle Kunden
            long count = 0;
            for (Storable s : cargoList) { //Alle Freachtstuecke
                if (s.getOwner().getName().equals(a.getName())) { // wenn das Freachtstueck zu Kunde gehört count++
                    count++;
                }
            }
            result.put(a.getName(), count); // Name und Anzahl speichern
        }
        return result;

    }
    // ---------------------HILFSMETHODEN-----------------------

    private boolean customerExists(Customer owner) {
        for (Customer c : customerList) {
            if (c.getName().equals(owner.getName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isWarehouseFull() {
        return cargoList.size() >= this.capacity;
    }

    private int nextFreeStorageLocation() {
        Set<Integer> usedLocations = new HashSet<>();
        for (Storable a : cargoList) {
            usedLocations.add(a.getStorageLocation());
        }
        int location = 1;
        while (usedLocations.contains(location)) {
            location++;
        }
        return location;
    }


    // ---------------------Fraechtstueke-----------------------

    public boolean createDryBulkCargo(Customer owner, java.math.BigDecimal value, Hazard hazards, int grainSize) {
        if (isWarehouseFull()) return false; //Hilfsmethode
        if (!customerExists(owner)) return false; // Hilfsmethode
        int location = nextFreeStorageLocation();
        DryBulkCargoImpl cargo = new DryBulkCargoImpl(grainSize, owner, location, value, hazards);
        cargoList.add(cargo);
        notifyObservers("cargoAdded", cargo);
        return true;
    }

    public boolean createUnitisedCargo(Customer owner, java.math.BigDecimal value, Hazard hazards, boolean fragile) {
        if (isWarehouseFull()) return false; //Hilfsmethode
        if (!customerExists(owner)) return false; // Hilfsmethode
        int location = nextFreeStorageLocation();
        UnitisedCargoImpl cargo = new UnitisedCargoImpl(fragile, owner, location, value, hazards);
        cargoList.add(cargo);
        notifyObservers("cargoAdded", cargo);
        return true;
    }

    public boolean createDryBulkAndUnitisedCargo(Customer owner, java.math.BigDecimal value, Hazard hazards,
                                                 int grainSize, boolean fragile) {
        if (cargoList.size() >= capacity) return false;
        if (!customerExists(owner)) return false;

        int location = nextFreeStorageLocation();
        DryBulkAndUnitisedCargoImpl cargo = new DryBulkAndUnitisedCargoImpl(owner, value, hazards, grainSize, fragile, location);
        cargoList.add(cargo);
        notifyObservers("cargoAdded", cargo);
        return true;
    }


    public boolean removeCargo(int storageLocation) {
        boolean removed = cargoList.removeIf(a -> a.getStorageLocation() == storageLocation);
        if (removed) {
            notifyObservers("cargoRemoved", storageLocation);
        }
        return removed;
    }

    public boolean updateInspectionDate(int storageLocation, Date newDate) {
        for (StorableWithInspection a : cargoList) {
            if (a.getStorageLocation() == storageLocation) {
                a.setLastInspectionDate(newDate);
                return true;
            }
        }
        return false;
    }

    public List<Storable> listCargo(String typeName) {
        List<Storable> result = new ArrayList<>();
        for (Storable a : cargoList) {
            if (a.getClass().getSimpleName().equals(typeName)) {
                result.add(a);
            }
        }
        return result;
    }

    public List<Storable> listCargo() {
        List<Storable> result = new ArrayList<>();
        for (Storable a : cargoList) {
            result.add(a);
        }
        return result;
    }

    public Set<Hazard> getPresentHazards() {
        Set<Hazard> hazards = new HashSet<>();
        for (StorableWithInspection a : cargoList) {
            hazards.add(a.getHazards());
        }
        return hazards;
    }

    public Set<Hazard> getAbsentHazards() {
        Set<Hazard> all = new HashSet<>(Arrays.asList(Hazard.values()));
        all.removeAll(getPresentHazards());
        return all;
    }

            public int getCapacity () {
                return capacity;
            }

        }





