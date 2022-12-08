package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Auth {
    static Database database;

    static {
        try {
            database = new Database();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet login(String username, String password) throws SQLException {
        return database.SelectData("account_user", "*", "username=" + username + " and password=" + password);
    }

    public static void main(String[] args) {
        try{
            Dictionary<String, String> fields = new Hashtable<>();
            fields.put("permission_id", "bigint");
            fields.put("group_id", "bigint");
//            Permission = new Permission("auth_permission", "permission", fields);
            GroupPermissions groupPermissions = new GroupPermissions("auth_group_permission", "group_permission", fields);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
