package com.code2bind.studenti.interfaces;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.exceptions.ValueError;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelManager {
    Database DATABASE = null;

    void save(boolean force_insert, boolean force_update, String using, String update_fields) throws ValueError, SQLException;

    ResultSet delete(String using, boolean keep_parents);

    // TODO: 2022-12-03 will add other methods later

}
