package de.school.itp12;

import db.MariaDbConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;
import db.MariaDbConnection;
public class ReadingProvider extends  MariaDbConnection {


    public void update(UUID id, Reading reading) {

        Connection connection = getConnection();
        String sql = "UPDATE hausverwaltung.reading\n" +
                "SET comment=?, date_of_reading=?, meter_count=?, meter_id=?, " +
                "customer_id=?, substitute=?, kind_of_meter=?" +
                "WHERE id=?;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reading.getComment());
            pstmt.setDate(2, Date.valueOf(reading.getDateOfReading()));
            pstmt.setDouble(3, reading.getMeterCount());
            pstmt.setString(4, reading.getMeterId());
            pstmt.setString(5, reading.getCustomer().id.toString());
            pstmt.setString(7, reading.getKindOfMeter().name());
            pstmt.setString(8, id.toString());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  Reading read(UUID id) {

        Connection conn = getConnection();

        Reading result = null;
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM hausverwaltung.reading WHERE id = " + id.toString();
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                if (rs.next()) {
                    String comment= rs.getString("comment");
                    LocalDate dateOfReading= rs.getDate("date_of_reading").toLocalDate();
                    Double meterCount= rs.getDouble("meter_count");
                    String meterId=rs.getString("meter_id");
                    Customer customer = CustomerProvider.read(UUID.fromString(rs.getString("customer_id") ));
                    boolean substitute=rs.getBoolean("substitute");
                    IReading.KindOfMeter kindOfMeter=IReading.KindOfMeter.valueOf(rs.getString("kind_of_meter"));
                    result=new Reading(id,comment,dateOfReading,meterCount,meterId,customer,substitute,kindOfMeter);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }
    public ArrayList<Reading> readAll() {

        Connection conn = getConnection();

        ArrayList<Reading> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT id FROM hausverwaltung.reading";
            try (ResultSet rs = stmt.executeQuery(selectSql)) {
                while (rs.next()) {
                    UUID id = UUID.fromString(rs.getString("id"));
                    Reading reading= read(id);
                    result.add(reading);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public  void delete(UUID id) {

        Connection conn = getConnection();


        try (Statement stmt = conn.createStatement()) {
            String deleteSql = "DELETE FROM hausverwaltung.reading WHERE id = " + id.toString();
            System.out.println(deleteSql);
            stmt.execute(deleteSql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public UUID insert(Reading reading) {

        Connection conn = getConnection();
        String sql = "INSERT INTO hausverwaltung.reading (comment, date_of_reading, meter_count, meter_id, " +
                "customer_id, substitute, kind_of_meter) " +
                "VALUES (?, ?, ?, ? ,? ,?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reading.getComment());
            pstmt.setDate(2, Date.valueOf(reading.getDateOfReading()));
            pstmt.setDouble(3, reading.getMeterCount());
            pstmt.setString(4, reading.getMeterId());
            pstmt.setString(5, reading.getCustomer().id.toString());
            pstmt.setString(7, reading.getKindOfMeter().name());
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
