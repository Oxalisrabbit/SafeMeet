package com.example.originalnavigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_SOS extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sos);
        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("data");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);
        origin = receiverContent;

        Log.d("SOS", origin.toString());

        Button backButton = (Button) findViewById(R.id.backButton4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_SOS.this, MainActivity_Details.class);
                Bundle bundle = new Bundle();
                ArrayList bundleList = new ArrayList();
                HashMap<String, Object> flag = new HashMap<>();
                flag.put("flag","flag");
                origin.add(flag);
                bundleList.add(origin);
                bundle.putParcelableArrayList("data", bundleList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button emergency_contact = (Button) findViewById(R.id.emergency_contact);
        emergency_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
//                Uri uri = Uri.parse("tel:" + "0061-468964110");
                Uri uri = Uri.parse("tel:" + "1 555-521-5554");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}