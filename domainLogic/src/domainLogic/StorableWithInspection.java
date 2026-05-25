package domainLogic;

import administration.Storable;
import java.util.Date;

public interface StorableWithInspection extends Storable {
    void setLastInspectionDate(Date date);
    cargo.Hazard getHazards();
}