package com.code2bind.studenti.auth;

import java.sql.SQLException;

public class Auth {
    public static void main(String[] args) {
        try{
            Permission permission = new Permission();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
