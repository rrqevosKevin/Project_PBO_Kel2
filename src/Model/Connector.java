package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static Connection MySQLConfig;
   
    private static final String HOST = "localhost";
    private static final String PORT = "3308"; 
    private static final String DB_NAME = "sihutan";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection configDB() throws SQLException {
        try {
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            MySQLConfig = DriverManager.getConnection(url, USER, PASS);
            
        } catch (SQLException e) {
            System.err.println("koneksi gagal " + e.getMessage());
        }
        
        return MySQLConfig;
     }
}