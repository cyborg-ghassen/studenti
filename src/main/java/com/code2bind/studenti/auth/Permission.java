package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Permission {
    Database database = new Database();
    ResultSet set;

    ContentType type = new ContentType();

    public static Dictionary<String, String> fields = new Hashtable<>();
    public static Dictionary<String, String> values = new Hashtable<>();

    public Permission() throws SQLException {
        String name = "permission";
        fields.put("name", "varchar(80) unique");
        fields.put("contenttype_id", "bigint");
        fields.put("codename", "varchar(80)");
        set = database.createTable(name, fields);
        // alter foreign key for contenttype
        database.alterTable(name, "contenttype_id", "id", "contenttype");
        // insert corresponding values for both contenttype and permissions
        values.put("model", name);
        values.put("app_label", "auth");
        type.insert("contenttype", values);
    }
}
