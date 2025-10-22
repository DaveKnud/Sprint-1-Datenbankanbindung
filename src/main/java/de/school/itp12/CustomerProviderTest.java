package de.school.itp12;
import db.MariaDbConnection;
import de.school.itp12.Customer;
import de.school.itp12.CustomerProvider;
import de.school.itp12.ICustomer;
import java.util.UUID;
import java.util.Properties;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.time.LocalDate;




//Testklasse für CustumerProvider
public class CustomerProviderTest {
    static MariaDbConnection db;
    static CustomerProvider costumerProvider;
    static Customer c;

    /* static void start(){
        //proprieties laden
    Properties props = new Properties();
    try (inputStream inputStream = CustomerProvider.class.getResourceAsStream("/db.proprierties")){};
    }
}

     */


