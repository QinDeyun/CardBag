package com.example.activitylifecycle_205801;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activitylifecycle_205801.entity.Card_bank;
import com.example.activitylifecycle_205801.entity.Card_student;
import com.example.activitylifecycle_205801.util.Conduct_bitmap;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture;
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult;
import com.huawei.hms.mlsdk.MLAnalyzerFactory;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.mlsdk.text.MLLocalTextSetting;
import com.huawei.hms.mlsdk.text.MLText;
import com.huawei.hms.mlsdk.text.MLTextAnalyzer;

import java.io.IOException;
import java.util.HashMap;

import io.card.payment.CardIOActivity;
import io.card.payment.CardType;
import io.card.payment.CreditCard;

public class StuCardRecognitionActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView frontImg;
    private ImageView frontSimpleImg;
    private ImageView frontDeleteImg;
    private LinearLayout frontAddView;
    private TextView showResult;
    private String lastFrontResult = "";
    private String lastBackResult = "";
    private Bitmap currentImage;


    private HashMap<String,String> map_result;

    private static final int REQUEST_SCAN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_card_recognition);

        initComponent();
    }

    private void initComponent() {
        frontImg = findViewById(R.id.test_avatar_img);
        frontSimpleImg = findViewById(R.id.test_avatar_sample_img);
        frontDeleteImg = findViewById(R.id.test_avatar_delete);
        frontAddView = findViewById(R.id.test_avatar_add);
        showResult = findViewById(R.id.test_show_result);

        frontAddView.setOnClickListener(this);
        frontDeleteImg.setOnClickListener(this);
        findViewById(R.id.test_back).setOnClickListener(this);

        this.map_result=new HashMap<>();
        findViewById(R.id.save).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.test_avatar_add:
                startCaptureActivity();
                break;
            case R.id.test_avatar_delete:
                this.map_result.clear();
                showFrontDeleteImage();
                lastFrontResult = "";
                break;
            case R.id.test_back:
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
//        MLBcrCaptureConfig config = new MLBcrCaptureConfig.Factory()
//                .setResultType(MLBcrCaptureConfig.RESULT_ALL)
//                .setOrientation(MLBcrCaptureConfig.ORIENTATION_LANDSCAPE)
//                .create();
//        MLBcrCapture bcrCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config);
//
//        bcrCapture.captureFrame(this, this.callback);


//        //方式二：使用自定义参数MLLocalTextSetting配置端侧文本分析器。
//        MLLocalTextSetting setting = new MLLocalTextSetting.Factory()
//                .setOCRMode(MLLocalTextSetting.OCR_DETECT_MODE)
//                // 设置识别语种。
//                .setLanguage("zh")
//                .create();
//        MLTextAnalyzer analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(setting);
//        // 通过bitmap创建MLFrame，bitmap为输入的Bitmap格式图片数据。
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.yc);
//        MLFrame frame = MLFrame.fromBitmap(bitmap1);
//        Task<MLText> task = analyzer.asyncAnalyseFrame(frame);
//        task.addOnSuccessListener(new OnSuccessListener<MLText>() {
//            @Override
//            public void onSuccess(MLText text) {
//                System.out.println("$$$$$" + text.getStringValue());
//                ExtractInfo extractInfo = null;
//                extractInfo.main(text.getStringValue());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(Exception e) {
//                // 识别失败处理。
//            }
//        });
//        try {
//            if (analyzer != null) {
//                analyzer.stop();
//            }
//        } catch (IOException e) {
//            // 异常处理。
//        }
        Intent scanIntent = new Intent(this, CardIOActivity.class);

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
        scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_RESULT, true);

        // REQUEST_SCAN is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, REQUEST_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("TAG", "onActivityResult(" + requestCode + ", " + resultCode + ", " + data + ")");

        String outStr = new String();
        Bitmap cardTypeImage = null;

        if (requestCode == REQUEST_SCAN && data != null
                && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard result = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            if (result != null) {
                outStr += "Card number: " + result.getRedactedCardNumber() + "\n";

                CardType cardType = result.getCardType();
                cardTypeImage = cardType.imageBitmap(this);
                outStr += "Card type: " + cardType.name() + " cardType.getDisplayName(null)="
                        + cardType.getDisplayName(null) + "\n";
            }
        }

        if (data != null) {
            Bitmap card = CardIOActivity.getCapturedCardImage(data);
            showFrontImage(card);


            //方式二：使用自定义参数MLLocalTextSetting配置端侧文本分析器。
            MLLocalTextSetting setting = new MLLocalTextSetting.Factory()
                    .setOCRMode(MLLocalTextSetting.OCR_DETECT_MODE)
                    // 设置识别语种。
                    .setLanguage("zh")
                    .create();
            MLTextAnalyzer analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(setting);
            // 通过bitmap创建MLFrame，bitmap为输入的Bitmap格式图片数据。
            MLFrame frame = MLFrame.fromBitmap(card);
            Task<MLText> task = analyzer.asyncAnalyseFrame(frame);
            task.addOnSuccessListener(new OnSuccessListener<MLText>() {
                @Override
                public void onSuccess(MLText text) {
                    System.out.println(text.getStringValue());
                    showResult.setText(ExtractInfo.getInf(text.getStringValue()));

                    map_result=ExtractInfo.getmap(text.getStringValue());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    // 识别失败处理。
                }
            });
            try {
                if (analyzer != null) {
                    analyzer.stop();
                }
            } catch (IOException e) {
                // 异常处理。
            }

            Log.i("TAG", "Set result: " + outStr);
        }
    }

