package de.school.itp12;

import db.MariaDbConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class ReadingProvider extends MariaDbConnection {


    public void update(UUID id, Reading reading) {

        Connection conn = openConnection();
        String sql = "UPDATE hausverwaltung.reading\n" +
                "SET Comment=?, DateOfReading=?, meterCount=?, meterId=?, " +
                "customer=?, substitute=?, kindOfMeter=?" +
                "WHERE ID=?;";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, reading.getComment());
            pstmt.setDate(2, Date.valueOf(reading.getDateOfReading()));
            pstmt.setDouble(3, reading.getMeterCount());
            pstmt.setString(4, reading.getMeterId());
            pstmt.setObject(5, reading.getCustomer());
            pstmt.setString(7, reading.getKindOfMeter().name());
            pstmt.setString(8, id.toString());
            pstmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
