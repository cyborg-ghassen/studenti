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
}
