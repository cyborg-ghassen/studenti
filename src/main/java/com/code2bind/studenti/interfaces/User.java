package com.code2bind.studenti.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface User {
    void setPassword(String password) throws SQLException;

    void getForeignUniqueData();

    List<String> getForeignFields();

    boolean is_authenticated();

    boolean is_superuser();

    boolean is_active();

    boolean is_staff();

    String username();

    String first_name();

    String last_name();

    String avatar();
}
