package cli;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cli.events.EventBus;
import domainLogic.AdministrationImpl;
import domainLogic.CustomerImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/ByteArrayOutputStream.html
// https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/PrintStream.html
// https://www.baeldung.com/java-testing-system-out-println

class CLITest {

    @Test
    void start_exitCommand_terminates() {
        AdministrationImpl admin = new AdministrationImpl(10);
        System.setIn(new ByteArrayInputStream(":x\n".getBytes()));

        new CLI(admin, new EventBus()).start();

        assertTrue(true);
    }
    @Test
    void start_addCustomer_customerAdded() {
        AdministrationImpl admin = new AdministrationImpl(10);
        EventBus eventBus = new EventBus();
        eventBus.subscribe("addCustomer", event ->
                admin.addCustomer(new CustomerImpl((String) event.getData()))
        );
        System.setIn(new ByteArrayInputStream(":c\nEren\n:x\n".getBytes()));

        new CLI(admin, eventBus).start();

        assertEquals(1, admin.getCustomersWithCargoCount().size());
    }
    @Test
    void start_addCustomer_outputCorrect() {
        AdministrationImpl admin = new AdministrationImpl(10);
        EventBus eventBus = new EventBus();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outContent);
        System.setOut(printStream);

        eventBus.subscribe("addCustomer", event -> {
            String name = (String) event.getData();
            admin.addCustomer(new CustomerImpl(name));
            printStream.println("kunde hinzugefuegt: " + name);
        });

        System.setIn(new ByteArrayInputStream(":c\nEren\n:x\n".getBytes()));
        new CLI(admin, eventBus).start();

        assertTrue(outContent.toString().contains("kunde hinzugefuegt: Eren"));
    }
}