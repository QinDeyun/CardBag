package com.example.CardBag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.CardBag.entity.Card_ID;
import com.example.CardBag.entity.Card_bank;
import com.example.CardBag.entity.Card_student;
import com.example.CardBag.util.MyDatabaseHelper;
import com.imangazaliev.circlemenu.CircleMenu;
import com.loopeer.cardstack.CardStackView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CardBag.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AndroidExpandingViewLibrary extends AppCompatActivity implements CardStackView.ItemExpendListener {

    public static Card_ID[] card_ids;
    public static Card_bank[] card_banks;
    public static Card_student[] card_students;
    public static String[] TEST_DATAS = new String[]{
            "身份证0",
            "银行卡1",
            "校园卡",
            "银行卡2",
            "银行卡3",
            "银行卡3"
    };
    private CardStackView mStackView;
    private LinearLayout mActionButtonContainer;
    private TestStackAdapter mTestStackAdapter;

    @Override
    public void onItemExpend(boolean expend) {
        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_expanding_view_library);
        this.init();
//        Card_ID.delCard(new MyDatabaseHelper(this).getWritableDatabase(),"card_id",card_ids[0]);
//        try {
//            // 创建Intent对象，指定要启动的Activity类
//            Intent intent2 = new Intent(AndroidExpandingViewLibrary.this, Activity_bankCardDetail.class);
//            // 添加数据到Intent对象中，key为"message"，value为"Hello, World!"
//            intent2.putExtra("number", "6228480028915057377");
////            intent2.putExtra("snum", "202542");
////            intent2.putExtra("idnum", "131127200203133412");
//            // 启动Activity B
//            startActivity(intent2);
//        }catch (Exception e)
//        {
//            System.out.println(e);
//        }

//
//        mStackView = (CardStackView) findViewById(R.id.stackview_main);
//        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
//        mStackView.setItemExpendListener(this);
//        mTestStackAdapter = new TestStackAdapter(this);
//        mStackView.setAdapter(mTestStackAdapter);
//
//
//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
//                    }
//                }
//                , 200
//        );


        CircleMenu circleMenu = findViewById(R.id.fab);
        circleMenu.setOnItemClickListener((Function1<? super Integer, Unit>) view -> {
            if (view.toString().equals("0")) {
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, IDCardRecognitionActivity.class);
                startActivity(intent);
            }
            if (view.toString().equals("1")) {
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, BankCardRecognitionActivity.class);
                startActivity(intent);
            }
            if (view.toString().equals("2")) {
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, StuCardRecognitionActivity.class);
                startActivity(intent);
            }
            return null;
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.init();
    }

    //查询所有卡片信息
    private void findAllCards() {
        try {
            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
            SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
            this.card_ids = Card_ID.queryAllFromDatabase(db, "card_id");
            this.card_banks = Card_bank.getAllRecordsFromDatabase(db, "card_bank");
            this.card_students = Card_student.queryAllFromDatabase(db, "card_student");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }


    // 往字符串数组追加新数据
    private  String[] insert(String[] originalArray, String string ) {

        String[] newArray = new String[originalArray.length + 1];

        System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);

        newArray[newArray.length - 1] = string;

        return newArray;

    }

    //活动初始化
    void init(){
        this.findAllCards();
        List<String> list=new ArrayList<>();
        int n=0;
        for (int i = 0; i < card_ids.length; i++) {
            System.out.println(card_ids[i].toString());
            list.add("身份证");
            n++;

        }
        for (int i = 0; i < card_banks.length; i++) {
            System.out.println(card_banks[i].toString());
            list.add("银行卡");
            n++;

        }
        for (int i = 0; i < card_students.length; i++) {
            System.out.println(card_students[i].toString());
            list.add("校园卡");
            n++;

        }
        System.out.println(list);
        AndroidExpandingViewLibrary.TEST_DATAS=  list.toArray(new String[n]);


        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(this,AndroidExpandingViewLibrary.this);
        mStackView.setAdapter(mTestStackAdapter);


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );

    }


}