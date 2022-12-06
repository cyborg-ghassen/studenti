package com.code2bind.studenti.auth;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Auth {
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
