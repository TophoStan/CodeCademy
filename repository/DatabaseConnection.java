package repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.crypto.Data;

/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 * 
 * @author www.codejava.net
 *
 */

public class DatabaseConnection {
    private Connection conn;

    public DatabaseConnection() {
        this.conn = null;
    }

    public void connect() {
        System.out.println("Trying to connect to the database");
        try {
            System.out.println("Connection succesfull");

            String dbURL = "jdbc:sqlserver://aei-sql2.avans.nl\\studenten:1443;databaseName=CodeCademy7";
            String user = "group7";
            String pass = "groepje7";
            this.conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } //finally {
        //     try {
        //         if (conn != null && !conn.isClosed()) {
        //             conn.close();
        //         }
        //     } catch (SQLException ex) {
        //         ex.printStackTrace();
        //     }
        // }

    }

    public void viewTable() {
        String query = "select EmployeeId, Name from Employee";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String employeeName = rs.getString("Name");
                int employeeID = rs.getInt("EmployeeId");
                
                System.out.println(employeeID + ", " + employeeName);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
