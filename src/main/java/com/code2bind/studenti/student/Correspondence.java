package com.code2bind.studenti.student;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Correspondence extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());
    public Correspondence(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "user_id", "id", "account_user");
        database.alterTable(tableName, "material_id", "id", "student_material");
        database.alterTable(tableName, "class_id", "id", "student_class");
        values.put("model", model);
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }

    public Correspondence() throws SQLException {
        super("student_correspondence", createFields());
        database.alterTable("student_correspondence", "user_id", "id", "account_user");
        database.alterTable("student_correspondence", "material_id", "id", "student_material");
        database.alterTable("student_correspondence", "class_id", "id", "student_class");
        values.put("model", "correspondence");
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "correspondence");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint");
        fields.put("material_id", "bigint");
        fields.put("class_id", "bigint");
        return fields;
    }
}
