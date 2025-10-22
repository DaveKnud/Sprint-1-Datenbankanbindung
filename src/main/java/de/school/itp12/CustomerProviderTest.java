package de.school.itp12;
import db.MariaDbConnection;
import de.school.itp12.Customer;
import de.school.itp12.CustomerProvider;
import de.school.itp12.ICustomer;
import org.junit.Test;

import java.util.UUID;
import java.util.Properties;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.time.LocalDate;




// Testklasse für CustomerDao
public class CustomerDaoTest {

    // Diese Variablen brauche ich für alle Tests
    static MariaDbConnection db;
    static CustomerProvider dao;
    static Customer c;


    static void start() {
        // 1. db.properties laden
        Properties props = new Properties();
        try (InputStream in = CustomerDaoTest.class.getResourceAsStream("/db.properties")) {
            props.load(in);
        } catch (Exception e) {
            System.out.println("Fehler beim Laden der Properties!");
        }

        // 2. Verbindung öffnen und Tabellen erstellen
        db = new MariaDbConnection();
        db.openConnection(props);
        db.createAllTables();

        // 3. CustomerDao erzeugen
        dao = new CustomerProvider(db);

        // 4. Einen Test-Kunden vorbereiten
        c = new Customer();
        c.setId(UUID.randomUUID());
        c.setFirstName("Lisa");
        c.setLastName("Schmidt");
        c.setGender(ICustomer.Gender.W);
        c.setBirthDate(LocalDate.of(2000, 1, 15));
    }

    // -------------------------
    // TEST 1: Insert Kunde
    // -------------------------
    @Test
    void kunde_einfuegen() {
        boolean ok = dao.insert(c);
        Assertions.assertTrue(ok); // Prüfen, ob es geklappt hat
    }

    // -------------------------
    // TEST 2: Kunde wieder finden
    // -------------------------
    @Test
    void kunde_finden() {
        var found = dao.findById(c.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Lisa", found.getClass());
    }

    // -------------------------
    // TEST 3: Kunde löschen
    // -------------------------
    @Test
    void kunde_loeschen() {
        boolean deleted = dao.delete(c.getId());
        Assertions.assertTrue(deleted);
    }

    @AfterAll
    static void ende() {
        db.truncateAllTables(); // löscht alle Daten aus den Tabellen
        db.closeConnection();
    }
}