//        //方式二：使用自定义参数MLLocalTextSetting配置端侧文本分析器。
//        MLLocalTextSetting setting = new MLLocalTextSetting.Factory()
//                .setOCRMode(MLLocalTextSetting.OCR_DETECT_MODE)
//                // 设置识别语种。
//                .setLanguage("zh")
//                .create();
//        MLTextAnalyzer analyzer = MLAnalyzerFactory.getInstance().getLocalTextAnalyzer(setting);
//        // 通过bitmap创建MLFrame，bitmap为输入的Bitmap格式图片数据。
//        MLFrame frame = MLFrame.fromBitmap(card);
//        Task<MLText> task = analyzer.asyncAnalyseFrame(frame);
//        task.addOnSuccessListener(new OnSuccessListener<MLText>() {
//            @Override
//            public void onSuccess(MLText text) {
//                System.out.println("$$$$$" + text.getStringValue());
//                ExtractInfo extractInfo = null;
//                extractInfo.main(text.getStringValue());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(Exception e) {
//                // 识别失败处理。
//            }
//        });
//        try {
//            if (analyzer != null) {
//                analyzer.stop();
//            }
//        } catch (IOException e) {
//            // 异常处理。
//        }


    private String formatIdCardResult(MLBcrCaptureResult result) {
        StringBuilder resultBuilder = new StringBuilder();

        resultBuilder.append("Number：");
        resultBuilder.append(result.getNumber());
        resultBuilder.append("\r\n");

        resultBuilder.append("Issuer：");
        resultBuilder.append(result.getIssuer());
        resultBuilder.append("\r\n");

        resultBuilder.append("Organization：");
        resultBuilder.append(result.getOrganization());
        resultBuilder.append("\r\n");

        resultBuilder.append("Type：");
        resultBuilder.append(result.getType());
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
//        lastFrontResult = formatIdCardResult(idCardResult);
//        showResult.setText(lastFrontResult);
//        showResult.append(lastBackResult);
//        ((ImageView) findViewById(R.id.test_number)).setImageBitmap(idCardResult.getNumberBitmap());
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
                String fronturl = Conduct_bitmap.saveBitmapToInternalStorage(this, "Student_front" + map_result.get("number"), front);

                Card_student card_student = new Card_student(map_result.get("sname"), map_result.get("snum"), map_result.get("sclass"), map_result.get("scollege"), fronturl);
                System.out.println(card_student);



                MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
                SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                Card_student.insertToDatabase(writableDatabase, "card_student", card_student);
            }catch (Exception exception){
                System.out.println(exception);
            }
            finally {
            }

        }
        else {
            Toast.makeText(this, "请扫描校园卡", Toast.LENGTH_SHORT).show();
        }
    }

}