package com.code2bind.studenti.student;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.managers.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

    private SimpleIntegerProperty id;
    private SimpleStringProperty label;
    private SimpleStringProperty field;
    private SimpleStringProperty level;

    public Class(int id, String label, String field, String level) throws SQLException {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.label = new SimpleStringProperty(label);
        this.field = new SimpleStringProperty(field);
        this.level = new SimpleStringProperty(level);
    }

    public int getId() {
        return id.get();
    }

    public String getLabel() {
        return label.get();
    }

    public String getField() {
        return field.get();
    }

    public String getLevel() {
        return level.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setField(String field) {
        this.field.set(field);
    }

    public void setLevel(String level) {
        this.level.set(level);
    }

    public void setLabel(String label) {
        this.label.set(label);
    }
}
