package db;

import java.sql.*;
import java.util.Properties;

public class MariaDbConnection implements IDatabaseConnection {

    private Connection connection;

    @Override
    public IDatabaseConnection openConnection(Properties props) {
        try {
            String user = props.getProperty(System.getProperty("user.name") + ".db.user");
            String pw   = props.getProperty(System.getProperty("user.name") + ".db.pw");
            String url  = props.getProperty(System.getProperty("user.name") + ".db.url");
            connection = DriverManager.getConnection(url, user, pw);
            System.out.println("Verbunden mit DB");
        } catch (SQLException e) {
            System.out.println("Verbindung fehlgeschlagen: " + e.getMessage());
        }
        return this;
    }

    @Override
    public void createAllTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS customer (
                    id CHAR(36) PRIMARY KEY,
                    first_name VARCHAR(100),
                    last_name VARCHAR(100),
                    gender VARCHAR(2),
                    birth_date DATE
                );
                """);
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reading (
                    id CHAR(36) PRIMARY KEY,
                    comment TEXT,
                    date_of_reading DATE,
                    meter_count DOUBLE,
                    meter_id VARCHAR(50),
                    customer_id CHAR(36),
                    substitute BOOLEAN,
                    kind_of_meter VARCHAR(20),
                    FOREIGN KEY (customer_id) REFERENCES customer(id)
                        ON DELETE SET NULL
                );
                """);
            System.out.println("Tabellen erstellt");
        } catch (SQLException e) {
            System.out.println("Tabellenfehler: " + e.getMessage());
        }
    }

    @Override
    public void truncateAllTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE reading;");
            stmt.execute("TRUNCATE TABLE customer;");
            System.out.println("Tabellen geleert");
        } catch (SQLException e) {
            System.out.println("Fehler beim Leeren: " + e.getMessage());
        }
    }

    @Override
    public void removeAllTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS reading;");
            stmt.execute("DROP TABLE IF EXISTS customer;");
            System.out.println("Tabellen gelöscht");
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen: " + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) connection.close();
            System.out.println("Verbindung geschlossen");
        } catch (SQLException e) {
            System.out.println("Fehler beim Schließen: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
