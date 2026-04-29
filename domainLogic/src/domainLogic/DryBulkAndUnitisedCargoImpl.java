package domainLogic;

import administration.Customer;
import cargo.DryBulkAndUnitisedCargo;
import cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

public class DryBulkAndUnitisedCargoImpl implements DryBulkAndUnitisedCargo {
    private final Customer owner;
    private final BigDecimal value;
    private final Hazard hazards;
    private final int grainSize;
    private final boolean fragile;
    private final int storageLocation;
    private Date lastInspectionDate;
    private final Date insertionDate;

    public DryBulkAndUnitisedCargoImpl(Customer owner, BigDecimal value,
                                       Hazard hazards, int grainSize,
                                       boolean fragile, int storageLocation) {
        this.owner = owner;
        this.value = value;
        this.hazards = hazards;
        this.grainSize = grainSize;
        this.fragile = fragile;
        this.storageLocation = storageLocation;
        this.insertionDate = new Date();
        this.lastInspectionDate = null;
    }

    @Override
    public Customer getOwner() { return owner; }

    @Override
    public Duration getDurationOfStorage() {
        long diff = new Date().getTime() - insertionDate.getTime();
        return Duration.ofMillis(diff);
    }

    @Override
    public Date getLastInspectionDate() { return lastInspectionDate; }

    public void setLastInspectionDate(Date date) {
        this.lastInspectionDate = date;
    }

    @Override
    public int getStorageLocation() { return storageLocation; }

    @Override
    public BigDecimal getValue() { return value; }

    @Override
    public Hazard getHazards() { return hazards; }

    @Override
    public int getGrainSize() { return grainSize; }

    @Override
    public boolean isFragile() { return fragile; }

    @Override
    public String toString() {
        return "DryBulkAndUnitisedCargoImpl{" +
                "fragile=" + fragile +
                ", owner=" + owner +
                ", value=" + value +
                ", hazards=" + hazards +
                ", grainSize=" + grainSize +
                ", storageLocation=" + storageLocation +
                ", lastInspectionDate=" + lastInspectionDate +
                ", insertionDate=" + insertionDate +
                '}';
    }
}