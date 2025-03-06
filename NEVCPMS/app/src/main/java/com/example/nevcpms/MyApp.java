package com.example.nevcpms;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.services.core.ServiceSettings;

import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.RequestCallback;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        JVerificationInterface.setDebugMode(true);
        final long start = System.currentTimeMillis();
        JVerificationInterface.init(this, new RequestCallback<String>() {
            @Override
            public void onResult(int code, String result) {
                Log.d("MyApp", "[init] code = " + code + " result = " + result + " consists = " + (System.currentTimeMillis() - start));
            }
        });

        /*
        * 随着工信部推行加强个人信息隐私的保护政策，各大SDK都需要相应增加一个API接口，确保用户是在知道会采集信息的情况下使用某一个功能，
        * 所以我们在使用高德的定位、地图、搜索功能时，需要先同意隐私政策，不通过则无法使用，一般是通过App启动之后出现一个弹窗，
        * 弹窗中你告知用户那些信息被采集，用到了那些SDK，作用是什么。
        * */
//        Context mContext = this;
//        // 定位隐私政策同意
//        AMapLocationClient.updatePrivacyShow(mContext,true,true);
//        AMapLocationClient.updatePrivacyAgree(mContext,true);
//        // 地图隐私政策同意
//        MapsInitializer.updatePrivacyShow(mContext,true,true);
//        MapsInitializer.updatePrivacyAgree(mContext,true);
//        // 搜索隐私政策同意
//        ServiceSettings.updatePrivacyShow(mContext,true,true);
//        ServiceSettings.updatePrivacyAgree(mContext,true);

    }


}