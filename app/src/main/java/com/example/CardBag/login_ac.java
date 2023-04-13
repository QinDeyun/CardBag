package com.example.CardBag;// MainActivity.java

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import CardBag.R;

public class login_ac extends AppCompatActivity {
    public static login_ac inst = null;
    private static final int REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS = 1001;
    private Button btnBeautiful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBeautiful = findViewById(R.id.btnBeautiful);

        btnBeautiful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理按钮点击事件的逻辑
                Toast.makeText(login_ac.this, "请使用指纹或密码解锁", Toast.LENGTH_SHORT).show();
                KeyguardManager keyguardManager = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    keyguardManager = getSystemService(KeyguardManager.class);
                }
                Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("解锁卡包", "请使用指纹或密码解锁");
                startActivityForResult(intent, REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        inst = this;
        if (requestCode == REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            if (resultCode == RESULT_OK) {
                // 用户输入了正确的设备密码，可以在这里进行相应的操作，如登录
                Toast.makeText(this, "设备密码验证成功", Toast.LENGTH_SHORT).show();
                // 在这里可以执行登录操作，例如跳转到登录成功界面等
                Intent intent = new Intent(inst, AndroidExpandingViewLibrary.class);
                startActivity(intent);
                inst.finish();

            }
        }
    }

}
