package com.code2bind.studenti.account;

import com.code2bind.studenti.managers.Session;

import java.sql.SQLException;

public class Account extends Session {
    public Account(String username, String password) throws SQLException {
        super(username, password);
    }
}
