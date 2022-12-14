package com.example.housemanagementsystem.database;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnectionManager {

    private static PropertiesConfiguration databaseProperties = new PropertiesConfiguration();
    private static Connection connection;

    private static String password;
    private static String username;
    private static String connectionUrl;

    private DBConnectionManager() {
        updateConnection();
    }

    private static void updateConnection() {
        try {
            if (connectionUrl == null) {
                databaseProperties.load("database.properties");
                String host = databaseProperties.getString("database.host");
                String port = databaseProperties.getString("database.port");
                String dbName = databaseProperties.getString("database.name");

                password = databaseProperties.getString("database.password");
                username = databaseProperties.getString("database.username");
                connectionUrl = host + ":" + port + "/" + dbName + "?serverTimezone=UTC&autoReconnect=true";

                connection = DriverManager.getConnection(connectionUrl, username, password);
            }
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                updateConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}

