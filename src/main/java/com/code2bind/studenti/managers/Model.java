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

    public String name;

    public Dictionary<String, String> fields = new Hashtable<>();

    public Model() throws SQLException {}

    public Model(String tableName, Dictionary<String, String> fields) throws SQLException {
        this.name = tableName;
        this.fields = fields;
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
}
