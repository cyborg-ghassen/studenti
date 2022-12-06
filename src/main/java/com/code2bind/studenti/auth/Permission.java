package com.code2bind.studenti.auth;

import com.code2bind.studenti.database.Database;
import com.code2bind.studenti.fields.ForeignKey;
import com.code2bind.studenti.fields.SET_NULL;
import com.code2bind.studenti.managers.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class Permission extends Model {
    String[] crud = {"view", "delete", "add", "change"};
    Database database = new Database();

    ContentType type = new ContentType("contenttype", fields);

    public static Dictionary<String, String> fields = new Hashtable<>();
    public static Dictionary<String, String> values = new Hashtable<>();

    public Permission(String name, String model, Dictionary<String, String> fields) throws SQLException {
        super(name, fields);
        // add foreign key for contenttype
        database.alterTable(name, "contenttype_id", "id", "contenttype");
        // insert corresponding values for both contenttype and permissions
        values.put("model", model);
        values.put("app_label", "auth");
        type.insert("contenttype", values);
    }

    public Permission(String name, String model) throws SQLException {
        super(name, fields);
        // insert corresponding values for permissions
        for (String c : crud) {
            ResultSet contenttype = database.SelectData("contenttype", "id", "model='" + model + "'");
            if (contenttype.next()){
                values.put("name", "Can " + c + " " + model);
                values.put("contenttype_id", String.valueOf(contenttype.getInt(1)));
                values.put("codename", c + "_" + model);
                type.insert(name, values);

            }
            contenttype.close();
        }
    }
}
