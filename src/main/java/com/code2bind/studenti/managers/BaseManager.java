package com.code2bind.studenti.managers;

import com.code2bind.studenti.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public abstract class BaseManager {
    private String app_name = "";

    public ResultSet set;

    public static Database database;

    private String name;

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_name() {
        return app_name;
    }

    public Dictionary<String, String> fields = new Hashtable<>();
    public Dictionary<String, String> values = new Hashtable<>();

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected BaseManager(String name) throws SQLException {
        setName(name);
    }
}
