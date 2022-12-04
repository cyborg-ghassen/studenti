package com.code2bind.studenti.fields;

import java.util.List;

public class CharField {
    public List<String> choices;
    public int max_length = 80;
    public boolean unique = false;
    public String varchar = "varchar(0)";
    public CharField(){}
    public CharField(int max_length, List<String> choices){
        this.choices = choices;
        this.max_length = max_length;
        this.varchar = "varchar(" + max_length + ") ";
    }

    public CharField(int max_length, boolean unique){
        this.unique = unique;
        this.max_length = max_length;
        this.varchar = "varchar(" + max_length + ") ";
        if (unique)
            this.varchar = this.varchar.concat("unique");
    }
    public CharField(int max_length){
        this.max_length = max_length;
        this.varchar = "varchar(" + max_length + ") ";
    }
}
