package com.code2bind.studenti.student;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Test {
    public static void main(String[] args) throws SQLException {
        Dictionary<String, String> fields = new Hashtable<>();
        fields.put("user_id", "bigint");
        fields.put("material_id", "bigint");
        fields.put("class_id", "bigint");
        Correspondence material = new Correspondence("student_correspondence", "correspondence", fields);
    }
}
