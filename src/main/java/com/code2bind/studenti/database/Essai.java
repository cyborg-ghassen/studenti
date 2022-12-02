package com.code2bind.studenti.database;

import java.sql.SQLException;

public class Essai {
    public static void main(String[] args){
        try {
            Database database = new Database();
            System.out.println(database);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
