package com.code2bind.studenti.managers;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.exceptions.ValueError;
import com.code2bind.studenti.interfaces.ModelManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class Model implements ModelManager {
    Database database = new Database();

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private Dictionary<String, String> fields = new Hashtable<>();

    public void setFields(Dictionary<String, String> fields) {
        this.fields = fields;
    }

    public Dictionary<String, String> getFields() {
        return this.fields;
    }

    public Model(String tableName) throws SQLException {

    }

    public Model(String tableName, Dictionary<String, String> fields) throws SQLException {
        this.name = tableName;
        this.fields = fields;
        database.createTable(name, fields);
    }

    @Override
    public ResultSet save(boolean force_insert, boolean force_update, String using, String update_fields) throws ValueError, SQLException, IllegalAccessException {
        if (force_insert && (force_update || !Objects.equals(update_fields, "")))
            throw new ValueError("Cannot force both insert and updating in model saving.");

        if (!Objects.equals(update_fields, "")){
            String[] fields = database.getFields(name);
        }
        return database.createTable(name, fields);
    }

    @Override
    public ResultSet save() throws SQLException {
        return database.createTable(name, fields);
    }

    @Override
    public ResultSet delete(String using, boolean keep_parents) {
        return null;
    }

    public String getAppName(){
        return getClass().getPackageName();
    }
}
