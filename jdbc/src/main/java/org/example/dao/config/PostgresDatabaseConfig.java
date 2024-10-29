package org.example.dao.config;

import org.example.dao.config.annotation.Configuration;
import org.example.dao.config.exception.PostgresConnectException;

import java.sql.*;

@Configuration
public class PostgresDatabaseConfig implements DatabaseConfig {

    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "RadikDev.6899";
    private static final String DATABASE = "new_postgresql";

    @Override
    public Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DATABASE, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PostgresConnectException("Postgres Database Connection Failed");
        }
    }
}
