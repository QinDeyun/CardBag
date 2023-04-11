package com.example.activitylifecycle_205801;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.activitylifecycle_205801.entity.Card_ID;
import com.example.activitylifecycle_205801.entity.Card_bank;
import com.example.activitylifecycle_205801.entity.Card_student;
import com.example.activitylifecycle_205801.util.MyDatabaseHelper;
import com.imangazaliev.circlemenu.CircleMenu;
import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AndroidExpandingViewLibrary extends AppCompatActivity implements CardStackView.ItemExpendListener {

    private Card_ID[] card_ids;
    private Card_bank[] card_banks;
    private Card_student[] card_students;
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


//        try {
//            // 创建Intent对象，指定要启动的Activity类
//            Intent intent2 = new Intent(AndroidExpandingViewLibrary.this, Activity_IDCardDetail.class);
//            // 添加数据到Intent对象中，key为"message"，value为"Hello, World!"
//            intent2.putExtra("idnum", "131127200203133412");
//            // 启动Activity B
//            startActivity(intent2);
//        }catch (Exception e)
//        {
//            System.out.println(e);
//        }


        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(this);
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

//        ExpandingList expandingList = (ExpandingList) findViewById(R.id.expanding_list_main);
//        ExpandingItem item = expandingList.createNewItem(R.layout.expanding_layout);
//
//        /*ExpandingItem extends from View, so you can call findViewById to get any View inside the layout*/
//        TextView textView = (TextView) item.findViewById(R.id.title);
//        textView.setText("It Works!!");
//
//
//        //This will create 5 items
//        item.createSubItems(2);
//
//        //get a sub item View
//        View subItemZero = item.getSubItemView(0);
//        ((TextView) subItemZero.findViewById(R.id.sub_title)).setText("Cool");
//
//        View subItemOne = item.getSubItemView(1);
//        ((TextView) subItemOne.findViewById(R.id.sub_title)).setText("Awesome");
//
//        item.setIndicatorColorRes(R.color.black);
//        item.setIndicatorIconRes(R.color.black);
//
//        ExpandingItem item2 = expandingList.createNewItem(R.layout.expanding_layout);
//
//        /*ExpandingItem extends from View, so you can call findViewById to get any View inside the layout*/
//        TextView textView2 = (TextView) item2.findViewById(R.id.title);
//        textView2.setText("It Works!!");
//
//
//        //This will create 5 items
//        item2.createSubItems(2);
//
//        //get a sub item View
//        View subItemZero2 = item2.getSubItemView(0);
//        ((TextView) subItemZero2.findViewById(R.id.sub_title)).setText("Cool2");
//
//        View subItemOne2 = item2.getSubItemView(1);
//        ((TextView) subItemOne2.findViewById(R.id.sub_title)).setText("Awesome2");
//
//        item2.setIndicatorColorRes(R.color.black);
//        item2.setIndicatorIconRes(R.color.black);


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

    //查询所有卡片信息
    private void findAllCards() {
        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        this.card_ids = Card_ID.queryAllFromDatabase(db, "card_id");
        this.card_banks = Card_bank.getAllRecordsFromDatabase(db, "card_bank");
        this.card_students = Card_student.queryAllFromDatabase(db, "card_student");
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
        mStackView = (CardStackView) findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(this);
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

        CircleMenu circleMenu = findViewById(R.id.fab);
        circleMenu.setOnItemClickListener((Function1<? super Integer, Unit>) view -> {
            if (view.toString().equals("0")){
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, IDCardRecognitionActivity.class);
                startActivity(intent);
            }
            if (view.toString().equals("1")){
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, BankCardRecognitionActivity.class);
                startActivity(intent);
            }
            if (view.toString().equals("2")){
                Intent intent = new Intent(AndroidExpandingViewLibrary.this, StuCardRecognitionActivity.class);
                startActivity(intent);
            }
            return null;
        });

        this.findAllCards();
        String[] strings=new String[]{};
        for (int i = 0; i < card_ids.length; i++) {
            insert(strings,"身份证"+i);
            System.out.println(card_ids[i].toString());

        }
        for (int i = 0; i < card_banks.length; i++) {
            insert(strings,"银行卡"+i);
            System.out.println(card_banks[i].toString());

        }
        for (int i = 0; i < card_students.length; i++) {
            insert(strings,"校园卡"+i);
            System.out.println(card_students[i].toString());

        }
        AndroidExpandingViewLibrary.TEST_DATAS=strings;

        for (int i = 0; i < AndroidExpandingViewLibrary.TEST_DATAS.length; i++) {
            System.out.println(AndroidExpandingViewLibrary.TEST_DATAS[i]);
        }


    }


}