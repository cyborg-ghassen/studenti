package com.code2bind.studenti.account;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class UserPermissions extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public UserPermissions(String tableName, String model,  Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "user_id", "id", "account_user");
        database.alterTable(tableName, "permission_id", "id", "auth_permission");
        values.put("model", model);
        values.put("app_label", "account");
        type.insert("contenttype", values);
    }

    public UserPermissions() throws SQLException {
        super("account_userpermissions", createFields());
        database.alterTable("account_userpermissions", "user_id", "id", "account_user");
        database.alterTable("account_userpermissions", "permission_id", "id", "auth_permission");
        values.put("model", "userpermissions");
        values.put("app_label", "account");
        type.insert("contenttype", values);
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint");
        fields.put("permission_id", "bigint");
        return fields;
    }
}
