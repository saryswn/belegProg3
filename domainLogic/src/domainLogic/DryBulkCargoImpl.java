package domainLogic;

import administration.Customer;
import cargo.DryBulkCargo;
import cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;

public class DryBulkCargoImpl implements DryBulkCargo, Inspectable, StorableWithInspection{
   private final int grainSize;
   private final Customer owner;
   private final int storageLocation;
   private final BigDecimal value;
   private final Hazard hazards;
   private Date lastInspectionDate;
   private final Date insertionDate;
    public DryBulkCargoImpl(int grainSize, Customer owner,int storageLocation, BigDecimal value, Hazard hazards) {
        this.grainSize = grainSize;
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
       long diff = new Date().getTime() - insertionDate.getTime(); // .getTime() = wandelt das Datum um in Millisekunden
       return Duration.ofMillis(diff); // wandelt die Millisekunden in eine lesbare Duration um
    }

    public int getGrainSize() {
        return grainSize;
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
        return "DryBulkCargoImpl{" +
                ", GrainSize=" + grainSize +
                ", owner=" + owner +
                ", storageLocation=" + storageLocation +
                ", value=" + value +
                ", hazards=" + hazards +
                ", lastInspectionDate=" + lastInspectionDate +
                '}';
    }
}