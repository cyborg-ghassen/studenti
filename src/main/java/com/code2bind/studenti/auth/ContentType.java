package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.fields.CharField;
import com.code2bind.studenti.managers.Model;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ContentType extends Model {

    Database database = new Database();

    public ContentType(String name, Dictionary<String, String> fields) throws SQLException {
        super(name, fields);
//        Dictionary<String, String> values = new Hashtable<>();
//        values.put("model", name);
//        values.put("app_label", "initial");
//        insert(name, values);
    }

    public ContentType() throws SQLException {
        super("contenttype", createFields());
    }
    public void insert(String name, Dictionary<String, String> values) throws SQLException {
        database.InsertData(name, values);
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("model", "varchar(80) unique");
        fields.put("app_label", "varchar(80)");
        return fields;
    }
}
