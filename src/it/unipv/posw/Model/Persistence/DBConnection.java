package it.unipv.posw.Model.Persistence;

import java.io.FileInputStream;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private final String PROPERTYDBDRIVER = "db.driver";
    private final String PROPERTYDBURL = "db.url";
    private final String PROPERTYNAME = "db.username";
    private final String PROPERTYPSW = "db.password";
    private String username;
    private String password;
    private String dbDriver;
    private String dbURL;
    private Connection c;

    // Costruttore privato: carica le properties
    private DBConnection() {
    	Properties p = new Properties(System.getProperties());
        try {
            p.load(new FileInputStream("Properties/properties"));
            username = p.getProperty(PROPERTYNAME);
            password = p.getProperty(PROPERTYPSW);
            dbDriver = p.getProperty(PROPERTYDBDRIVER);
            dbURL = p.getProperty(PROPERTYDBURL);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Punto di accesso Singleton
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection startConnection() throws SQLException {
        	
    	try {
    		dbURL = String.format(dbURL);
    		Class.forName(dbDriver);
            c = DriverManager.getConnection(dbURL, username, password);
            System.out.println("connessione aperta");
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return c;
    }

    public void closeConnection(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
                System.out.println("connessione chiusa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}