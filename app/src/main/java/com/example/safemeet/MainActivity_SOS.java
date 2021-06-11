package com.example.safemeet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity_SOS extends AppCompatActivity {

    private List<HashMap<String, Object>> origin = new ArrayList<>();
    UserInfo userInfo;
    TypeInfo allType = new TypeInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sos);
        setTitle("SOS");

        Bundle receiver = getIntent().getExtras();
        ArrayList list = receiver.getParcelableArrayList("data");
        List<HashMap<String, Object>> receiverContent = (List<HashMap<String, Object>>) list.get(0);
        origin = receiverContent;

        userInfo = (UserInfo) getIntent().getSerializableExtra("UserInfo");

        allType = (TypeInfo) getIntent().getSerializableExtra("TypeInfo");

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
                bundle.putSerializable("UserInfo", userInfo);
                bundle.putSerializable("TypeInfo", allType);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button emergency_center = (Button) findViewById(R.id.emergency_center);
        emergency_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String emergencyFlag = "Emergency request at" + new Timestamp(System.currentTimeMillis()).toString() + ";";
                        if (DBUtils.addEmergencyMessage(
                                Integer.valueOf((String) origin.get(0).get("uid")),
                                Integer.valueOf((String) origin.get(0).get("id")),
                                emergencyFlag
                        ) == true){
                            Log.d("Emergency", "success");
                        }
                        else {
                            Log.d("Emergency", "failed");
                        }
                    }
                }).start();
            }
        });

        Button show_location = (Button) findViewById(R.id.show_location);
        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 500, new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            double lng = location.getLongitude();
                            double lat = location.getLatitude();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String currentLocation = "(" + lng + "," + lat + ");";
                                    DBUtils.addEmergencyMessage(
                                            Integer.valueOf((String) origin.get(0).get("uid")),
                                            Integer.valueOf((String) origin.get(0).get("id")),
                                            currentLocation
                                    );
                                    Log.d("LocationInfo", currentLocation);
                                }
                            }).start();
                        }
                    });
                }
                catch (SecurityException e){
                    Log.d("shareLocation", "failed:" + e.getMessage());
                }
            }
        });

        Button emergency_contact = (Button) findViewById(R.id.emergency_contact);
        emergency_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:" + userInfo.emergencyCall);
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}