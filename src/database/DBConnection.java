package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static Connection con;

    public static Connection getConnection() {

        String jdbcURL = "jdbc:mysql://localhost:3306/hospital_queue_system";
        String dbusername = "root";
        String dbpassword = "Chanchal@SQL2026";
        

        try {

            con = DriverManager.getConnection(
                    jdbcURL,
                    dbusername,
                    dbpassword
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}