package com.example.activitylifecycle_205801;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activitylifecycle_205801.entity.Card_bank;

import com.example.activitylifecycle_205801.util.Conduct_bitmap;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;

import java.util.HashMap;

public class BankCardRecognitionActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView frontImg;
    private ImageView frontSimpleImg;
    private ImageView frontDeleteImg;
    private LinearLayout frontAddView;
    private TextView showResult;
    private String lastFrontResult = "";
    private String lastBackResult = "";
    private Bitmap currentImage;

    private HashMap<String,String>  map_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_recognition);
        initComponent();
    }

    private void initComponent() {
        frontImg = findViewById(R.id.avatar_img);
        frontSimpleImg = findViewById(R.id.avatar_sample_img);
        frontDeleteImg = findViewById(R.id.avatar_delete);
        frontAddView = findViewById(R.id.avatar_add);
        showResult = findViewById(R.id.show_result);

        frontAddView.setOnClickListener(this);
        frontDeleteImg.setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

        //zlf
        this.map_result=new HashMap<>();
        findViewById(R.id.save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.avatar_add:
                startCaptureActivity();
                break;
            case R.id.avatar_delete:
                showFrontDeleteImage();
                this.map_result.clear();
                lastFrontResult = "";
                break;
            case R.id.back:
                finish();
                break;
            case R.id.save:
                save();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentImage != null && !currentImage.isRecycled()) {
            currentImage.recycle();
            currentImage = null;
        }
    }

    private void startCaptureActivity() {
        MLBcrCaptureConfig config = new MLBcrCaptureConfig.Factory()
                .setResultType(MLBcrCaptureConfig.RESULT_ALL)
                .setOrientation(MLBcrCaptureConfig.ORIENTATION_LANDSCAPE)
                .create();
        MLBcrCapture bcrCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config);

        bcrCapture.captureFrame(this, this.callback);
    }

    private String formatIdCardResult(MLBcrCaptureResult result) {
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("Number：");
        resultBuilder.append(result.getNumber());
        this.map_result.put("number",result.getNumber());
        resultBuilder.append("\r\n");

        resultBuilder.append("Issuer：");
        resultBuilder.append(result.getIssuer());
        this.map_result.put("issuer",result.getIssuer());
        resultBuilder.append("\r\n");

        resultBuilder.append("Organization：");
        resultBuilder.append(result.getOrganization());
        this.map_result.put("organization",result.getOrganization());
        resultBuilder.append("\r\n");

        resultBuilder.append("Type：");
        resultBuilder.append(result.getType());
        this.map_result.put("type",result.getType());
        resultBuilder.append("\r\n");
        return resultBuilder.toString();

    }

    private MLBcrCapture.Callback callback = new MLBcrCapture.Callback() {
        @Override
        public void onSuccess(MLBcrCaptureResult result) {
            if (result == null) {
                return;
            }
            Bitmap bitmap = result.getOriginalBitmap();
            showSuccessResult(bitmap, result);
        }

        @Override
        public void onCanceled() {
        }

        @Override
        public void onFailure(int recCode, Bitmap bitmap) {
            showResult.setText(" RecFailed ");
        }

        @Override
        public void onDenied() {
        }
    };

    private void showSuccessResult(Bitmap bitmap, MLBcrCaptureResult idCardResult) {
        showFrontImage(bitmap);
        lastFrontResult = formatIdCardResult(idCardResult);
        showResult.setText(lastFrontResult);
        showResult.append(lastBackResult);
        ((ImageView) findViewById(R.id.number)).setImageBitmap(idCardResult.getNumberBitmap());
    }

    private void showFrontImage(Bitmap bitmap) {
        frontImg.setVisibility(View.VISIBLE);
        frontImg.setImageBitmap(bitmap);
        frontSimpleImg.setVisibility(View.GONE);
        frontAddView.setVisibility(View.GONE);
        frontDeleteImg.setVisibility(View.VISIBLE);
    }

    private void showFrontDeleteImage() {
        frontImg.setVisibility(View.GONE);
        frontSimpleImg.setVisibility(View.VISIBLE);
        frontAddView.setVisibility(View.VISIBLE);
        frontDeleteImg.setVisibility(View.GONE);
    }


    private void save(){
        if(!map_result.isEmpty()){

            try {
                Drawable drawable_front = this.frontImg.getDrawable();
                Bitmap front = ((BitmapDrawable) drawable_front).getBitmap();
                String fronturl = Conduct_bitmap.saveBitmapToInternalStorage(this, "Bank_front" + map_result.get("number"), front);

                Card_bank card_bank = new Card_bank(map_result.get("number"), map_result.get("issuer"), map_result.get("organization"), map_result.get("type"), fronturl);
                System.out.println(card_bank);



                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                Card_bank.insertToDatabase(writableDatabase, "card_bank", card_bank);
            }catch (Exception exception){
                System.out.println(exception);
            }
            finally {
            }

        }
        else {
            Toast.makeText(this, "请扫描银行卡", Toast.LENGTH_SHORT).show();
        }
    }

}