package com.example.nevcpms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.example.nevcpms.R;

public class InfoWinAdapter implements AMap.InfoWindowAdapter, View.OnClickListener{
    private Context mContext;
    private LatLng latLng;
    private TextView nameTV;
    private String agentName;
    private TextView addrTV;
    private String snippet;



    //必须实现的接口中的方法
    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        agentName = marker.getTitle();
    }

    @SuppressLint("StringFormatInvalid")

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.infowindow_layout, null);
        nameTV = (TextView) view.findViewById(R.id.agent_name);
        addrTV = (TextView) view.findViewById(R.id.agent_addr);

        nameTV.setText(agentName);
        addrTV.setText(String.format(mContext.getString(R.string.turn_to_map),snippet));

        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
