package de.school.itp12;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import java.time.LocalDate;
import java.util.Properties;
import java.util.UUID;

public class Customer implements ICustomer {
    UUID id;
    String firstName;
    String lastName;
    Gender gender;
    LocalDate birthDate;


    public Customer(UUID id, String firstName, String lastName, Gender gender, LocalDate birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }


    public void update() {

        Connection conn = getConnection();
        String sql = "UPDATE hausverwaltung.customer\n" +
                "SET FirstName=?, LastName=?, Gender=?, BirthDate=?\n" +
                "WHERE ID=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setObject(3,gender );
            pstmt.setDate(4, Date.valueOf(birthDate));
            pstmt.setObject(5, id);
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*public Customer read(int id) {

        Connection conn = getConnection();

        Customer result = null;
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM hausverwaltung.customer WHERE ID = " + id;
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                if (rs.next()) {
                    int personId = rs.getInt("personId");
                    int massnahmeId = rs.getInt("massnahmeId");
                    this.id = (UUID) rs.getObject("ID");
                    this.firstName = rs.getString("firstName");
                    this.lastName =  rs.getString("lastName");
                    this.gender = gender;
                    this.birthDate = birthDate;
                    result = new Teilnehmer(id,personId,massnahmeId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static ArrayList<Teilnehmer> readAll() {

        Connection conn = getConnection();

        ArrayList<Teilnehmer> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT id FROM teilnehmer";
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Teilnehmer teilnehmer = TableTeilnehmer.read(id);
                    result.add(teilnehmer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static void delete(int id) {

        Connection conn = DbManager.getConnection();

        try (Statement stmt = conn.createStatement()) {
            String deleteSql = "DELETE FROM teilnehmer WHERE id = " + id;
            System.out.println(deleteSql);
            stmt.execute(deleteSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

}