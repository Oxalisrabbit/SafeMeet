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

public class MainActivity_Home extends AppCompatActivity {

    private List<Map<String, Object>> types = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> origin = new ArrayList<>();
    private ListView listContent;
    private SimpleAdapter adapter;
    private UserInfo userInfo;
    private TypeInfo allType = new TypeInfo();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            listContent.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        setTitle("Choose the Type of Activities :");
        Button backButton = (Button) findViewById(R.id.backButton);
        listContent = (ListView) findViewById(R.id.listContent);
        adapter = new SimpleAdapter(this, types, R.layout.list_type, new String[]{"typeImage", "typeName"}, new int[]{R.id.type_image, R.id.type_name});

        userInfo = (UserInfo) getIntent().getSerializableExtra("UserInfo");

        Log.e("UserInfo", userInfo.username);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_Home.this, MainActivity_Login.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                types.clear();
                origin.clear();
                List<HashMap<String, Object>> res = DBUtils.getAllType();
                if (res != null){
                    for(int i = 0; i < res.size(); i++){
                        Map<String, Object> typeInfo = new HashMap<String, Object>();
                        typeInfo.put("typeName", res.get(i).get("name").toString());
                        if (typeInfo.get("typeName").equals("MEAL")){
                            typeInfo.put("typeImage", R.drawable.icon_meal);
                        }
                        else if (typeInfo.get("typeName").equals("MOVIE")){
                            typeInfo.put("typeImage", R.drawable.icon_movie);
                        }
                        else if (typeInfo.get("typeName").equals("SHOPPING")){
                            typeInfo.put("typeImage", R.drawable.icon_shopping);
                        }
                        else {
                            typeInfo.put("typeImage", R.drawable.icon_other);
                        }
                        types.add(typeInfo);
                        allType.add(res.get(i));
                        origin.add(res.get(i));
                    }
                }
                else {
                    Log.d("OnItemClick","null");
                }
                handler.sendMessage(new Message());
            }
        }).start();

        listContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_Home.this, MainActivity_EachType.class);
                List<HashMap<String, Object>> list = new ArrayList<>();
                list.add(origin.get(position));
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

    }
}