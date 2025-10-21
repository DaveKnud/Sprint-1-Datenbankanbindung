package db;

import java.util.Properties;

interface IDatabaseConnection {
    IDatabaseConnection openConnection(Properties properties);
    void createAllTables();
    void truncateAllTables();
    void removeAllTables();
    void closeConnection();
}