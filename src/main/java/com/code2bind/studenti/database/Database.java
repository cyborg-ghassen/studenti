package com.code2bind.studenti.database;

import java.sql.*;

public class Database {
    static DatabaseConnection connection = new DatabaseConnection();
    static Connection c;

    static String query = "load data LOCAL INFILE NULL INTO TABLE table_name";

    static {
        try {
            c = connection.connect();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static PreparedStatement statement;

    static {
        try {
            statement = c.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Database() throws SQLException {}

    void createTable(String name) throws SQLException {
        query = "create table " + name + " (" +
                "id bigint not null auto_increment," +
                "constraint " + name + "_pk primary key (id))";
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field, int size) throws SQLException {
        query = "alter table " + name + " add " + field + " varchar(" + size + ")";
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field) throws SQLException {
        query = "alter table " + name + " add " + field + " int";
        statement.execute(query);
        statement.close();
    }

    void alterTable(String name, String field, boolean bool) throws SQLException {
        if (bool){
            query = "alter table " + name + " add " + field + " tinyint";
            statement.execute(query);
            statement.close();
        }
    }

    void InsertData(String name, Array values) throws SQLException {
        query = "insert into " + name + " values " + values;
        statement.execute(query);
        statement.close();
    }

    ResultSet SelectData(String name) throws SQLException {
        ResultSet set;
        query = "select * from " + name;
        statement.execute(query);
        set = statement.getResultSet();
        statement.close();
        return set;
    }

    ResultSet SelectData(String name, String value) throws SQLException {
        ResultSet set;
        query = "select " + value + " from " + name;
        statement.execute(query);
        set = statement.getResultSet();
        statement.close();
        return set;
    }
}
