package domainLogic;

import administration.Customer;
import cargo.Hazard;
import cargo.UnitisedCargo;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class UnitisedCargoImpl implements UnitisedCargo {
    private final boolean fragile;
    private final Customer owner;
    private final int storageLocation;
    private final BigDecimal value;
    private final Hazard hazards;
    private Date lastInspectionDate;
    private final Date insertionDate;

    public UnitisedCargoImpl(boolean fragile, Customer owner, int storageLocation,BigDecimal value, Hazard hazards){
        this.fragile = fragile;
        this.owner = owner;
        this.storageLocation = storageLocation;
        this.value = value;
        this.hazards = hazards;
        this.insertionDate = new Date();
        this.lastInspectionDate = null;
    }
    @Override
    public BigDecimal getValue() {
        return value;
    }


    public Duration getDurationOfStorage() {
        long diff = new Date().getTime() - insertionDate.getTime();
        return Duration.ofMillis(diff);
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public int getStorageLocation() {
        return storageLocation;
    }

    @Override
    public Hazard getHazards() {
        return hazards;
    }


    @Override
    public Customer getOwner() {
        return owner;
    }

    @Override
    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    @Override
    public String toString() {
        return "UnitisedCargoImpl{" +
                "fragile=" + fragile +
                ", owner=" + owner +
                ", storageLocation=" + storageLocation +
                ", value=" + value +
                ", hazards=" + hazards +
                ", lastInspectionDate=" + lastInspectionDate +
                ", insertionDate=" + insertionDate +
                '}';
    }
}