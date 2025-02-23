/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author nhl08
 */
public class TestDB {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=OnlineInterviewScheduleDB;encrypt=true;trustServerCertificate=true;";
        String user = "hlinh";
        String password = "linh0809.";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("✅ Connected to SQL Server successfully!");
            } else {
                System.out.println("❌ Connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
