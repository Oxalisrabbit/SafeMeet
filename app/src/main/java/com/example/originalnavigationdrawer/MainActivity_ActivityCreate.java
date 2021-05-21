package com.example.originalnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }
}