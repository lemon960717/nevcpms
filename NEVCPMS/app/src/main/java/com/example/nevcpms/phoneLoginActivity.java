package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.RequestCallback;

public class phoneLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG ="phoneLogin" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_login_layout);

        //SDK初始化
        JVerificationInterface.init(this, 5000, new RequestCallback<String>() {
            @Override
            public void onResult(int code, String msg) {
                Log.d("tag","code = " + code + " msg = " + msg);
            }
        });

        //获取sdk初始化是否成功标识
        boolean isSuccess = JVerificationInterface.isInitSuccess();

        //判断当前的手机网络环境是否可以使用认证。
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if(!verifyEnable){
            Log.d(TAG,"当前网络环境不支持认证");
            return;
        }

    }

    @Override
    public void onClick(View v) {

    }
}