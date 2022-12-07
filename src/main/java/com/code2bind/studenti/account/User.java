package com.code2bind.studenti.account;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class User extends Model {
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public User(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        values.put("model", model);
        values.put("app_label", "account");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }
}
