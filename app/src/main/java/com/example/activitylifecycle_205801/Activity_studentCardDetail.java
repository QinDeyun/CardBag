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

import com.example.activitylifecycle_205801.entity.Card_bank;
import com.example.activitylifecycle_205801.entity.Card_student;
import com.example.activitylifecycle_205801.util.Conduct_bitmap;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Activity_studentCardDetail extends AppCompatActivity {
    private Card_student card_student;
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
        setContentView(R.layout.activity_student_card_detail);
        this.init();
        findViewById(R.id.button_edit).setOnClickListener(this.onClickListener);
        findViewById(R.id.photo).setOnClickListener(this.onClickListener);
    }



    void init(){
        Intent intent=getIntent();
        String snum=intent.getStringExtra("snum");
        System.out.println(snum+"测试");
        try {
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
            SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
            this.card_student = Card_student.queryCardByIdnum(writableDatabase, "card_student", snum);
            System.out.println(this.card_student);
        }catch (Exception e){
            System.out.println(e);
        }

        View card = findViewById(R.id.card_student);
        ((TextView)(card.findViewById(R.id.snum))).setText(this.card_student.getSnum());

    }
    void dialogshow_edit(){
        // 创建BottomSheetDialog对象
        BottomSheetDialog dialog = new BottomSheetDialog(Activity_studentCardDetail.this);
        // 设置自定义布局文件
        View dialogView = LayoutInflater.from(Activity_studentCardDetail.this).inflate(R.layout.card_student_dialog_edit, null);
        dialog.setContentView(dialogView);
        EditText sname = (EditText) dialogView.findViewById(R.id.sname);
        sname.setText(this.card_student.getSname());
        EditText snum = (EditText) dialogView.findViewById(R.id.snum);
        snum.setText(this.card_student.getSnum());
        EditText scalss = (EditText) dialogView.findViewById(R.id.scalss);
        scalss.setText(this.card_student.getSclass());
        EditText scollege = (EditText) dialogView.findViewById(R.id.scollege);
        scollege.setText(this.card_student.getScollege());


        Button buttonEdit = dialogView.findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card_student card_student1=card_student;
                card_student1.setSname(sname.getText().toString());
                card_student1.setSclass(scalss.getText().toString());
                card_student1.setScollege(scollege.getText().toString());
                card_student1.setSnum(snum.getText().toString());

                try {
                    MyDatabaseHelper myDatabaseHelper=new MyDatabaseHelper(Activity_studentCardDetail.this);
                    SQLiteDatabase writableDatabase = myDatabaseHelper.getWritableDatabase();
                    Card_student.updateDatabase(writableDatabase,"card_student",card_student1);
                    writableDatabase.close();
                    Toast.makeText(Activity_studentCardDetail.this, "修改成功", Toast.LENGTH_SHORT).show();
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
        BottomSheetDialog dialog = new BottomSheetDialog(Activity_studentCardDetail.this);
        // 设置自定义布局文件
        View dialogView = LayoutInflater.from(Activity_studentCardDetail.this).inflate(R.layout.card_student_dialog_photo, null);
        dialog.setContentView(dialogView);

        ImageView viewById = dialogView.findViewById(R.id.image);
        viewById.setImageBitmap(Conduct_bitmap.loadBitmapFromInternalStorage(this, this.card_student.getFronturl()));
        dialog.show();

    }
}