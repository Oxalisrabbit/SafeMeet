package com.example.safemeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_Rating extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rating);
        setTitle("Rating");

        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("data");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);
        origin = receiverContent;
        Button done = (Button) findViewById(R.id.done);
        TextView content = (TextView) findViewById(R.id.content_rating);
        RatingBar rating = (RatingBar) findViewById(R.id.rating);
        Log.d("",origin.toString());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.addComment(
                                Integer.valueOf((String) origin.get(0).get("id")),
                                Integer.valueOf((String) origin.get(0).get("uid")),
                                2,
                                rating.getRating(),
                                content.getText().toString()
                        );
                        DBUtils.changeActivityState(Integer.valueOf((String) origin.get(0).get("id")));
                        DBUtils.updateRatingByCreatorId(Integer.valueOf((String) origin.get(0).get("uid")));
                    }
                }).start();

                Intent intent = new Intent();
                intent.setClass(MainActivity_Rating.this, MainActivity_Home.class);
                startActivity(intent);
            }
        });
    }
}