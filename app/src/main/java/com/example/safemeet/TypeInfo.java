package com.example.safemeet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TypeInfo implements Serializable {
    HashMap<Integer, String> type;

    TypeInfo(){
        type = new HashMap<Integer, String>();
    }

    public void add(Map<String, Object> type){
        this.type.put(Integer.valueOf(type.get("id").toString()), String.valueOf(type.get("name").toString()));
    }
}
