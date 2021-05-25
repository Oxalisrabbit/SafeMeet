package com.example.originalnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_Details extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);
        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("data");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);
        Button backButton = (Button) findViewById(R.id.backButton3);
        Button SOS = (Button) findViewById(R.id.SOS);
        TextView state = (TextView) findViewById(R.id.state);
        ImageView state_mark = (ImageView) findViewById(R.id.state_mark);

        origin = receiverContent;

        try{
            origin.get(1);
            origin.remove(1);

            backButton.setText("FINISH");
            SOS.setText("SOS");
            state.setText("ACTIVITY ONGOING...");
            state_mark.setImageResource(android.R.drawable.presence_online);
        }
        catch (Exception e){

        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backButton.getText().equals("BACK")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity_Details.this, MainActivity_EachType.class);
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
                else if (backButton.getText().equals("FINISH")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity_Details.this, MainActivity_Rating.class);
                    List<HashMap<String, Object>> list = new ArrayList<>();
                    list.add(origin.get(0));
                    Bundle bundle = new Bundle();
                    ArrayList bundleList = new ArrayList<>();
                    bundleList.add(list);
                    bundle.putParcelableArrayList("data", bundleList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        TextView type = (TextView) findViewById(R.id.type);
        type.setText((String) origin.get(0).get("type"));
        TextView location = (TextView) findViewById(R.id.location);
        location.setText((String) origin.get(0).get("location"));
        TextView start = (TextView) findViewById(R.id.start);
        start.setText((String) origin.get(0).get("start"));
        TextView end = (TextView) findViewById(R.id.end);
        end.setText((String) origin.get(0).get("end"));
        TextView content = (TextView) findViewById(R.id.content_rating);
        content.setMovementMethod(ScrollingMovementMethod.getInstance());
        content.setBackgroundColor(getResources().getColor(R.color.purple_200));
        content.setText((String) origin.get(0).get("content"));
        TextView creator = (TextView) findViewById(R.id.creator);
        RatingBar rating_result = (RatingBar) findViewById(R.id.rating_result);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> res = DBUtils.getUserInfoById(Integer.valueOf((String) origin.get(0).get("uid"))).get(0);
                creator.setText((String) res.get("username"));
                rating_result.setRating(Float.valueOf((String) res.get("rating")));
                if (res.get("auth_token") == null){
                    creator.setTextColor(getResources().getColor(R.color.design_default_color_error));
                }
                else {
                    creator.setTextColor(getResources().getColor(R.color.teal_700));
                }
            }
        }).start();

        SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SOS.getText().equals("SOS")){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity_Details.this, MainActivity_SOS.class);
                    List<HashMap<String, Object>> list = new ArrayList<>();
                    list.add(origin.get(0));
                    Bundle bundle = new Bundle();
                    ArrayList bundleList = new ArrayList<>();
                    bundleList.add(list);
                    bundle.putParcelableArrayList("data", bundleList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if (SOS.getText().equals("JOIN")){
                    backButton.setText("FINISH");
                    SOS.setText("SOS");
                    state.setText("ACTIVITY ONGOING...");
                    state_mark.setImageResource(android.R.drawable.presence_online);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DBUtils.addNewMember(2 + ";", Long.valueOf((String) origin.get(0).get("id")));
                        }
                    }).start();
                }
            }
        });
    }
}