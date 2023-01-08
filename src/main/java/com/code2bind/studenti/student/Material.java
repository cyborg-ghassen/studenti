package com.code2bind.studenti.student;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.managers.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Material extends Model {
    private SimpleIntegerProperty id;
    private SimpleStringProperty label;
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());
    public Material(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        values.put("model", model);
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }

    public Material() throws SQLException {
        super("student_material", createFields());
        values.put("model", "material");
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "material");
    }

    public Material(int id, String label) throws SQLException {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.label = new SimpleStringProperty(label);
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("label", "varchar(80)");
        return fields;
    }

    public int getId() {
        return id.get();
    }

    public String getLabel() {
        return label.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setLabel(String label) {
        this.label.set(label);
    }
}
