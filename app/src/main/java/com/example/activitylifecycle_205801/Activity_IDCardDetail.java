package com.example.activitylifecycle_205801;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.activitylifecycle_205801.entity.Card_ID;
import com.example.activitylifecycle_205801.util.Conduct_bitmap;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_IDCardDetail extends AppCompatActivity {
    Card_ID card_id;
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case (R.id.photo):
                    dialogshow_photo();
                    break;
                case (R.id.button_edit):
                    dialogshow_edit();
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_detail);

        try {
            this.init();
        }catch (Exception e){
            System.out.println(e);
        }




        findViewById(R.id.button_edit).setOnClickListener(this.onClickListener);
        findViewById(R.id.photo).setOnClickListener(this.onClickListener);
    }

    void init(){
        Intent intent=getIntent();
        String idnum=intent.getStringExtra("idnum");

        try {
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
            SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
            this.card_id = Card_ID.queryCardByIdnum(writableDatabase, "card_id", idnum);
            System.out.println(this.card_id);
        }catch (Exception e){
            System.out.println(e);
        }

        View card = findViewById(R.id.card_id);
        ((TextView)(card.findViewById(R.id.idcard_name))).setText(this.card_id.getName());
        ((TextView)(card.findViewById(R.id.idcard_sex))).setText(this.card_id.getSex());
        ((TextView)(card.findViewById(R.id.idcard_nation))).setText(this.card_id.getNation());
        ((TextView)(card.findViewById(R.id.idcard_birthday))).setText(this.card_id.getBirthday());
        ((TextView)(card.findViewById(R.id.idcard_address))).setText(this.card_id.getAddress());
        ((TextView)(card.findViewById(R.id.idcard_idnum))).setText(this.card_id.getIdnum());

    }

    void dialogshow_photo(){
        try {


            Bitmap front = Conduct_bitmap.loadBitmapFromInternalStorage(this, this.card_id.getFirsturl());
            Bitmap back = Conduct_bitmap.loadBitmapFromInternalStorage(this, this.card_id.getSecondurl());
            Bitmap bitmap_added = Conduct_bitmap.compositing_bitmaps(front, back);
            Bitmap bitmap_show = bitmap_added;

            // 创建BottomSheetDialog对象
            BottomSheetDialog dialog = new BottomSheetDialog(Activity_IDCardDetail.this);
            // 设置自定义布局文件
            View dialogView = LayoutInflater.from(Activity_IDCardDetail.this).inflate(R.layout.card_id__dialog_photo, null);
            dialog.setContentView(dialogView);

            ImageView viewById = dialogView.findViewById(R.id.image);

            View.OnClickListener onClickListener1 = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case (R.id.add_watermark):

                            EditText editText = dialogView.findViewById(R.id.watermark);
                            viewById.setImageBitmap(Conduct_bitmap.add_watermark(bitmap_show, "仅限于" + editText.getText() + "  ,他用无效"));
                            break;
                        case (R.id.del_watermark):

                            viewById.setImageBitmap(bitmap_added);
                            break;
                        case (R.id.out):
                            try {
                                EditText editText2 = dialogView.findViewById(R.id.watermark);
                                Bitmap bitmap = ((BitmapDrawable) viewById.getDrawable()).getBitmap();
                                Conduct_bitmap.outPDf(Activity_IDCardDetail.this, Conduct_bitmap.add_watermark(bitmap_show, "仅限于" + editText2.getText() + "  ,他用无效"));
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                    }
                }
            };

            dialogView.findViewById(R.id.add_watermark).setOnClickListener(onClickListener1);
            dialogView.findViewById(R.id.del_watermark).setOnClickListener(onClickListener1);
            dialogView.findViewById(R.id.out).setOnClickListener(onClickListener1);

            viewById.setImageBitmap(bitmap_added);
            // 显示对话框
            dialog.show();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    void dialogshow_edit(){

        // 创建BottomSheetDialog对象
        BottomSheetDialog dialog = new BottomSheetDialog(Activity_IDCardDetail.this);
        // 设置自定义布局文件
        View dialogView = LayoutInflater.from(Activity_IDCardDetail.this).inflate(R.layout.card_id__dialog_edit, null);
        dialog.setContentView(dialogView);

        EditText editTextIdCardNumber = (EditText) dialogView.findViewById(R.id.editTextIdCardNumber);
        editTextIdCardNumber.setText(this.card_id.getIdnum());
        EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        editTextName.setText(this.card_id.getName());
        EditText editTextSex = (EditText) dialogView.findViewById(R.id.editTextSex);
        editTextSex.setText(this.card_id.getSex());
        EditText editTextNation = (EditText) dialogView.findViewById(R.id.editTextNation);
        editTextNation.setText(this.card_id.getNation());
        EditText editTextBirthday = (EditText) dialogView.findViewById(R.id.editTextBirthday);
        editTextBirthday.setText(this.card_id.getBirthday());
        EditText editTextAddress = (EditText) dialogView.findViewById(R.id.editTextAddress);
        editTextAddress.setText(this.card_id.getAddress());
        EditText editTextAuthority = (EditText) dialogView.findViewById(R.id.editTextAuthority);
        editTextAuthority.setText(this.card_id.getAuthority());
        EditText editTextValiddate = (EditText) dialogView.findViewById(R.id.editTextValiddate);
        editTextValiddate.setText(this.card_id.getValiddate());


        Button buttonEdit = dialogView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card_ID cardId_new=card_id;
                cardId_new.setIdnum(editTextIdCardNumber.getText().toString());
                cardId_new.setName(editTextName.getText().toString());
                cardId_new.setSex(editTextSex.getText().toString());
                cardId_new.setNation(editTextNation.getText().toString());
                cardId_new.setBirthday(editTextBirthday.getText().toString());
                cardId_new.setAddress(editTextAddress.getText().toString());
                cardId_new.setAuthority(editTextAuthority.getText().toString());
                cardId_new.setValiddate(editTextValiddate.getText().toString());
                try {
                    MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(Activity_IDCardDetail.this);
                    SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                    Card_ID.updateToDatabase(writableDatabase,"card_id",cardId_new);
                    writableDatabase.close();
                    Toast.makeText(Activity_IDCardDetail.this, "修改成功", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    init();
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }
        });


        dialog.show();
    }


}