package com.example.originalnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        Button backButton = (Button) findViewById(R.id.backButton6);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView age = (TextView) findViewById(R.id.age);
        TextView gender = (TextView) findViewById(R.id.gender);
        TextView phoneNum = (TextView) findViewById(R.id.phoneNum);
        TextView emergency_call = (TextView) findViewById(R.id.emergency_call);
        TextView tag1 = (TextView) findViewById(R.id.tag1);
        TextView tag2 = (TextView) findViewById(R.id.tag2);
        TextView tag3 = (TextView) findViewById(R.id.tag3);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (DBUtils.insertUserInfo(
                                username.getText().toString(),
                                password.getText().toString(),
                                Integer.valueOf(age.getText().toString()),
                                gender.getText().toString(),
                                phoneNum.getText().toString(),
                                emergency_call.getText().toString(),
                                tag1.getText().toString() + ";" + tag2.getText().toString() + ";" + tag3.getText().toString()
                        )){
                            Log.d("Register", "success");
                            backButton.callOnClick();

                        }
                        else {
                            Log.d("Register", "failed");
                        }
                    }
                }).start();
            }
        });
    }
}