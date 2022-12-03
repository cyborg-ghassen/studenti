package com.code2bind.studenti.auth;

import com.code2bind.studenti.managers.BaseManager;

import java.sql.SQLException;

public class ContentType extends BaseManager {
    public ContentType(String name) throws SQLException {
        super(name);
        setApp_name("auth");
        this.fields.put("app_label", "varchar(80)");
        this.fields.put("model", "varchar(80)");
        set = database.createTable(getName(), fields);
        insertValues(this.getApp_name());
    }

    public ContentType() throws SQLException {
        super("contenttype");
        setApp_name("auth");
        this.fields.put("app_label", "varchar(80)");
        this.fields.put("model", "varchar(80)");
        set = database.createTable(getName(), fields);
        insertValues(this.getApp_name());
    }

    void insertValues(String app) throws SQLException {
        values.put("app_label", app);
        values.put("model", this.getName());
        database.InsertData(this.getName(), values);
    }

}
