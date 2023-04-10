package com.example.activitylifecycle_205801;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCapture;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureConfig;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureFactory;
import com.huawei.hms.mlplugin.card.icr.cn.MLCnIcrCaptureResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class IDCardRecognitionActivity extends AppCompatActivity implements View.OnClickListener,
        SwitchButton.OnSwitchButtonStateChangeListener {
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
    private SwitchButton switchButton;
    private LinearLayout frontAddView;
    private LinearLayout backAddView;
    private TextView showResult;
    private String lastFrontResult = "";
    private String lastBackResult = "";
    private boolean isRemote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_idcard_recognition);
        this.initComponent();
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
        this.switchButton = this.findViewById(R.id.switch_button_view);
        this.switchButton.setOnSwitchButtonStateChangeListener(this);
        this.switchButton.setCurrentState(this.isRemote);
        this.frontAddView.setOnClickListener(this);
        this.backAddView.setOnClickListener(this);
        this.frontDeleteImg.setOnClickListener(this);
        this.backDeleteImg.setOnClickListener(this);
        this.findViewById(R.id.back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.avatar_add:
                Log.i(IDCardRecognitionActivity.TAG, "onClick avatar_img");
                this.lastType = true;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(IDCardRecognitionActivity.PERMISSIONS, IDCardRecognitionActivity.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType);
                }
                break;
            case R.id.emblem_add:
                Log.i(IDCardRecognitionActivity.TAG, "onClick emblem_img");
                this.lastType = false;
                if (!this.isGranted(Manifest.permission.CAMERA)) {
                    this.requestPermission(IDCardRecognitionActivity.PERMISSIONS, IDCardRecognitionActivity.REQUEST_CODE);
                    return;
                } else {
                    this.startCaptureActivity(this.idCallBack, this.lastType);
                }
                break;
            case R.id.avatar_delete:
                Log.i(IDCardRecognitionActivity.TAG, "onClick avatar_delete");
                this.showFrontDeleteImage();
                this.lastFrontResult = "";
                break;
            case R.id.emblem_delete:
                Log.i(IDCardRecognitionActivity.TAG, "onClick emblem_delete");
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

    @Override
    public void onSwitchButtonStateChange(boolean state) {
        Log.i(IDCardRecognitionActivity.TAG, "remote");
        this.isRemote = state;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != IDCardRecognitionActivity.REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(IDCardRecognitionActivity.TAG, "Camera permission granted - initialize the lensEngine");
            this.startCaptureActivity(this.idCallBack, this.lastType);
            return;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(IDCardRecognitionActivity.TAG, "onConfigurationChanged");
    }

    private void startCaptureActivity(MLCnIcrCapture.CallBack callback, boolean isFront) {
        MLCnIcrCaptureConfig config = new MLCnIcrCaptureConfig.Factory()
                // 设置识别身份证的正反面。
                // true：正面。
                // false：反面。
                .setFront(isFront)
                .create();
        MLCnIcrCapture icrCapture = MLCnIcrCaptureFactory.getInstance().getIcrCapture(config);
        icrCapture.capture(callback, this);
    }

    private String formatIdCardResult(MLCnIcrCaptureResult result, boolean isFront) {
        Log.i(IDCardRecognitionActivity.TAG, "formatIdCardResult");
        StringBuilder resultBuilder = new StringBuilder();
        if (isFront) {
            resultBuilder.append("姓名：");
            resultBuilder.append(result.name);
            resultBuilder.append("\r\n");

            resultBuilder.append("性别：");
            resultBuilder.append(result.sex);
            resultBuilder.append("\r\n");

            resultBuilder.append("民族：");
            resultBuilder.append(result.nation);
            resultBuilder.append("\r\n");

            resultBuilder.append("出生日期：");
            resultBuilder.append(result.birthday);
            resultBuilder.append("\r\n");

            resultBuilder.append("住址：");
            resultBuilder.append(result.address);
            resultBuilder.append("\r\n");

            resultBuilder.append("身份证号: ");
            resultBuilder.append(result.idNum);
            resultBuilder.append("\r\n");
            Log.i(IDCardRecognitionActivity.TAG, "front result: " + resultBuilder.toString());
        } else {

            resultBuilder.append("签发机关: ");
            resultBuilder.append(result.authority);
            resultBuilder.append("\r\n");

            resultBuilder.append("有效期限: ");
            resultBuilder.append(result.validDate);
            resultBuilder.append("\r\n");
            Log.i(IDCardRecognitionActivity.TAG, "back result: " + resultBuilder.toString());
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
            Log.i(IDCardRecognitionActivity.TAG, "IdCallBack onRecSuccess");
            if (idCardResult == null) {
                Log.i(IDCardRecognitionActivity.TAG, "IdCallBack onRecSuccess idCardResult is null");
                return;
            }
            Bitmap bitmap = idCardResult.cardBitmap;
            if (IDCardRecognitionActivity.this.lastType) {
                Log.i(IDCardRecognitionActivity.TAG, "Front");
                IDCardRecognitionActivity.this.showFrontImage(bitmap);
                IDCardRecognitionActivity.this.lastFrontResult = IDCardRecognitionActivity.this.formatIdCardResult(idCardResult, true);
            } else {
                Log.i(IDCardRecognitionActivity.TAG, "back");
                IDCardRecognitionActivity.this.showBackImage(bitmap);
                IDCardRecognitionActivity.this.lastBackResult = IDCardRecognitionActivity.this.formatIdCardResult(idCardResult, false);
            }
            IDCardRecognitionActivity.this.showResult.setText(IDCardRecognitionActivity.this.lastFrontResult);
            IDCardRecognitionActivity.this.showResult.append(IDCardRecognitionActivity.this.lastBackResult);
        }

        @Override
        public void onCanceled() {
            Log.i(IDCardRecognitionActivity.TAG, "IdCallBack onRecCanceled");
        }

        @Override
        public void onFailure(int recCode, Bitmap bitmap) {
            Toast.makeText(IDCardRecognitionActivity.this.getApplicationContext(), R.string.get_data_failed, Toast.LENGTH_SHORT).show();
            Log.i(IDCardRecognitionActivity.TAG, "IdCallBack onRecFailed: " + recCode);
        }

        @Override
        public void onDenied() {
            Log.i(IDCardRecognitionActivity.TAG, "IdCallBack onCameraDenied");
        }
    };

    private void showFrontImage(Bitmap bitmap) {
        Log.i(IDCardRecognitionActivity.TAG, "showFrontImage");
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