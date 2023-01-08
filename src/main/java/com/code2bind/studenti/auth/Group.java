package com.code2bind.studenti.auth;

import com.code2bind.studenti.managers.Model;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Group extends Model {
    private SimpleStringProperty id;
    private SimpleStringProperty name;

    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public Group(String id, String name) throws SQLException {
        super();
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
    }

    public String getId() {
        return id.get();
    }

    @Override
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Group(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        // insert corresponding values for both contenttype and permissions
        values.put("model", model);
        values.put("app_label", "auth");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "group");
    }

    public Group() throws SQLException {
        super("auth_group", createFields());
        values.put("model", "group");
        values.put("app_label", "auth");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "group");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("name", "varchar(80) unique");
        return fields;
    }
}
