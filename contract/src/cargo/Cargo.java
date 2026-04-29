package cargo;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Cargo extends Serializable {
    BigDecimal getValue();
    Hazard getHazards();
}
