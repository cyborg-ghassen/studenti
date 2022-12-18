package com.code2bind.studenti.student;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Class extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public Class(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "user_id", "id", "account_user");
        values.put("model", model);
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }

    public Class() throws SQLException{
        super("student_class", createFields());
        database.alterTable("student_class", "user_id", "id", "account_user");
        values.put("model", "class");
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "class");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint null");
        fields.put("label", "varchar(80)");
        fields.put("level", "varchar(80)");
        fields.put("field", "varchar(80)");
        return fields;
    }
}
