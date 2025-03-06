package com.example.nevcpms;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {
    BottomNavigationView bottomNavigation;

    /**
     * 初始化定位
     */
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigation = findViewById(R.id.bottom_navigation);

        //获取navController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //通过setupWithNavController将底部导航和导航控制器进行绑定
        NavigationUI.setupWithNavController(bottomNavigation, navController);

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
        //初始化定位
        initLocation();
        //开始定位
        startLocation();
        //停止定位
        stopLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 初始化定位
     */
    private void initLocation() {
        try {
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化AMapLocationClientOption对象
            mLocationOption = new AMapLocationClientOption();
            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //获取最近3s内精度最高的一次定位结果
            mLocationOption.setOnceLocationLatest(true);
            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);
            //设置定位超时时间，单位是毫秒
            mLocationOption.setHttpTimeOut(6000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 开始定位
     */
    private void startLocation() {
        if (mLocationClient != null) mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    private void stopLocation() {
        if (mLocationClient != null) mLocationClient.stopLocation();
    }

    private void showMsg(CharSequence nevcpms) {
        Toast.makeText(this, nevcpms, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            showMsg("定位失败，aMapLocation 为空");
            return;
        }
        // 获取定位结果
        if (aMapLocation.getErrorCode() == 0) {
            // 定位成功
            showMsg("定位成功");
//            aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//            aMapLocation.getLatitude();//获取纬度
//            aMapLocation.getLongitude();//获取经度
//            aMapLocation.getAccuracy();//获取精度信息
//            aMapLocation.getAddress();//详细地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//            aMapLocation.getCountry();//国家信息
//            aMapLocation.getProvince();//省信息
//            aMapLocation.getCity();//城市信息
            String result = aMapLocation.getDistrict();//城区信息
//            aMapLocation.getStreet();//街道信息
//            aMapLocation.getStreetNum();//街道门牌号信息
//            aMapLocation.getCityCode();//城市编码
//            aMapLocation.getAdCode();//地区编码
//            aMapLocation.getAoiName();//获取当前定位点的AOI信息
//            aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//            aMapLocation.getFloor();//获取当前室内定位的楼层
//            aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态

//            binding.tvAddress.setText(result);
            showMsg("当前定位结果"+result);
            // 停止定位
            stopLocation();
        } else {
            // 定位失败
            showMsg("定位失败，错误：" + aMapLocation.getErrorInfo());
            Log.e("aMapLocation","location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }

    }
}