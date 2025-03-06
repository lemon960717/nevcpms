package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nevcpms.bean.Collection;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.bean.Station;
import com.example.nevcpms.util.CommonData;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class StationDetail extends AppCompatActivity implements View.OnClickListener {

    private MyDataBaseHelper myDataBaseHelper;

    private ArrayList<Collection> collectionList;
    private ArrayList<Pile> pileList;

    private ImageView detailStationImage;
    private TextView detailStationName;
    private TextView detailStationLocation;
    private TextView detailDistrict;
    private TextView detailChargePrice;
    private ImageView collectClick;

    private TextView parkingPrice;
    private TextView servicePrice;
    private TextView openTime;
    private TextView introduction;
    private LinearLayout c_s_phone;
    private LinearLayout navigation;
    private LinearLayout pile;

    private String DSC_S_phone;
    private double longitude;
    private double latitude;

    private int stationId;
    private String phone;
    private boolean flag = false;
    private boolean exist = false;
    private String DSName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.station_detail_layout);

        myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);
        initView();
    }

    private void initView() {

        detailStationName = findViewById(R.id.detail_stationName);
        detailStationLocation = findViewById(R.id.detail_stationLocation);
        detailDistrict = findViewById(R.id.detail_district);
        detailChargePrice = findViewById(R.id.detail_price);
        detailStationImage = findViewById(R.id.detail_images);
        collectClick = findViewById(R.id.collectClick);

        parkingPrice = findViewById(R.id.parking_price);
        servicePrice = findViewById(R.id.service_price);
        openTime = findViewById(R.id.open_time);
        introduction = findViewById(R.id.introduction);
        c_s_phone = findViewById(R.id.customer_service_phone);
        navigation = findViewById(R.id.nav_detail);
        pile = findViewById(R.id.pile);
        //从bundle中取出值

        Intent intent = getIntent();
        Station station = intent.getParcelableExtra("station");
        Log.d("station1", "initView: " + station);
        DSName = station.getName();
        String DSLocation = station.getLocation();
        String DSDistrict = station.getDistrict();
        String DSChargePrice = station.getChargePrice();

        int imageId = station.getImageId();
        Log.d("imageId", "initView: " + imageId);

        stationId = station.getId();
        phone = CommonData.getInstance().getPhoneNum();

        String DSParkingPrice = station.getParkingPrice();
        String DSServicePrice = station.getServicePrice();
        String DSOpenTime = station.getOpenTime();
        String DSIntroduction = station.getIntroduction();
        DSC_S_phone = station.getServicePhone();
        longitude = station.getLongitude();

        latitude = station.getLatitude();


//        bundle.putString("parking_price",parkingPrice);
//        bundle.putString("service_price",servicePrice);
//        bundle.putString("openTime",openTime);
//        bundle.putString("introduction",introduction);
//        bundle.putString("c_s_phone",c_s_phone);
//        bundle.putDouble("longitude",longitude);
//        bundle.putDouble("latitude",latitude);


        //从数据库中获取所有的【收藏条目】
        collectionList = myDataBaseHelper.findAllCollection();
        //判断【本站】是否已经被【本用户】收藏
        //若是，则切换图标
        //若否，保留默认图标
        for (int i = 0; i < collectionList.size(); i++) {
            //数据库中存在此记录，置存在位exist为true
            if (collectionList.get(i).getPhone().equals(phone) && collectionList.get(i).getStation_id() == stationId) {
                exist = true;

                if (collectionList.get(i).getIsCollected() == 1) { //标志位为1，已收藏，置收藏位flag为true
                    collectClick.setImageResource(R.drawable.collected);
                    flag = true;
                }

            }
        }

        detailStationName.setText(DSName);
        detailStationLocation.setText(DSLocation);
        detailDistrict.setText(DSDistrict);
        detailChargePrice.setText(DSChargePrice);

        detailStationImage.setImageResource(imageId);
//        Log.d("TAG", "initView: "+ detailStationImage.setImageResource(imageId);


        parkingPrice.setText(DSParkingPrice);
        servicePrice.setText(DSServicePrice);
        openTime.setText(DSOpenTime);
        introduction.setText(DSIntroduction);

        collectClick.setOnClickListener(this);
        c_s_phone.setOnClickListener(this);
        navigation.setOnClickListener(this);
        pile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collectClick:
                if (flag) {//此前已被收藏，则点击按钮为取消收藏操作
                    collectClick.setImageResource(R.drawable.before_collected);
                    //myDataBaseHelper.deleteCollection(phone,stationId);//弃用删除，虽然用删除也可以达到一样的效果
                    myDataBaseHelper.updateCollection(0, phone, stationId);
                } else {//此前尚未收藏，则点击按钮为收藏操作
                    if (exist) {
                        myDataBaseHelper.updateCollection(1, phone, stationId);
                    } else {
                        myDataBaseHelper.addCollection(phone, stationId, 1);
                    }
                    collectClick.setImageResource(R.drawable.collected);
                }
                break;
            case R.id.customer_service_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + DSC_S_phone));
                startActivity(intent);
                break;
            case R.id.nav_detail:

                try {
                    Intent intent1 = Intent.getIntent("androidamap://viewMap?so urceApplication=找桩&poiname=" + DSName + "&lat=" + latitude + "&lon=" + longitude + "&dev=0");
                    startActivity(intent1);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.pile:
                //从pile对象中获取数据
//                int stationId = .getStationId();
                pileList = myDataBaseHelper.getPileByStationId(stationId);
                Bundle bundle = new Bundle();
                bundle.putInt("stationId", stationId);
//                bundle.putString("phone", phone);

                Intent intent_detail = new Intent();
                intent_detail.setClass(v.getContext(), PileCaseActivity.class);
                intent_detail.putExtras(bundle);
                v.getContext().startActivity(intent_detail);

//                Intent intent1 = new Intent(StationDetail.this, PileCaseActivity.class);
//                startActivity(intent1);
                break;
        }
    }
}