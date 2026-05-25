package domainLogic;

import administration.Customer;
import cargo.Hazard;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


// https://youtu.be/MztH6vkeFVk?si=U4hFUzfeyvfU8U_V
// https://www.baeldung.com/mockito-verify
// https://site.mockito.org/

class AdministrationImplTest {
    @Test
    void addCustomer_returnsTrue() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        boolean result = admin.addCustomer(customer);
        assertTrue(result);

    }
    @Test
    void addCustomer_duplicateReturnsFalse() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);

        boolean result = admin.addCustomer(customer);

        assertFalse(result);
    }

    @Test
    void removeCargo_returnsTrue() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);
        admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        boolean result = admin.removeCargo(1);

        assertTrue(result);
    }

    @Test
    void removeCargo_listIsEmpty() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);
        admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        admin.removeCargo(1);

        assertEquals(0, admin.listCargo().size());
    }

    @Test
    void createDryBulkCargo_capacityExceeded() {
        AdministrationImpl admin = new AdministrationImpl(1);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);
        admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        boolean result = admin.createDryBulkCargo(customer, new BigDecimal("200.00"), Hazard.TOXIC, 3);

        assertFalse(result);
    }

    @Test
    void createDryBulkCargo_customerNotFound() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");

        boolean result = admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        assertFalse(result);
    }

    @Test
    void updateInspectionDate_returnsTrue() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);
        admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        boolean result = admin.updateInspectionDate(1, new Date());

        assertTrue(result);
    }

    @Test
    void updateInspectionDate_notFound() {
        AdministrationImpl admin = new AdministrationImpl(10);

        boolean result = admin.updateInspectionDate(99, new Date());

        assertFalse(result);
    }

    @Test
    void addCustomer_mockitoVerifyName() {
        AdministrationImpl admin = new AdministrationImpl(10);
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Eren Jaeger");

        // erstmal einen echten kunden hinzufügen damit getName() aufgerufen wird beim vergleich
        admin.addCustomer(new CustomerImpl("Eren Jaeger"));
        admin.addCustomer(mockCustomer);

        verify(mockCustomer, atLeastOnce()).getName();
    }

    @Test
    void createDryBulkCargo_mockitoOwnerName() {
        AdministrationImpl admin = new AdministrationImpl(10);
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Mikasa Ackermann");
        admin.addCustomer(mockCustomer);

        admin.createDryBulkCargo(mockCustomer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        verify(mockCustomer, atLeastOnce()).getName();
    }

    @Test
    void createDryBulkCargo_mockitoCargoInList() {
        AdministrationImpl admin = new AdministrationImpl(10);
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Mikasa Ackermann");
        admin.addCustomer(mockCustomer);

        admin.createDryBulkCargo(mockCustomer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);

        assertEquals(1, admin.listCargo().size());
    }
}
