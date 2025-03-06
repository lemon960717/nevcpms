package com.example.nevcpms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nevcpms.adapter.PileAdapter;
import com.example.nevcpms.adapter.PileCancelAdapter;
import com.example.nevcpms.bean.MyAppointData;
import com.example.nevcpms.bean.MyAppointment;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.util.CommonData;

import java.time.Period;
import java.util.ArrayList;

public class MyAppointmentActivity extends AppCompatActivity {
    private ArrayList<MyAppointData> appointmentPileList = new ArrayList<MyAppointData>();
    private ArrayList<MyAppointment> appointmentList = new ArrayList<MyAppointment>();

    String selectedTime;
    String phone;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_appointment);


        RecyclerView recyclerView = findViewById(R.id.recycler_view_appointment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

//        PileAdapter adapter = new PileAdapter(appointmentPileList);
//        recyclerView.setAdapter(adapter);

        PileCancelAdapter cancelAdapter = new PileCancelAdapter(this, appointmentPileList);
        recyclerView.setAdapter(cancelAdapter);


        initPiles();


    }

    private void initPiles() {

        //获取当前登录的手机号
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone_str");


        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);

        appointmentList = myDataBaseHelper.findAllAppointment();
        Log.d("appointmentList", "initPiles: " + appointmentList);

        //得到需要展示的所有pile的id
        for (int i = 0; i < appointmentList.size(); i++) {
            if (appointmentList.get(i).getPhone().equals(phone) && appointmentList.get(i).getIsAppointment() == 1) {
                int id = appointmentList.get(i).getPile_id();
                String starTime = appointmentList.get(i).getStarTime();
                String endTime = appointmentList.get(i).getEndTime();
//                isUsed_cancel.setText(starTime + "—" + endTime);
                appointmentPileList.add(new MyAppointData(myDataBaseHelper.getPileById(id),starTime,endTime));
            }
        }
//
//        isUsed_cancel = findViewById(R.id.isUsed_cancel);
//        selectedTime=intent.getStringExtra("selectedTime");
//        isUsed_cancel.setText(selectedTime);

        Log.d("测试", String.valueOf(appointmentPileList.size()));
    }
}
