package com.code2bind.studenti.fields;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForeignKey extends Field {
    public static Database database;

    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Object to = null;
    public boolean nul = false;

    public Object on_delete = null;

    public boolean unique = false;

    public ForeignKey(Object to, Object on_delete) throws SQLException {
        this.to = to;
        this.on_delete = on_delete;
    }
    ForeignKey(Object to, boolean unique, Object on_delete) throws SQLException {
        this.to = to;
        this.unique = unique;
        this.on_delete = on_delete;
    }

    ForeignKey(Object to, boolean nul, Object on_delete, boolean unique) throws SQLException {
        this.to = to;
        this.nul = nul;
        this.unique = unique;
        this.on_delete = on_delete;
    }

    public static ResultSet getForeignValues(String tableName, String cond) throws SQLException {
        return database.SelectData(tableName, "*", cond);
    }
}
