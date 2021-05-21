package com.example.originalnavigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_EachType extends AppCompatActivity {

    private ArrayList<String> activities = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> origin = new ArrayList<>();
    private ListView activitiesEachType;
    private ArrayAdapter<String> adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            activitiesEachType.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_each_type);
        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("type");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);

        Button backButton = (Button) findViewById(R.id.backButton2);
        activitiesEachType = (ListView) findViewById(R.id.activities_each_type);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, activities);
        Button activityCreate = (Button) findViewById(R.id.activityCreate);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_EachType.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        activityCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_EachType.this, MainActivity_ActivityCreate.class);
                List<HashMap<String, Object>> list = new ArrayList<>();
                HashMap<String, Object> type = new HashMap<String, Object>();
                type.put("id", origin.get(0).get("type"));
                list.add(type);
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                bundleList.add(list);
                bundle.putParcelableArrayList("type", bundleList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                activities.clear();
                origin.clear();
                Log.d("ActivitiesUpdate",String.valueOf(DBUtils.updateStateByEnd()));
                List<HashMap<String, Object>> res = DBUtils.getActivitiesByType(Integer.valueOf((String) receiverContent.get(0).get("id")));
                if (res != null){
                    for(int i = 0; i < res.size(); i++){
                        activities.add(String.valueOf(res.get(i)));
                        origin.add(res.get(i));
                    }
                }
                else {
                    Log.d("OnItemClick","null");
                }
                handler.sendMessage(new Message());
            }
        }).start();

        activitiesEachType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_EachType.this, MainActivity_Details.class);
                List<HashMap<String, Object>> list = new ArrayList<>();
                list.add(origin.get(position));
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                bundleList.add(list);
                bundle.putParcelableArrayList("data", bundleList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}