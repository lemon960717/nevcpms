package com.example.nevcpms.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.example.nevcpms.MyDataBaseHelper;
import com.example.nevcpms.R;
import com.example.nevcpms.RouteNaviActivity;
import com.example.nevcpms.adapter.InfoWinAdapter;
import com.example.nevcpms.bean.Station;


import java.net.URISyntaxException;
import java.util.ArrayList;

public class BFragment extends Fragment implements AMap.OnMyLocationChangeListener, AMap.InfoWindowAdapter, AMap.OnMapClickListener, AMap.OnMarkerClickListener {

    private TextureMapView textureMapView;
    private View view;
    private View infoWindow = null;
    private AMap aMap;
    private Location mlocation;


    private ArrayList<Station> list = new ArrayList<Station>();
    private MyDataBaseHelper myDataBaseHelper;
    private double lat;
    private double log;
    private String name;
    private String location;
    private String CSPhone;

    private Station station;


    private LatLng mylatlng;
    private InfoWinAdapter infoWinAdapter;//自定义标记点的布局
    private Marker marker;
    private Context context;

    private LinearLayout nav;
    private LinearLayout phone;

    //武汉市经纬度范围113°41′-115°05′、北纬29°58′-31°22′
    public static final LatLng WUHAN = new LatLng(30.512487, 114.349680);// 武汉市经纬度
    protected static CameraPosition cameraPosition;


    LatLng getTarget() {
        return WUHAN;
    }

    CameraPosition getCameraPosition() {
        return cameraPosition;
    }

