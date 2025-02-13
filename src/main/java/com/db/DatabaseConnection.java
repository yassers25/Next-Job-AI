package com.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/java_project";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(10); // Maximum connections in the pool
        config.setMinimumIdle(2); // Minimum idle connections in the pool
        config.setIdleTimeout(30000); // 30 seconds idle timeout
        config.setConnectionTimeout(20000); // 20 seconds max wait for a connection
        config.setMaxLifetime(1800000); // 30 minutes max connection lifetime
        config.setDriverClassName("org.postgresql.Driver"); // load driver

        dataSource = new HikariDataSource(config);
    }

    // Get a connection from the pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Close the data source when the application is shutting down
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
