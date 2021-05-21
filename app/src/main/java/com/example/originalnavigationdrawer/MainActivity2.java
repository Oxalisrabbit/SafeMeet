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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

//    private String[] testData = {"test1", "test2", "test3", "test4", "test5"};
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> origin = new ArrayList<>();
    private ListView listContent;
    private ArrayAdapter<String> adapter;

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
        setContentView(R.layout.activity_main2);
        Button backButton = (Button) findViewById(R.id.backButton);
        listContent = (ListView) findViewById(R.id.listContent);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, types);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity2.this, MainActivity.class);
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
                        types.add(String.valueOf(res.get(i)));
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
                intent.setClass(MainActivity2.this, MainActivity_EachType.class);
                List<HashMap<String, Object>> list = new ArrayList<>();
                list.add(origin.get(position));
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                bundleList.add(list);
                bundle.putParcelableArrayList("type", bundleList);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }
}