package de.school.itp12;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class Customer implements ICustomer{
    UUID id;
    String firstName;
    String lastName;
    ICustomer.Gender gender;
    LocalDate birthDate;

    public static Connection getConnection() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/db.properties")) {
            props.load(fis);

            String url = props.getProperty("Schueler.db.url");
            String user = props.getProperty("Schueler.db.user");
            String password = props.getProperty("Schueler.db.pw");

            return DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Property-Datei: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler bei der DB-Verbindung: " + e.getMessage(), e);
        }
    }
    public void insertCustomer(Customer customer){
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ICustomer.Gender getGender() {
        return gender;
    }

    public void setGender(ICustomer.Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


}
