package com.code2bind.studenti.managers;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.interfaces.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Session implements User {
    Database database = new Database();

    ResultSet set;

    int key;

    String username;

    public Session() throws SQLException {
        Random random = new Random(256);
        key = random.nextInt();
    }

    public Session(String username, String password) throws SQLException {
        Random random = new Random(256);
        key = random.nextInt();
        set = database.SelectData("account_user", "*", "username=" + username + " and password=" + password);
        this.username = set.getString("username");
    }

    @Override
    public void setPassword(String password) throws SQLException {
        set = database.updateData("account_user", "password", password, "username=" + username);
    }

    @Override
    public void getForeignUniqueData() {

    }

    @Override
    public List<String> getForeignFields() {
        return null;
    }

    @Override
    public boolean is_authenticated() {
        return true;
    }

    @Override
    public boolean is_superuser() {
        return false;
    }

    @Override
    public boolean is_active() {
        return false;
    }

    @Override
    public boolean is_staff() {
        return false;
    }

    @Override
    public String username() {
        return null;
    }

    @Override
    public String first_name() {
        return null;
    }

    @Override
    public String last_name() {
        return null;
    }

    @Override
    public String avatar() {
        return null;
    }
}
