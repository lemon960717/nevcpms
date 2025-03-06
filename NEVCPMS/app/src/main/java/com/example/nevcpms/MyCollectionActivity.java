package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.nevcpms.adapter.StationAdapter;
import com.example.nevcpms.bean.Collection;
import com.example.nevcpms.bean.Station;

import java.util.ArrayList;

public class MyCollectionActivity extends AppCompatActivity {


    private ArrayList<Station> collectedStationList=new ArrayList<Station>();
    private ArrayList<Collection> collectionList=new ArrayList<Collection>();
    private ArrayList<Integer> IdList=new ArrayList<>();
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_layout);

        //初始化
        initStations();

        //获取到recyclerView实例
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.recycler_view_collection);

        // StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        //创建StationAdapter实例
        StationAdapter adapter=new StationAdapter(collectedStationList,phone);
        recyclerView.setAdapter(adapter);

    }

    private void initStations(){

        //获取当前登录的手机号
        Intent intent= getIntent();
        phone=intent.getStringExtra("phone_str");

        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);

        collectionList=myDataBaseHelper.findAllCollection();
        Log.d("collectionList", "initStations: "+collectionList);

        //得到需要展示的所有station的id
        for(int i=0;i<collectionList.size();i++){
            if(collectionList.get(i).getPhone().equals(phone)&&collectionList.get(i).getIsCollected()==1){
                int id=collectionList.get(i).getStation_id();
                collectedStationList.add(myDataBaseHelper.getStationById(id));
            }
        }
        Log.d("测试", String.valueOf(collectedStationList.size()));

    }
}