package com.example.nevcpms.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nevcpms.R;
import com.example.nevcpms.StationDetail;
import com.example.nevcpms.bean.Station;

import java.util.ArrayList;
import java.util.List;

public class
StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {
    private List<Station> mStationList;//定义全局变量
    private String mPhone;//当前登录的手机号

    //定义内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        View stationView;
        ImageView stationImage;
        TextView stationName;
        TextView stationLocation;
        TextView stationDistrict;
        TextView price;


        public ViewHolder(View view) {//构造函数传入的参数view是RecycleView子项的最外层布局
            super(view);
            stationView = view;
            //获取布局中的ImageView和TextView
            stationImage = (ImageView) view.findViewById(R.id.station_images);
            stationName = (TextView) view.findViewById(R.id.station_name);
            stationLocation = (TextView) view.findViewById(R.id.detail_location);
            stationDistrict = (TextView) view.findViewById(R.id.district);
            price = (TextView) view.findViewById(R.id.price);
        }
    }

    //构造函数把要展示的数据源传入进来并赋值给全局变量mFruitList
    //以及把现在是哪个手机号传入
    public StationAdapter(ArrayList<Station> stationList, String phone) {
        mStationList = stationList;
        mPhone = phone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.stationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Station station = mStationList.get(position);
                Intent intent_detail = new Intent(v.getContext(),StationDetail.class);
                intent_detail.putExtra("station",station);
                v.getContext().startActivity(intent_detail);
                //从station对象中得到数据
//                String stationName = station.getName();
//                String chargePrice = station.getChargePrice();
//                String location = station.getLocation();
//                String district = station.getDistrict();
//                int imageId = station.getImageId();
//                int stationId = station.getId();
//
//                String parkingPrice = station.getParkingPrice();
//                String servicePrice = station.getServicePrice();
//                String openTime = station.getOpenTime();
//                String introduction = station.getIntroduction();
//                String c_s_phone = station.getServicePhone();
//                double longitude = station.getLongitude();
//                double latitude = station.getLatitude();
//
//                //通过Bundle绑定数据
//                Bundle bundle = new Bundle();
//                bundle.putString("name", stationName);
//                bundle.putString("chargePrice", chargePrice);
//                bundle.putString("location", location);
//                bundle.putString("district", district);
//                bundle.putInt("imageId", imageId);
//                bundle.putInt("stationId", stationId);
//
//                bundle.putString("parking_price", parkingPrice);
//                bundle.putString("service_price", servicePrice);
//                bundle.putString("openTime", openTime);
//                bundle.putString("introduction", introduction);
//                bundle.putString("c_s_phone", c_s_phone);
//                bundle.putDouble("longitude", longitude);
//                bundle.putDouble("latitude", latitude);

//                bundle.putString("phone", mPhone);


                //使用Bundle传递数据
//                intent_detail.putExtras(bundle);

            }

        });
//        holder.stationImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Station station = mStationList.get(position);
//                Toast.makeText(v.getContext(), "You clicked image" + station.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.stationDistrict.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Station station = mStationList.get(position);
//                Toast.makeText(v.getContext(), "You clicked district" + station.getDistrict(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.stationLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Station station = mStationList.get(position);
//                Toast.makeText(v.getContext(), "You clicked detail_location" + station.getDetail_location(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.price.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Station station = mStationList.get(position);
//                Toast.makeText(v.getContext(), "You clicked price" + station.getPrice(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = mStationList.get(position);
        holder.stationImage.setImageResource(station.getImageId());
        holder.stationName.setText(station.getName());
        holder.stationDistrict.setText(station.getDistrict());
        holder.stationLocation.setText(station.getLocation());
        holder.price.setText(station.getChargePrice());

    }

    @Override
    public int getItemCount() {
        return mStationList.size();
    }

}


