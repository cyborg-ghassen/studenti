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

public class Absence extends Model {
    Database database = new Database();
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    private SimpleIntegerProperty id;
    private SimpleStringProperty user;
    private SimpleStringProperty material;
    private SimpleStringProperty date;
    private SimpleStringProperty lesson;

    public Absence(int id, String username, String label, String date, String lesson) throws SQLException {
        super();
        this.id = new SimpleIntegerProperty(id);
        this.user = new SimpleStringProperty(username);
        this.material = new SimpleStringProperty(label);
        this.date = new SimpleStringProperty(date);
        this.lesson = new SimpleStringProperty(lesson);
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setLesson(String lesson) {
        this.lesson.set(lesson);
    }

    public void setMaterial(String material) {
        this.material.set(material);
    }

    public int getId() {
        return id.get();
    }

    public String getUser() {
        return user.get();
    }

    public String getDate() {
        return date.get();
    }

    public String getLesson() {
        return lesson.get();
    }

    public String getMaterial() {
        return material.get();
    }

    public Absence(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        database.alterTable(tableName, "user_id", "id", "account_user");
        database.alterTable(tableName, "material_id", "id", "student_material");
        values.put("model", model);
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }

    public Absence() throws SQLException {
        super("student_absence", createFields());
        database.alterTable("student_absence", "user_id", "id", "account_user");
        database.alterTable("student_absence", "material_id", "id", "student_material");
        values.put("model", "absence");
        values.put("app_label", "student");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "absence");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint null");
        fields.put("material_id", "bigint");
        fields.put("lesson", "varchar(80)");
        fields.put("date", "date");
        return fields;
    }
}
