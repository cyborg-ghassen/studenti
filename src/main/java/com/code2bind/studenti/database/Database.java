package com.code2bind.studenti.database;

import java.sql.*;

public class Database {
    static DatabaseConnection connection = new DatabaseConnection();
    static Connection c;

    static String query;

    static {
        try {
            c = connection.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static PreparedStatement statement;

    static {
        try {
            statement = (PreparedStatement) c.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Database() throws SQLException {}

    void createTable(String name) throws SQLException {
        query = "create table ?(" +
                "id bigint not null auto_increment," +
                "constraint ? primary key (id))";
        statement.setString(1, name);
        statement.setString(2, name);
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field, int size) throws SQLException {
        query = "alter table ? add ? varchar(?)";
        statement.setString(1, name);
        statement.setString(2, field);
        statement.setInt(3, size);
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field) throws SQLException {
        query = "alter table ? add ? int";
        statement.setString(1, name);
        statement.setString(2, field);
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field, boolean bool) throws SQLException {
        if (bool){
            query = "alter table ? add ? tinyint";
            statement.setString(1, name);
            statement.setString(2, field);
            statement.execute(query);
            statement.close();
        }
    }

    void InsertData(String name, Array values) throws SQLException {
        query = "insert into ? values ?";
        statement.setString(1, name);
        statement.setArray(2, values);
        statement.execute(query);
        statement.close();
    }

    ResultSet SelectData(String name) throws SQLException {
        ResultSet set;
        query = "select * from ?";
        statement.setString(1, name);
        statement.execute(query);
        set = statement.getResultSet();
        statement.close();
        return set;
    }

    ResultSet SelectData(String name, String value) throws SQLException {
        ResultSet set;
        query = "select ? from ?";
        statement.setString(1, value);
        statement.setString(2, name);
        statement.execute(query);
        set = statement.getResultSet();
        statement.close();
        return set;
    }
}
