package domainLogic;

import administration.Customer;
import cargo.Hazard;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Quellen: Claude (claude.ai) 29.04.2026
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
    void removeCargo_listIsEmpty() {
        AdministrationImpl admin = new AdministrationImpl(10);
        CustomerImpl customer = new CustomerImpl("Eren Jaeger");
        admin.addCustomer(customer);
        admin.createDryBulkCargo(customer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);
        boolean result = admin.removeCargo(1);
        assertTrue(result);
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
    void addCustomer_mockitoVerify() {
        AdministrationImpl admin = new AdministrationImpl(10);
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Eren Jaeger");

        admin.addCustomer(mockCustomer);
        String name = mockCustomer.getName();

        assertEquals("Eren Jaeger", name);
        verify(mockCustomer, atLeastOnce()).getName();
    }
    @Test
    void createDryBulkCargo_mockitoVerify() {
        AdministrationImpl admin = new AdministrationImpl(10);
        Customer mockCustomer = mock(Customer.class);
        when(mockCustomer.getName()).thenReturn("Mikasa Ackermann");

        admin.addCustomer(mockCustomer);
        admin.createDryBulkCargo(mockCustomer, new BigDecimal("100.00"), Hazard.FLAMMABLE, 5);
        String name = admin.listCargo().get(0).getOwner().getName();

        assertEquals("Mikasa Ackermann", name);
        verify(mockCustomer, atLeastOnce()).getName();
    }
}