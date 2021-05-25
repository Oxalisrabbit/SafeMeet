package com.example.originalnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_ActivityCreate extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_create);
        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("type");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);
        origin = receiverContent;

        Button backButton = (Button) findViewById(R.id.backButton5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_ActivityCreate.this, MainActivity_EachType.class);
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                bundleList.add(origin);
                bundle.putParcelableArrayList("type", bundleList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        TextView type_create = (TextView) findViewById(R.id.type_create);
        TextView location_create = (TextView) findViewById(R.id.location_create);
        TextView start_create = (TextView) findViewById(R.id.start_create);
        TextView end_create = (TextView) findViewById(R.id.end_create);
        TextView content_create = (TextView) findViewById(R.id.content_create);
        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (DBUtils.insertActivity(1,
                                Integer.valueOf(type_create.getText().toString()),
                                java.sql.Timestamp.valueOf(start_create.getText().toString()),
                                java.sql.Timestamp.valueOf(end_create.getText().toString()),
                                location_create.getText().toString(),
                                content_create.getText().toString()
                        )) {
                            Log.d("Create", "success");
                            backButton.callOnClick();
                        }
                        else {
                            Log.d("Create", "failed");
                        }
                    }
                }).start();
            }
        });
    }
}