package com.code2bind.studenti.auth;

import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Group extends Model {
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public Group(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        // insert corresponding values for both contenttype and permissions
        values.put("model", model);
        values.put("app_label", "auth");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "group");
    }

    public Group() throws SQLException {
        super("auth_group", createFields());
        values.put("model", "group");
        values.put("app_label", "auth");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "group");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("name", "varchar(80) unique");
        return fields;
    }
}
