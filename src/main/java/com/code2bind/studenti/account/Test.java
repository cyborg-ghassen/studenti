package com.code2bind.studenti.account;

import com.code2bind.studenti.auth.GroupPermissions;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Test {
    public static void main(String[] args) throws SQLException {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("username", "varchar(80) unique not null");
        fields.put("avatar", "longblob");
        fields.put("phone", "int");
        fields.put("created_at", "datetime default CURRENT_TIMESTAMP not null");
        fields.put("updated_at", "datetime default CURRENT_TIMESTAMP not null");
        fields.put("last_login", "datetime default CURRENT_TIMESTAMP not null");
        fields.put("first_name", "varchar(80)");
        fields.put("last_name", "varchar(80)");
        User user = new User("account_user", "user", fields);
    }
}
