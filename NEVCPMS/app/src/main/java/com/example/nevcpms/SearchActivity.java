package com.example.nevcpms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.nevcpms.adapter.StationAdapter;
import com.example.nevcpms.bean.Station;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Station> targetStations=new ArrayList<>();
    private MyDataBaseHelper myDataBaseHelper;
    private EditText searchContent;
    private ImageView searchButton;
    private String phone;
    private RecyclerView recyclerView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        //在本activity里去除难看的actionBar标题栏，实现状态栏沉浸效果
        if (Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //数据库
        myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);
        //初始化控件
        initView();

        //获取当前登录的电话号码的信息
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone");

        //获取到recyclerView实例
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view_search);
        // StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        //创建StationAdapter实例
//        StationAdapter adapter=new StationAdapter(targetStations,phone);
//        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        searchContent=findViewById(R.id.search_content);
        searchButton=findViewById(R.id.search_btn);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr=searchContent.getText().toString().trim();
                targetStations=myDataBaseHelper.getSpecialStationData(nameStr);
                Log.d("测试target", String.valueOf(targetStations.size()));

                if(targetStations.size()==0){
                    Toast.makeText(getBaseContext(), "没有查询到相应结果~", Toast.LENGTH_SHORT).show();
                }

                //创建StationAdapter实例
                StationAdapter adapter=new StationAdapter(targetStations,phone);
                recyclerView.setAdapter(adapter);
            }
        });
    }



}