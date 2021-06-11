package com.example.safemeet;

import java.io.Serializable;
import java.util.HashMap;

public class UserInfo implements Serializable {
    long id;
    String username;
    String password;
    String auth_token;
    int age;
    String gender;
    String phoneNum;
    String emergencyCall;
    String tags;
    float rating;

    UserInfo(){
        id = 0;
        username = "";
        password = "";
        auth_token = "";
        age = 0;
        gender = "";
        phoneNum = "";
        emergencyCall = "";
        tags = "";
        rating = 0;
    }

    UserInfo(int id, String username, String password, String auth_token, int age, String gender, String phoneNum, String emergencyCall, String tags, float rating){
        this.id = id;
        this.username = username;
        this.password = password;
        this.auth_token = auth_token;
        this.age = age;
        this.gender = gender;
        this.phoneNum = phoneNum;
        this.emergencyCall = emergencyCall;
        this.tags = tags;
        this.rating = rating;
    }

    UserInfo(HashMap<String, Object> userInfo){
        try{
            id = Long.valueOf(userInfo.get("id").toString());
            password = String.valueOf(userInfo.get("password").toString());
            emergencyCall = String.valueOf(userInfo.get("emergency_call").toString());
        }
        catch (Exception e){
            id = 0;
            password = "";
            emergencyCall = "";
        }
        username = String.valueOf(userInfo.get("username").toString());
        auth_token = String.valueOf(userInfo.get("auth_token").toString());
        age = Integer.valueOf(userInfo.get("age").toString());
        gender = String.valueOf(userInfo.get("gender").toString());
        phoneNum = String.valueOf(userInfo.get("phone_num").toString());
        tags = String.valueOf(userInfo.get("tags").toString());
        rating = Float.valueOf(userInfo.get("rating").toString());
    }
}
