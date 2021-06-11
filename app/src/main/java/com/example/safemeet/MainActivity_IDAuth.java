package com.example.safemeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;

import androidx.annotation.NonNull;

//SwitchButton.OnSwitchButtonStateChangeListener
public class MainActivity_IDAuth extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "IDCardRecognition";

    private boolean lastType = false; // false: front， true：back.
    private static final int REQUEST_CODE = 10;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    private ImageView frontImg;
    private ImageView backImg;
    private ImageView frontSimpleImg;
    private ImageView backSimpleImg;
    private ImageView frontDeleteImg;
    private ImageView backDeleteImg;
    private LinearLayout frontAddView;
    private LinearLayout backAddView;
    private TextView showResult;
    private String lastFrontResult = "";
    private String lastBackResult = "";
    private boolean isRemote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main_idauth);
        this.setTitle("ID Authentication");
        this.initComponent();

        Button backButton = (Button) findViewById(R.id.backButton7);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity_IDAuth.this, MainActivity_Register.class);
                TextView result = (TextView) findViewById(R.id.show_result);
                if (result.getText() != null && result.getText() != ""){
                    Bundle bundle = new Bundle();
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });
    }

    private void initComponent() {
        this.frontImg = this.findViewById(R.id.avatar_img);
        this.backImg = this.findViewById(R.id.emblem_img);
        this.frontSimpleImg = this.findViewById(R.id.avatar_sample_img);
        this.backSimpleImg = this.findViewById(R.id.emblem_sample_img);
        this.frontDeleteImg = this.findViewById(R.id.avatar_delete);
        this.backDeleteImg = this.findViewById(R.id.emblem_delete);
        this.frontAddView = this.findViewById(R.id.avatar_add);
        this.backAddView = this.findViewById(R.id.emblem_add);
        this.showResult = this.findViewById(R.id.show_result);
//        this.switchButton = this.findViewById(R.id.switch_button_view);
//        this.switchButton.setOnSwitchButtonStateChangeListener(this);
//        this.switchButton.setCurrentState(this.isRemote);
        this.frontAddView.setOnClickListener(this);
        this.backAddView.setOnClickListener(this);
        this.frontDeleteImg.setOnClickListener(this);
        this.backDeleteImg.setOnClickListener(this);
//        this.findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.avatar_add:
                Log.i(MainActivity_IDAuth.TAG, "onClick avatar_img");
                this.lastType = true;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(MainActivity_IDAuth.PERMISSIONS, MainActivity_IDAuth.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
                }
                break;
            case R.id.emblem_add:
                Log.i(MainActivity_IDAuth.TAG, "onClick emblem_img");
                this.lastType = false;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(MainActivity_IDAuth.PERMISSIONS, MainActivity_IDAuth.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
                }
                break;
            case R.id.avatar_delete:
                Log.i(MainActivity_IDAuth.TAG, "onClick avatar_delete");
                this.showFrontDeleteImage();
                this.lastFrontResult = "";
                break;
            case R.id.emblem_delete:
                Log.i(MainActivity_IDAuth.TAG, "onClick emblem_delete");
                this.showBackDeleteImage();
                this.lastBackResult = "";
                break;
            case R.id.back:
                this.finish();
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onSwitchButtonStateChange(boolean state) {
//        Log.i(IDAuth.TAG, "remote");
//        this.isRemote = state;
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MainActivity_IDAuth.REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity_IDAuth.TAG, "Camera permission granted - initialize the lensEngine");
            this.startCaptureActivity(this.idCallBack, this.lastType, this.isRemote);
            return;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(MainActivity_IDAuth.TAG, "onConfigurationChanged");
    }

    private void startCaptureActivity(MLCnIcrCapture.CallBack callBack, boolean isFront, boolean isRemote) {
        Log.i(MainActivity_IDAuth.TAG, "startCaptureActivity");
        MLCnIcrCaptureConfig config =
                new MLCnIcrCaptureConfig.Factory().setFront(isFront).setRemote(isRemote).create();
        MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);

        icrCapture.capture(callBack, this);
    }

    private String formatIdCardResult(MLCnIcrCaptureResult result, boolean isFront) {
        Log.i(MainActivity_IDAuth.TAG, "formatIdCardResult");
        StringBuilder resultBuilder = new StringBuilder();
        if (isFront) {
            resultBuilder.append("Name：");
            resultBuilder.append(result.name);
            resultBuilder.append("\r\n");

            resultBuilder.append("Sex：");
            resultBuilder.append(result.sex);
            resultBuilder.append("\r\n");



            resultBuilder.append("IDNum: ");
            resultBuilder.append(result.idNum);
            resultBuilder.append("\r\n");
            Log.i(MainActivity_IDAuth.TAG, "front result: " + resultBuilder.toString());
        } else {

            resultBuilder.append("ValidDate: ");
            resultBuilder.append(result.validDate);
            resultBuilder.append("\r\n");
            Log.i(MainActivity_IDAuth.TAG, "back result: " + resultBuilder.toString());
        }
        return resultBuilder.toString();
    }

    private boolean isGranted(String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            int checkSelfPermission = this.checkSelfPermission(permission);
            return checkSelfPermission == PackageManager.PERMISSION_GRANTED;
        }
    }

    private boolean requestPermission(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (!this.isGranted(permissions[0])) {
            this.requestPermissions(permissions, requestCode);
        }
        return true;
    }

    private MLCnIcrCapture.CallBack idCallBack = new MLCnIcrCapture.CallBack() {
        @Override
        public void onSuccess(MLCnIcrCaptureResult idCardResult) {
            Log.i(MainActivity_IDAuth.TAG, "IdCallBack onRecSuccess");
            if (idCardResult == null) {
                Log.i(MainActivity_IDAuth.TAG, "IdCallBack onRecSuccess idCardResult is null");
                return;
            }
            Bitmap bitmap = idCardResult.cardBitmap;
            if (MainActivity_IDAuth.this.lastType) {
                Log.i(MainActivity_IDAuth.TAG, "Front");
                MainActivity_IDAuth.this.showFrontImage(bitmap);
                MainActivity_IDAuth.this.lastFrontResult = MainActivity_IDAuth.this.formatIdCardResult(idCardResult, true);
            } else {
                Log.i(MainActivity_IDAuth.TAG, "back");
                MainActivity_IDAuth.this.showBackImage(bitmap);
                MainActivity_IDAuth.this.lastBackResult = MainActivity_IDAuth.this.formatIdCardResult(idCardResult, false);
            }
            MainActivity_IDAuth.this.showResult.setText(MainActivity_IDAuth.this.lastFrontResult);
            MainActivity_IDAuth.this.showResult.append(MainActivity_IDAuth.this.lastBackResult);
        }

        @Override
        public void onCanceled() {
            Log.i(MainActivity_IDAuth.TAG, "IdCallBack onRecCanceled");
        }

        @Override
        public void onFailure(int recCode, Bitmap bitmap) {
            Toast.makeText(MainActivity_IDAuth.this.getApplicationContext(), R.string.get_data_failed, Toast.LENGTH_SHORT).show();
            Log.i(MainActivity_IDAuth.TAG, "IdCallBack onRecFailed: " + recCode);
        }

        @Override
        public void onDenied() {
            Log.i(MainActivity_IDAuth.TAG, "IdCallBack onCameraDenied");
        }
    };

    private void showFrontImage(Bitmap bitmap) {
        Log.i(MainActivity_IDAuth.TAG, "showFrontImage");
        this.frontImg.setVisibility(View.VISIBLE);
        this.frontImg.setImageBitmap(bitmap);
        this.frontSimpleImg.setVisibility(View.GONE);
        this.frontAddView.setVisibility(View.GONE);
        this.frontDeleteImg.setVisibility(View.VISIBLE);
    }

    private void showBackImage(Bitmap bitmap) {
        this.backImg.setVisibility(View.VISIBLE);
        this.backImg.setImageBitmap(bitmap);
        this.backAddView.setVisibility(View.GONE);
        this.backSimpleImg.setVisibility(View.GONE);
        this.backDeleteImg.setVisibility(View.VISIBLE);
    }

    private void showFrontDeleteImage() {
        this.frontImg.setVisibility(View.GONE);
        this.frontSimpleImg.setVisibility(View.VISIBLE);
        this.frontAddView.setVisibility(View.VISIBLE);
        this.frontDeleteImg.setVisibility(View.GONE);
    }

    private void showBackDeleteImage() {
        this.backImg.setVisibility(View.GONE);
        this.backAddView.setVisibility(View.VISIBLE);
        this.backSimpleImg.setVisibility(View.VISIBLE);
        this.backDeleteImg.setVisibility(View.GONE);
    }

}