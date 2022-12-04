package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.exceptions.ValueError;
import com.code2bind.studenti.fields.CharField;
import com.code2bind.studenti.managers.Model;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class ContentType {

    Database database = new Database();
    ResultSet set;

    public static Dictionary<String, String> fields = new Hashtable<>();
    public static Dictionary<String, String> values = new Hashtable<>();

    public ContentType() throws SQLException {
        String name = "contenttype";
        fields.put("model", "varchar(80) unique");
        fields.put("app_label", "varchar(80)");
        set = database.createTable(name, fields);
        values.put("model", "contenttype");
        values.put("app_label", "initial");
        database.InsertData(name, values);
    }

    public void insert(String name, Dictionary<String, String> values) throws SQLException {
        database.InsertData(name, values);
    }
}
