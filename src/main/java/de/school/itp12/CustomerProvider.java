package de.school.itp12;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class CustomerProvider {

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

    public void update(UUID id,Customer customer) {

        Connection conn = getConnection();
        String sql = "UPDATE hausverwaltung.customer\n" +
                "SET FirstName=?, LastName=?, Gender=?, BirthDate=?\n" +
                "WHERE ID=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getGender().name());
            pstmt.setDate(4, Date.valueOf(customer.birthDate));
            pstmt.setString(5, id.toString());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Customer read(UUID id) {

        Connection conn = getConnection();

        Customer result = null;
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM hausverwaltung.customer WHERE ID = " + id.toString();
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                if (rs.next()) {


                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    ICustomer.Gender gender = ICustomer.Gender.valueOf(rs.getString("Gender"));
                    LocalDate birthDate = (rs.getDate("BirthDate").toLocalDate());
                    result=new Customer(id,firstName,lastName,gender,birthDate);


                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }
    public static ArrayList<Customer> readAll() {

        Connection conn = getConnection();

        ArrayList<Customer> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT id FROM hausverwaltung.customer";
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("ID"));
                    Customer customer = read(id);
                    result.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void delete(UUID id) {

        Connection conn = getConnection();

        try (Statement stmt = conn.createStatement()) {
            String deleteSql = "DELETE FROM hausverwaltung.customer WHERE id = " + id.toString();
            System.out.println(deleteSql);
            stmt.execute(deleteSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public  static UUID insert(Customer customer) {

        Connection conn = getConnection();
        String sql = "INSERT INTO hausverwaltung.customer (FirstName, LastName, Gender, BirthDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setDate(3, Date.valueOf(customer.birthDate));
            pstmt.setString(4,customer.getGender().name());

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                UUID newId =UUID.fromString(rs.getString(1)) ;
                return newId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public Object findById(UUID id) {
    }
}
