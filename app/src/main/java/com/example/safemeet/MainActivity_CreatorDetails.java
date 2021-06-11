package com.example.safemeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_CreatorDetails extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();
    private List<HashMap<String, Object>> creator = new ArrayList<>();
    private UserInfo userInfo;
    private TypeInfo allType = new TypeInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_creator_details);
        setTitle("Creator Details");

        Bundle receiver = getIntent().getExtras();
        ArrayList list1 = receiver.getParcelableArrayList("data");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list1.get(0);
        origin = receiverContent;

        ArrayList list2 = receiver.getIntegerArrayList("creator");
        List<HashMap<String, Object>> creatorContent = (List<HashMap<String, Object>>) list2.get(0);
        creator = creatorContent;

        userInfo = (UserInfo) getIntent().getSerializableExtra("UserInfo");

        allType = (TypeInfo) getIntent().getSerializableExtra("TypeInfo");

        Button backButton = (Button) findViewById(R.id.backButton8);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_CreatorDetails.this, MainActivity_Details.class);
                List<HashMap<String, Object>> list = new ArrayList<>();
                list.add(origin.get(0));
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList<>();
                bundleList.add(list);
                bundle.putParcelableArrayList("data", bundleList);
                bundle.putSerializable("UserInfo", userInfo);
                bundle.putSerializable("TypeInfo", allType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        UserInfo creatorInfo = new UserInfo(creator.get(0));
        TextView username = (TextView) findViewById(R.id.creator_username);
        TextView age = (TextView) findViewById(R.id.creator_age);
        TextView gender = (TextView) findViewById(R.id.creator_gender);
        TextView phoneNum = (TextView) findViewById(R.id.creator_phone_num);
        TextView tags = (TextView) findViewById(R.id.creator_tags);
        TextView authState = (TextView) findViewById(R.id.creator_auth_state);

        username.setText(creatorInfo.username);
        age.setText(String.valueOf(creatorInfo.age));
        gender.setText(creatorInfo.gender);
        phoneNum.setText(creatorInfo.phoneNum);
        tags.setText(creatorInfo.tags);
        authState.setText(creatorInfo.auth_token);
        if (creatorInfo.auth_token.equals("YES")){
            authState.setTextColor(getResources().getColor(R.color.teal_700));
        }
        else {
            authState.setTextColor(getResources().getColor(R.color.design_default_color_error));
        }
    }
}