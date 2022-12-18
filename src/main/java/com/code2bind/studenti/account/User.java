package com.code2bind.studenti.account;

import com.code2bind.studenti.auth.ContentType;
import com.code2bind.studenti.auth.Permission;
import com.code2bind.studenti.managers.Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class User extends Model {
    public static Dictionary<String, String> values = new Hashtable<>();
    ContentType type = new ContentType("contenttype", getFields());

    public User(String tableName, String model, Dictionary<String, String> fields) throws SQLException {
        super(tableName, fields);
        values.put("model", model);
        values.put("app_label", "account");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", model);
    }

    public String make_password(String password){
        return get_SHA_256_SecurePassword(password);
    }

    public static String get_SHA_256_SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update("sha256_".getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public User() throws SQLException {
        super("account_user", createFields());
        values.put("model", "user");
        values.put("app_label", "account");
        type.insert("contenttype", values);
        Permission permission = new Permission("auth_permission", "user");
    }

    private static Dictionary<String, String> createFields() {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("username", "varchar(80) unique");
        fields.put("password", "varchar(256)");
        fields.put("first_name", "varchar(80)");
        fields.put("last_name", "varchar(80)");
        fields.put("email", "varchar(100)");
        fields.put("created_at", "datetime DEFAULT CURRENT_TIMESTAMP");
        fields.put("updated_at", "datetime DEFAULT CURRENT_TIMESTAMP");
        fields.put("last_login", "datetime DEFAULT CURRENT_TIMESTAMP");
        fields.put("is_superuser", "tinyint");
        fields.put("is_active", "tinyint");
        fields.put("is_staff", "tinyint");
        fields.put("is_light", "tinyint");
        return fields;
    }
}
