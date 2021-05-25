package com.example.originalnavigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_Admin extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();
    private ArrayList emergency = new ArrayList();
    private ListView emergencyData;
    private ArrayAdapter<String> adapter;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            emergencyData.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        emergencyData = (ListView) findViewById(R.id.emergency_data);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, emergency);
        Button fresh = (Button) findViewById(R.id.fresh);
        fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        emergency.clear();
                        origin.clear();
                        Log.d("ActivitiesUpdate",String.valueOf(DBUtils.updateStateByEnd()));
                        List<HashMap<String, Object>> res = DBUtils.getAllEmergency();
                        if (res != null){
                            for(int i = 0; i < res.size(); i++){
                                emergency.add(String.valueOf(res.get(i)));
                                origin.add(res.get(i));
                            }
                        }
                        else {

                        }
                        handler.sendMessage(new Message());
                    }
                }).start();
            }
        });

        emergencyData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(MainActivity_Admin.this)
                        .setMessage((String) origin.get(position).get("content"))
                        .setPositiveButton("cancel", null)
                        .show();
            }
        });
    }
}