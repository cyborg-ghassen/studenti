package com.code2bind.studenti.account;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Test {
    public static void main(String[] args) throws SQLException {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint");
        fields.put("email", "varchar(100)");
        fields.put("confirmed", "tinyint default 0");
        UserEmail userEmail = new UserEmail("account_useremail", "user_email", fields);
    }
}
