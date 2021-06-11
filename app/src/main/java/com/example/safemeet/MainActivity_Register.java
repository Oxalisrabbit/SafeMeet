package com.example.safemeet;

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
        setTitle("Register");
        Bundle receiver = getIntent().getExtras();

        Button backButton = (Button) findViewById(R.id.backButton6);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_Register.this, MainActivity_Login.class);
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
        TextView authState = (TextView) findViewById(R.id.auth_state);

        if (receiver != null) {
            Log.e("Auth", "pass");
            authState.setTextColor(getResources().getColor(R.color.teal_700));
            authState.setText("\nYES");

        }
        else {
            Log.e("Auth", "deny");
            authState.setTextColor(getResources().getColor(R.color.design_default_color_error));
            authState.setText("\nNO");
        }

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
                                tag1.getText().toString() + ";" + tag2.getText().toString() + ";" + tag3.getText().toString(),
                                authState.getText().toString()
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

        Button auth = (Button) findViewById(R.id.auth);
        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_Register.this, MainActivity_IDAuth.class);
                startActivity(intent);
            }
        });
    }
}