package com.example.safemeet;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity_EachType extends AppCompatActivity {

    private ArrayList<Map<String, Object>> activities = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> origin = new ArrayList<>();
    private ListView activitiesEachType;
    private SimpleAdapter adapter;
    private UserInfo userInfo;
    private TypeInfo allType = new TypeInfo();

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

        allType = (TypeInfo) getIntent().getSerializableExtra("TypeInfo");
        setTitle(allType.type.get(Integer.valueOf(receiverContent.get(0).get("id").toString())));

        userInfo = (UserInfo) getIntent().getSerializableExtra("UserInfo");

        Log.e("recv", receiverContent.get(0).toString());
        Log.e("UserInfo", userInfo.username);
        Log.e("TypeInfo", allType.type.toString());

        Button backButton = (Button) findViewById(R.id.backButton2);
        activitiesEachType = (ListView) findViewById(R.id.activities_each_type);
        adapter = new SimpleAdapter(this, activities, R.layout.list_activity, new String[]{"userImage", "activityStart", "activitySize", "activityLocation"}, new int[]{R.id.user_image, R.id.activity_start, R.id.activity_size, R.id.activity_location});
        Button activityCreate = (Button) findViewById(R.id.activityCreate);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_EachType.this, MainActivity_Home.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("UserInfo", userInfo);
                bundle.putSerializable("TypeInfo", allType);
                intent.putExtras(bundle);
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
                type.put("id", receiverContent.get(0).get("id").toString());
                list.add(type);
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                bundleList.add(list);
                bundle.putParcelableArrayList("type", bundleList);
                bundle.putSerializable("UserInfo", userInfo);
                bundle.putSerializable("TypeInfo", allType);
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
                        Map<String, Object> activity = new HashMap<String, Object>();
                        activity.put("userImage", R.drawable.user_image);
                        activity.put("activityStart", res.get(i).get("start").toString());
                        activity.put("activitySize", res.get(i).get("size").toString());
                        activity.put("activityLocation", res.get(i).get("location").toString());
                        activities.add(activity);
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
                bundle.putSerializable("UserInfo", userInfo);
                bundle.putSerializable("TypeInfo", allType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}