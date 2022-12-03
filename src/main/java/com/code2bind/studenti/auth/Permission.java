package com.code2bind.studenti.auth;

import com.code2bind.studenti.managers.BaseManager;

import java.sql.SQLException;

public class Permission {

    String[] crud = {"view", "add", "update", "delete"};

    ContentType type = new ContentType("auth_permission");

    public Permission() throws SQLException {
        type.setName("permission");
        type.fields.put("name", "varchar(80)");
        type.fields.put("contenttype_id", "bigint");
        type.fields.put("codename", "varchar(80)");
        this.insertValues();
    }

    void insertValues() throws SQLException {
        for (String s : crud) {
            type.values.put("name", "Can " + s + " " + type.getName());
            type.values.put("contenttype_id", String.valueOf(type.set.getString(0)));
            type.values.put("codename", s + "_" + type.getName());
            BaseManager.database.InsertData(type.getName(), type.values);
        }
    }

}
