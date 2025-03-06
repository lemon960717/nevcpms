package com.example.nevcpms;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nevcpms.util.CommonData;

public class SplashActivity extends AppCompatActivity {

    //跳过按钮
    private Button btnSkip;
    private int currentSecond = 3;
    private SharedPreferences sharedPreferences;


    //private Handler handler = new Handler();

    //创建Handler对象
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 111) {
                btnSkip.setText("跳过" + currentSecond);
                currentSecond--;//时间减一秒
                if (currentSecond >= 0) {
                    handler.sendEmptyMessageDelayed(111, 1000);//一秒后給自己发送一个信息
                }
            }
        }
    };

    private Runnable runnableToLogin = new Runnable() {
        @Override
        public void run() {
            toLoginActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //在本activity里去除难看的actionBar标题栏，实现状态栏沉浸效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        initView();
        initEvent();

        //延迟4秒
        handler.postDelayed(runnableToLogin, 3000);
        handler.sendEmptyMessage(111);//給Handler对象发送信息

    }

    //初始化组件
    public void initView() {
        btnSkip = findViewById(R.id.splash_btn_skip);
    }

    //监听事件
    public void initEvent() {

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //防止LoginActivity被打开两次
                handler.removeCallbacks(runnableToLogin);
                toLoginActivity();
            }
        });
    }

    /**
     * 跳转到登录界面
     */
    private void toLoginActivity() {
        //判断是否已经存在登录信息
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String phoneNumber = sharedPreferences.getString("phone", null);

        if (sharedPreferences.getString("phone", null) == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", phoneNumber);

            CommonData.getInstance().setPhoneNum(phoneNumber);
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        handler.removeCallbacks(runnableToLogin);
    }
}