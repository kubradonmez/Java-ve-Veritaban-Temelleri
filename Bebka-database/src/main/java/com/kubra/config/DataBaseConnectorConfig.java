package com.kubra.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectorConfig {
    private static Connection connection;

    public static void setConnection(){
        try{
            connection = DriverManager.getConnection(
                    DataBaseConfig.DATABASE_URL,
                    DataBaseConfig.DATABASE_USERNAME,
                    DataBaseConfig.DATABASE_PASSWORD);
            System.out.println("Connected!");
        }catch (SQLException e){
            System.out.println("Connection failed!");
            System.out.println("Hata: " + e.getMessage());
            e.printStackTrace(); // Hatanın detayını görebilmemiz için
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection(){
        try{
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
