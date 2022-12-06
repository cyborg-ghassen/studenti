package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class GroupPermissions extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public GroupPermissions(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "group_id", "id", "auth_group");
        database.alterTable(tableName, "permission_id", "id", "auth_permission");
        values.put("model", model);
        values.put("app_label", "auth");
        type.insert("contenttype", values);
    }
}
