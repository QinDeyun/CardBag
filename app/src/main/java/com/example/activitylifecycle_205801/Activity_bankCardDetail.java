package com.example.activitylifecycle_205801;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activitylifecycle_205801.entity.Card_ID;
import com.example.activitylifecycle_205801.entity.Card_bank;
import com.example.activitylifecycle_205801.util.Conduct_bitmap;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_bankCardDetail extends AppCompatActivity {
    Card_bank card_bank;
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
        setContentView(R.layout.activity_bank_card_detail);

        this.init();
        findViewById(R.id.button_edit).setOnClickListener(this.onClickListener);
        findViewById(R.id.photo).setOnClickListener(this.onClickListener);
    }
    void init(){
        Intent intent=getIntent();
        String number=intent.getStringExtra("number");
        System.out.println(number+"测试");
        try {
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
            SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
            this.card_bank = Card_bank.getRecordByNumber(writableDatabase, "card_bank", number);
            System.out.println(this.card_bank);
        }catch (Exception e){
            System.out.println(e);
        }

        View card = findViewById(R.id.card_bank);
        ((TextView)(card.findViewById(R.id.cardNumber))).setText(this.card_bank.getNumber());
        ((TextView)(card.findViewById(R.id.Type))).setText(this.card_bank.getType());
        ((TextView)(card.findViewById(R.id.Issure))).setText(this.card_bank.getIssuer());
    }
    void dialogshow_edit(){
        // 创建BottomSheetDialog对象
        BottomSheetDialog dialog = new BottomSheetDialog(Activity_bankCardDetail.this);
        // 设置自定义布局文件
        View dialogView = LayoutInflater.from(Activity_bankCardDetail.this).inflate(R.layout.card_bank_dialog_edit, null);
        dialog.setContentView(dialogView);
        EditText cardNumber = (EditText) dialogView.findViewById(R.id.cardNumber);
        cardNumber.setText(this.card_bank.getNumber());
        EditText editTextIssure = (EditText) dialogView.findViewById(R.id.editTextIssure);
        editTextIssure.setText(this.card_bank.getIssuer());
        EditText editTextOrgnization = (EditText) dialogView.findViewById(R.id.editTextOrgnization);
        editTextOrgnization.setText(this.card_bank.getOrganization());
        EditText editTextType = (EditText) dialogView.findViewById(R.id.editTextType);
        editTextType.setText(this.card_bank.getType());


        Button buttonEdit = dialogView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card_bank card_bank_new=card_bank;
                card_bank_new.setNumber(cardNumber.getText().toString());
                card_bank_new.setIssuer(editTextIssure.getText().toString());
                card_bank_new.setOrganization(editTextOrgnization.getText().toString());
                card_bank_new.setType(editTextType.getText().toString());

                try {
                    MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(Activity_bankCardDetail.this);
                    SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                    Card_bank.updateToDatabase(writableDatabase,"card_bank",card_bank_new);
                    writableDatabase.close();
                    Toast.makeText(Activity_bankCardDetail.this, "修改成功", Toast.LENGTH_SHORT).show();
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
    void dialogshow_photo(){
        // 创建BottomSheetDialog对象
        BottomSheetDialog dialog = new BottomSheetDialog(Activity_bankCardDetail.this);
        // 设置自定义布局文件
        View dialogView = LayoutInflater.from(Activity_bankCardDetail.this).inflate(R.layout.card_bank_dialog_photo, null);
        dialog.setContentView(dialogView);

        ImageView viewById = dialogView.findViewById(R.id.image);
        viewById.setImageBitmap(Conduct_bitmap.loadBitmapFromInternalStorage(this, this.card_bank.getFronturl()));
        dialog.show();

    }
}