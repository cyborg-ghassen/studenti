package com.code2bind.studenti.fields;

import com.code2bind.studenti.auth.ContentType;

public class ForeignKey {
    public Object to = null;
    public boolean nul = false;

    public Object on_delete = null;

    public ForeignKey(Object to, Object on_delete){
        this.to = to;
        this.on_delete = on_delete;
    }
    ForeignKey(Object to, boolean nul, Object on_delete){
        this.to = to;
        this.nul = nul;
        this.on_delete = on_delete;
    }
}
