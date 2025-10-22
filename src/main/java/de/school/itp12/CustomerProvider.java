package de.school.itp12;

import db.MariaDbConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class CustomerProvider extends MariaDbConnection {


    public void update(UUID id, Customer customer) {



        Connection conn = getConnection();
        String sql = "UPDATE hausverwaltung.customer " +
                "SET first_name=?, last_name=?, gender=?, birth_date=? " +
                "WHERE id=?;";

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

        try(Statement st=conn.createStatement()) {
            st.executeUpdate("UPDATE FROM hausverwaltung.reading SET customer_id= NULL WHERE customer_id="+id.toString());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Customer result = null;
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM hausverwaltung.customer WHERE id = " + id.toString();
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                if (rs.next()) {


                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    ICustomer.Gender gender = ICustomer.Gender.valueOf(rs.getString("gender"));
                    LocalDate birthDate = (rs.getDate("birth_date").toLocalDate());
                    result = new Customer(id, firstName, lastName, gender, birthDate);


                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    public ArrayList<Customer> readAll() {

        Connection conn = getConnection();

        ArrayList<Customer> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT id FROM hausverwaltung.customer";
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    Customer customer = read(id);
                    result.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void delete(UUID id) {

        Connection conn = getConnection();

        try (Statement stmt = conn.createStatement()) {
            String deleteSql = "DELETE FROM hausverwaltung.customer WHERE id = " + id.toString();
            System.out.println(deleteSql);
            stmt.execute(deleteSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public UUID insert(Customer customer) {

        Connection conn = getConnection();
        String sql = "INSERT INTO hausverwaltung.customer (first_name, last_name, gender, birth_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setDate(3, Date.valueOf(customer.birthDate));
            pstmt.setString(4, customer.getGender().name());

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                UUID newId = UUID.fromString(rs.getString(1));
                return newId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
