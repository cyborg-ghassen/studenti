package com.code2bind.studenti.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    Dotenv dotenv = Dotenv.load();
    private final String user = dotenv.get("MYSQL_USER");
    private final String password = dotenv.get("MYSQL_PASSWORD");
    private final String host = dotenv.get("MYSQL_HOST") + dotenv.get("MYSQL_DATABASE");
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(host, user, password);
    }
}