    void setCameraPosition(CameraPosition cameraPosition) {
        BFragment.cameraPosition = cameraPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_b, container, false);
        }
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textureMapView = getView().findViewById(R.id.map);

        //初始化显示地图
        if (textureMapView != null) {
            textureMapView.onCreate(savedInstanceState);
            aMap = textureMapView.getMap();

            //设置初级缩放级别和视角
            if (getCameraPosition() == null) {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(getTarget(), 10, 0, 0)));
            } else {
                aMap.moveCamera(CameraUpdateFactory.newCameraPosition(getCameraPosition()));
            }

            //设置希望展示的地图缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));


            //实现定位蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            ;

            //初始化定位蓝点样式类
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
            //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

            //显示定位蓝点
            myLocationStyle.showMyLocation(true);
            myLocationStyle.strokeWidth(20);
            myLocationStyle.strokeColor(Color.argb(10, 20, 30, 40));// 设置圆形的边框颜色
            myLocationStyle.radiusFillColor(Color.argb(10, 20, 30, 40));// 设置圆形的填充颜色


            //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.people));

            //定位蓝点展现模式
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
            //连续定位、且不将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）

            aMap.showIndoorMap(false);     //true：显示室内地图；false：不显示；
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.setOnMyLocationChangeListener(this);


            //自定义InfoWindow
            aMap.setOnMarkerClickListener(this);
            aMap.setInfoWindowAdapter(this);


            myDataBaseHelper = new MyDataBaseHelper(getContext(), "LBS.db", null, 1);
            list = myDataBaseHelper.getAllStationData();
            for (int i = 0; i < list.size(); i++) {
                lat = list.get(i).getLatitude();
                log = list.get(i).getLongitude();
                LatLng latLng = new LatLng(lat, log);

                name = list.get(i).getName();
                location = list.get(i).getLocation();
//                district=list.get(i).getDistrict();
//                charge_price=list.get(i).getChargePrice();
//                openTime=list.get(i).getOpenTime();
//                CSPhone=list.get(i).getServicePhone();

                //采用bundle来传值给每一个点的点击窗口
                //通过在marker中添加bundle对象，在点击marker的时候确定数据进行显示或者跳转
//                Bundle bundle=new Bundle();
//                bundle.putDouble("lat",lat);
//                bundle.putDouble("log",log);
//                bundle.putString("name",name);
//                bundle.putString("location",location);
//                bundle.putString("district",district);
//                bundle.putString("charge_price",charge_price);
//                bundle.putString("openTime",openTime);
//                bundle.putString("CSPhone",CSPhone);

                //大坑：高德地图中无setExtraInfo方法，无法通过bundle传值,只能用object
                //marker.setExtraInfo(bundle);

                //显示地图中所有充电站的位置
                marker = aMap.addMarker(new MarkerOptions().position(latLng).title(name).snippet(location).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon1)));
                marker.setObject(list.get(i));
            }


        }
    }


    @Override
    public void onMyLocationChange(Location location) {
        //从location对象中获取经纬度信息，地址描述信息，建议拿到位置之后调用逆地理编码接口获取（获取地址描述数据章节有介绍）
        mlocation = location;
        if (mlocation != null) {
            mylatlng = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
            Log.d("mlocation", "获取的经纬度信息: "+mlocation);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        textureMapView.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroy() {
        setCameraPosition(aMap.getCameraPosition());
        super.onDestroy();
        textureMapView.onDestroy();
    }


    @Override
    public View getInfoWindow(Marker marker) {
        if (infoWindow == null) {
            infoWindow = LayoutInflater.from(getContext()).inflate(R.layout.infowindow_layout, null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义info_window窗口
     * 如果界面比较简单，可不管
     */
    private void render(Marker marker, View infoWindow) {
        TextView title = infoWindow.findViewById(R.id.agent_name);
        TextView content = infoWindow.findViewById(R.id.agent_addr);
        ImageView imageView = infoWindow.findViewById(R.id.station_images);

        station = (Station) marker.getObject();
        int imageId = station.getImageId();

        String titleStr = marker.getTitle();
        String contentStr = marker.getSnippet();
        title.setText(titleStr);
        content.setText(contentStr);
        imageView.setImageResource(imageId);

    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
//            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }


        station = (Station) marker.getObject();

        showStationDialog(station);

        return true; // 返回:true 表示点击marker 后marker 不会移动到地图中心；返回false 表示点击marker 后marker 会自动移动到地图中心
    }


    /**
     * 弹出对话窗口
     */
    private void showStationDialog(Station station) {

        Station object = station;

        //R.style.***一定要写，不然不能充满整个屏宽，引用R.style.AppTheme就可以
        final AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AppTheme).create();
        View view = View.inflate(getContext(), R.layout.map_click_layout, null);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);

        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //window.setWindowAnimations(R.style.dialogStyle);
//        window.getDecorView().setPadding(0, 0, 0, 0);

        //设置dialog弹出后会点击屏幕或物理返回键，dialog不消失
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        window.setContentView(view);

        //获得window窗口的属性
        WindowManager.LayoutParams params = window.getAttributes();
        //设置窗口宽度为充满全屏
        params.width = WindowManager.LayoutParams.MATCH_PARENT;//如果不设置,可能部分机型出现左右有空隙,也就是产生margin的感觉
        //设置窗口高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;//显示dialog的时候,就显示软键盘
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;//就是这个属性导致window后所有的东西都成暗淡

        params.dimAmount = 0.0f;//设置对话框的透明程度背景(非布局的透明度)

        //将设置好的属性set回去
        window.setAttributes(params);

        ImageView dialogImage = dialog.getWindow().findViewById(R.id.dialog_image);
        TextView dialogName = dialog.getWindow().findViewById(R.id.dialog_name);
        TextView dialogLocation = dialog.getWindow().findViewById(R.id.dialog_location);
        TextView dialogDistrict = dialog.getWindow().findViewById(R.id.dialog_district);
        TextView dialogPrice = dialog.getWindow().findViewById(R.id.dialog_price);
        TextView dialogOpenTime = dialog.getWindow().findViewById(R.id.dialog_time);

        dialogImage.setImageResource(object.getImageId());
        dialogName.setText(object.getName());
        dialogLocation.setText(object.getLocation());
        dialogDistrict.setText(object.getDistrict());
        dialogPrice.setText(object.getChargePrice());
        dialogOpenTime.setText(object.getOpenTime());

        CSPhone = object.getServicePhone();
        lat = object.getLatitude();
        log = object.getLongitude();
        name = object.getName();


        dialog.getWindow().findViewById(R.id.dialog_nav).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("intent_test", "有没有触发点击事件-导航");

//                openGaoDeMap(lat,log,name);
                Intent intent = new Intent(getActivity(), RouteNaviActivity.class);
                intent.putExtra("gps", true);
                intent.putExtra("start", new NaviLatLng(mlocation.getLatitude(), mlocation.getLongitude()));
                intent.putExtra("end", new NaviLatLng(lat, log));
                startActivity(intent);
            }
        });
        dialog.getWindow().findViewById(R.id.dialog_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("intent_test", "有没有触发点击事件-电话");
                Intent intent2 = new Intent(Intent.ACTION_DIAL);
                intent2.setData(Uri.parse("tel:" + CSPhone));
                startActivity(intent2);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (getId()){
//            case R.id.dialog_nav:
//                Log.d("intent_test","有没有触发点击事件-导航");
//                openGaoDeMap();
//                break;
//            case R.id.dialog_phone:
//                Log.d("intent_test","有没有触发点击事件-电话");
//                Intent intent2=new Intent(Intent.ACTION_DIAL);
//                intent2.setData(Uri.parse("tel:13599351356"));
//                startActivity(intent2);
//                break;
//        }
//    }


    //打开本地安装的高德地图直接导航
    //已弃用
    private void openGaoDeMap(double lat, double log, String name) {
        String stationName = name;
        double latitude = lat;
        double longitude = log;
        Log.d("intent_test", "有没有触发点击事件22");
        try {
            Intent intent = Intent.getIntent("androidamap://viewMap?sourceApplication=找桩app&poiname=" + stationName + "&lat=" + latitude + "&lon=" + longitude + "&dev=0");
            startActivity(intent);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Log.d("intent_test", "有没有触发点击事件33");
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }


}