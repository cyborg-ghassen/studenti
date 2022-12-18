package com.code2bind.studenti.account;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class UserGroups extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public UserGroups(String tableName, String model,  Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "user_id", "id", "account_user");
        database.alterTable(tableName, "group_id", "id", "auth_group");
        values.put("model", model);
        values.put("app_label", "account");
        type.insert("contenttype", values);
    }

    public UserGroups() throws SQLException {
        super("account_usergroups", createFields());
        database.alterTable("account_usergroups", "user_id", "id", "account_user");
        database.alterTable("account_usergroups", "group_id", "id", "auth_group");
        values.put("model", "usergroups");
        values.put("app_label", "account");
        type.insert("contenttype", values);
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint");
        fields.put("group_id", "bigint");
        return fields;
    }
}
