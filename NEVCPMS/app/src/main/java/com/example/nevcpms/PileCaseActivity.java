package com.example.nevcpms;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nevcpms.adapter.PileAdapter;
import com.example.nevcpms.bean.MyAppointment;
import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.bean.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PileCaseActivity extends AppCompatActivity {
    private MyDataBaseHelper myDataBaseHelper;
    RecyclerView recyclerView;
    private ArrayList<Station> stationList = new ArrayList<Station>();
    private ArrayList<Pile> pileList = new ArrayList<Pile>();
    private ArrayList<Integer> IdList = new ArrayList<>();
    private ArrayList<MyAppointment> myAppointmentList;
    private int startHour = 0, startMinute = 0;
    private int endHour = 0, endMinute = 0;
    TextView tvAppointment;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pile_case_layout);

//        myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);
        initView();
        //初始化
//        initPile();
//        //FruitAdapter实例 展示充电桩
//        PileAdapter adapter1 = new PileAdapter(pileList);
//        recyclerView.setAdapter(adapter1);


//        int position = holder.getAdapterPosition();
//        Station station = mStationList.get(position);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);
        Bundle bundle = this.getIntent().getExtras();
        int id = bundle.getInt("stationId");
        pileList = myDataBaseHelper.getPileByStationId(id);
        Log.d("pileList", "onStart: "+pileList);
        PileAdapter adapter1 = new PileAdapter(pileList);
        recyclerView.setAdapter(adapter1);


}

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view_pileCase);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PileCaseActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }


    private void initPile() {


//        myDataBaseHelper.addPile(4109, "12KW", 0, "A1001");
//        myDataBaseHelper.addPile(4109, "12KW", 0, "A1002");
//        myDataBaseHelper.addPile(4109, "12KW", 0, "A1003");
//        myDataBaseHelper.addPile(4109, "12KW", 0, "A1004");
//
//        myDataBaseHelper.addPile(9, "12KW", 0, "A2001");
//        myDataBaseHelper.addPile(9, "12KW", 0, "A2002");
//        myDataBaseHelper.addPile(9, "12KW", 0, "A2003");
//        myDataBaseHelper.addPile(9, "12KW", 0, "A2004");
//
//        myDataBaseHelper.addPile(2, "12KW", 0, "A3001");
//        myDataBaseHelper.addPile(2, "12KW", 0, "A3002");
//        myDataBaseHelper.addPile(2, "12KW", 0, "A3003");
//        myDataBaseHelper.addPile(2, "12KW", 0, "A3004");
//
//        myDataBaseHelper.addPile(4198, "12KW", 0, "A4001");
//        myDataBaseHelper.addPile(4198, "12KW", 0, "A4002");
//        myDataBaseHelper.addPile(4198, "12KW", 0, "A4003");
//        myDataBaseHelper.addPile(4198, "12KW", 0, "A4004");
//
//        myDataBaseHelper.addPile(98, "12KW", 0, "A5001");
//        myDataBaseHelper.addPile(98, "12KW", 0, "A5002");
//        myDataBaseHelper.addPile(98, "12KW", 0, "A5003");
//        myDataBaseHelper.addPile(98, "12KW", 0, "A5004");
//
//        myDataBaseHelper.addPile(14, "12KW", 0, "A6001");
//        myDataBaseHelper.addPile(14, "12KW", 0, "A6002");
//        myDataBaseHelper.addPile(14, "12KW", 0, "A6003");
//        myDataBaseHelper.addPile(14, "12KW", 0, "A6004");
//
//        myDataBaseHelper.addPile(4119, "12KW", 0, "A7001");
//        myDataBaseHelper.addPile(4119, "12KW", 0, "A7002");
//        myDataBaseHelper.addPile(4119, "12KW", 0, "A7003");
//        myDataBaseHelper.addPile(4119, "12KW", 0, "A7004");
//
//        myDataBaseHelper.addPile(19, "12KW", 0, "A8001");
//        myDataBaseHelper.addPile(19, "12KW", 0, "A8002");
//        myDataBaseHelper.addPile(19, "12KW", 0, "A8003");
//        myDataBaseHelper.addPile(19, "12KW", 0, "A8004");
//
//        myDataBaseHelper.addPile(4183, "12KW", 0, "A9001");
//        myDataBaseHelper.addPile(4183, "12KW", 0, "A9002");
//        myDataBaseHelper.addPile(4183, "12KW", 0, "A9003");
//        myDataBaseHelper.addPile(4183, "12KW", 0, "A9004");
//
//        myDataBaseHelper.addPile(83, "12KW", 0, "A101");
//        myDataBaseHelper.addPile(83, "12KW", 0, "A102");
//        myDataBaseHelper.addPile(83, "12KW", 0, "A103");
//        myDataBaseHelper.addPile(83, "12KW", 0, "A104");
//
//        myDataBaseHelper.addPile(4, "12KW", 0, "A1101");
//        myDataBaseHelper.addPile(4, "12KW", 0, "A1102");
//        myDataBaseHelper.addPile(4, "12KW", 0, "A1103");
//        myDataBaseHelper.addPile(4, "12KW", 0, "A1104");
//
//        myDataBaseHelper.addPile(3, "12KW", 0, "A1201");
//        myDataBaseHelper.addPile(3, "12KW", 0, "A1202");
//        myDataBaseHelper.addPile(3, "12KW", 0, "A1203");
//        myDataBaseHelper.addPile(3, "12KW", 0, "A1204");
//
//        myDataBaseHelper.addPile(38, "12KW", 0, "A1301");
//        myDataBaseHelper.addPile(38, "12KW", 0, "A1302");
//        myDataBaseHelper.addPile(38, "12KW", 0, "A1303");
//        myDataBaseHelper.addPile(38, "12KW", 0, "A1304");
//
//        myDataBaseHelper.addPile(39, "12KW", 0, "B1001");
//        myDataBaseHelper.addPile(39, "12KW", 0, "B1002");
//        myDataBaseHelper.addPile(39, "12KW", 0, "B1003");
//        myDataBaseHelper.addPile(39, "12KW", 0, "B1004");
//
//        myDataBaseHelper.addPile(78, "12KW", 0, "B2001");
//        myDataBaseHelper.addPile(78, "12KW", 0, "B2002");
//        myDataBaseHelper.addPile(78, "12KW", 0, "B2003");
//        myDataBaseHelper.addPile(78, "12KW", 0, "B2004");
//
//        myDataBaseHelper.addPile(4175, "12KW", 0, "B3001");
//        myDataBaseHelper.addPile(4175, "12KW", 0, "B3002");
//        myDataBaseHelper.addPile(4175, "12KW", 0, "B3003");
//        myDataBaseHelper.addPile(4175, "12KW", 0, "B3004");
//
//        myDataBaseHelper.addPile(75, "12KW", 0, "B4001");
//        myDataBaseHelper.addPile(75, "12KW", 0, "B4002");
//        myDataBaseHelper.addPile(75, "12KW", 0, "B4003");
//        myDataBaseHelper.addPile(75, "12KW", 0, "B4004");
//
//        myDataBaseHelper.addPile(40, "12KW", 0, "B5001");
//        myDataBaseHelper.addPile(40, "12KW", 0, "B5002");
//        myDataBaseHelper.addPile(40, "12KW", 0, "B5003");
//        myDataBaseHelper.addPile(40, "12KW", 0, "B5004");
//
//        myDataBaseHelper.addPile(35, "12KW", 0, "B6001");
//        myDataBaseHelper.addPile(35, "12KW", 0, "B6002");
//        myDataBaseHelper.addPile(35, "12KW", 0, "B6003");
//        myDataBaseHelper.addPile(35, "12KW", 0, "B6004");
//
//        myDataBaseHelper.addPile(12, "12KW", 0, "B7001");
//        myDataBaseHelper.addPile(12, "12KW", 0, "B7002");
//        myDataBaseHelper.addPile(12, "12KW", 0, "B7003");
//        myDataBaseHelper.addPile(12, "12KW", 0, "B7004");
//
//        myDataBaseHelper.addPile(56, "12KW", 0, "B8001");
//        myDataBaseHelper.addPile(56, "12KW", 0, "B8002");
//        myDataBaseHelper.addPile(56, "12KW", 0, "B8003");
//        myDataBaseHelper.addPile(56, "12KW", 0, "B8004");
//
//        myDataBaseHelper.addPile(89, "12KW", 0, "B9001");
//        myDataBaseHelper.addPile(89, "12KW", 0, "B9002");
//        myDataBaseHelper.addPile(89, "12KW", 0, "B9003");
//        myDataBaseHelper.addPile(89, "12KW", 0, "B9004");
//
//        myDataBaseHelper.addPile(44, "12KW", 0, "B101");
//        myDataBaseHelper.addPile(44, "12KW", 0, "B102");
//        myDataBaseHelper.addPile(44, "12KW", 0, "B103");
//
//        myDataBaseHelper.addPile(8, "12KW", 0, "B1101");
//        myDataBaseHelper.addPile(8, "12KW", 0, "B1102");
//        myDataBaseHelper.addPile(8, "12KW", 0, "B1103");
//        myDataBaseHelper.addPile(8, "12KW", 0, "B1104");
//
//        myDataBaseHelper.addPile(4168, "12KW", 0, "B1201");
//        myDataBaseHelper.addPile(4168, "12KW", 0, "B1202");
//        myDataBaseHelper.addPile(4168, "12KW", 0, "B1203");
//        myDataBaseHelper.addPile(4168, "12KW", 0, "B1204");
//
//        myDataBaseHelper.addPile(68, "12KW", 0, "B1301");
//        myDataBaseHelper.addPile(68, "12KW", 0, "B1302");
//        myDataBaseHelper.addPile(68, "12KW", 0, "B1303");
//        myDataBaseHelper.addPile(68, "12KW", 0, "B1304");
//
//        myDataBaseHelper.addPile(4101, "12KW", 0, "C1001");
//        myDataBaseHelper.addPile(4101, "12KW", 0, "C1002");
//        myDataBaseHelper.addPile(4101, "12KW", 0, "C1003");
//
//        myDataBaseHelper.addPile(1, "12KW", 0, "C2001");
//        myDataBaseHelper.addPile(1, "12KW", 0, "C2002");
//
//        myDataBaseHelper.addPile(4143, "12KW", 0, "C3001");
//        myDataBaseHelper.addPile(4143, "12KW", 0, "C3002");
//        myDataBaseHelper.addPile(4143, "12KW", 0, "C3003");
//        myDataBaseHelper.addPile(4143, "12KW", 0, "C3004");
//
//        myDataBaseHelper.addPile(43, "12KW", 0, "C4001");
//        myDataBaseHelper.addPile(43, "12KW", 0, "C4002");
//        myDataBaseHelper.addPile(43, "12KW", 0, "C4003");
//        myDataBaseHelper.addPile(43, "12KW", 0, "C4004");
//
//        myDataBaseHelper.addPile(88, "12KW", 0, "C5001");
//        myDataBaseHelper.addPile(88, "12KW", 0, "C5002");
//        myDataBaseHelper.addPile(88, "12KW", 0, "C5003");
//
//        myDataBaseHelper.addPile(47, "12KW", 0, "C6001");
//        myDataBaseHelper.addPile(47, "12KW", 0, "C6002");
//        myDataBaseHelper.addPile(47, "12KW", 0, "C6003");
//        myDataBaseHelper.addPile(47, "12KW", 0, "C6004");
//
//        myDataBaseHelper.addPile(4194, "12KW", 0, "C7001");
//        myDataBaseHelper.addPile(4194, "12KW", 0, "C7002");
//        myDataBaseHelper.addPile(4194, "12KW", 0, "C7003");
//
//        myDataBaseHelper.addPile(94, "12KW", 0, "C8001");
//        myDataBaseHelper.addPile(94, "12KW", 0, "C8002");
//        myDataBaseHelper.addPile(94, "12KW", 0, "C8003");
//        myDataBaseHelper.addPile(94, "12KW", 0, "C8004");
//
//        myDataBaseHelper.addPile(13, "12KW", 0, "C9001");
//        myDataBaseHelper.addPile(13, "12KW", 0, "C9002");
//        myDataBaseHelper.addPile(13, "12KW", 0, "C9003");
//        myDataBaseHelper.addPile(13, "12KW", 0, "C9004");
//
//        myDataBaseHelper.addPile(4159, "12KW", 0, "C101");
//        myDataBaseHelper.addPile(4159, "12KW", 0, "C102");
//        myDataBaseHelper.addPile(4159, "12KW", 0, "C103");
//        myDataBaseHelper.addPile(4159, "12KW", 0, "C104");
//
//        myDataBaseHelper.addPile(59, "12KW", 0, "C1101");
//        myDataBaseHelper.addPile(59, "12KW", 0, "C1102");
//        myDataBaseHelper.addPile(59, "12KW", 0, "C1103");
//        myDataBaseHelper.addPile(59, "12KW", 0, "C1104");
//
//        myDataBaseHelper.addPile(85, "12KW", 0, "C1201");
//        myDataBaseHelper.addPile(85, "12KW", 0, "C1202");
//        myDataBaseHelper.addPile(85, "12KW", 0, "C1203");
//        myDataBaseHelper.addPile(85, "12KW", 0, "C1204");
//
//        myDataBaseHelper.addPile(97, "12KW", 0, "C1301");
//        myDataBaseHelper.addPile(97, "12KW", 0, "C1302");
//        myDataBaseHelper.addPile(97, "12KW", 0, "C1303");
//        myDataBaseHelper.addPile(97, "12KW", 0, "C1304");
//
//        myDataBaseHelper.addPile(25, "12KW", 0, "D1001");
//        myDataBaseHelper.addPile(25, "12KW", 0, "D1002");
//        myDataBaseHelper.addPile(25, "12KW", 0, "D1003");
//        myDataBaseHelper.addPile(25, "12KW", 0, "D1004");
//
//
//        myDataBaseHelper.addPile(90, "12KW", 0, "D2001");
//        myDataBaseHelper.addPile(90, "12KW", 0, "D2002");
//        myDataBaseHelper.addPile(90, "12KW", 0, "D2003");
//
//        myDataBaseHelper.addPile(36, "12KW", 0, "D3001");
//        myDataBaseHelper.addPile(36, "12KW", 0, "D3002");
//        myDataBaseHelper.addPile(36, "12KW", 0, "D3003");
//        myDataBaseHelper.addPile(36, "12KW", 0, "D3004");
//
//
//        myDataBaseHelper.addPile(46, "12KW", 0, "D4001");
//        myDataBaseHelper.addPile(46, "12KW", 0, "D4002");
//
//        myDataBaseHelper.addPile(26, "12KW", 0, "D5001");
//        myDataBaseHelper.addPile(26, "12KW", 0, "D5002");
//        myDataBaseHelper.addPile(26, "12KW", 0, "D5003");
//        myDataBaseHelper.addPile(26, "12KW", 0, "D5004");
//
//        myDataBaseHelper.addPile(10, "12KW", 0, "D6001");
//        myDataBaseHelper.addPile(10, "12KW", 0, "D6002");
//        myDataBaseHelper.addPile(10, "12KW", 0, "D6003");
//        myDataBaseHelper.addPile(10, "12KW", 0, "D6004");
//
//        myDataBaseHelper.addPile(66, "12KW", 0, "D7001");
//        myDataBaseHelper.addPile(66, "12KW", 0, "D7002");
//        myDataBaseHelper.addPile(66, "12KW", 0, "D7003");
//        myDataBaseHelper.addPile(66, "12KW", 0, "D7004");
//
//        myDataBaseHelper.addPile(42, "12KW", 0, "D8001");
//        myDataBaseHelper.addPile(42, "12KW", 0, "D8002");
//        myDataBaseHelper.addPile(42, "12KW", 0, "D8003");
//        myDataBaseHelper.addPile(42, "12KW", 0, "D8004");
//
//        myDataBaseHelper.addPile(52, "12KW", 0, "D9001");
//        myDataBaseHelper.addPile(52, "12KW", 0, "D9002");
//        myDataBaseHelper.addPile(52, "12KW", 0, "D9003");
//        myDataBaseHelper.addPile(52, "12KW", 0, "D9004");
//
//        myDataBaseHelper.addPile(81, "12KW", 0, "D101");
//        myDataBaseHelper.addPile(81, "12KW", 0, "D102");
//        myDataBaseHelper.addPile(81, "12KW", 0, "D103");
//        myDataBaseHelper.addPile(81, "12KW", 0, "D104");
//
//        myDataBaseHelper.addPile(74, "12KW", 0, "D1101");
//        myDataBaseHelper.addPile(74, "12KW", 0, "D1102");
//        myDataBaseHelper.addPile(74, "12KW", 0, "D1103");
//
//        myDataBaseHelper.addPile(76, "12KW", 0, "D1201");
//        myDataBaseHelper.addPile(76, "12KW", 0, "D1202");
//        myDataBaseHelper.addPile(76, "12KW", 0, "D1203");
//        myDataBaseHelper.addPile(76, "12KW", 0, "D1204");
//
//        myDataBaseHelper.addPile(51, "12KW", 0, "D1301");
//        myDataBaseHelper.addPile(51, "12KW", 0, "D1302");
//        myDataBaseHelper.addPile(51, "12KW", 0, "D1303");
//        myDataBaseHelper.addPile(51, "12KW", 0, "D1304");
//
//        myDataBaseHelper.addPile(79, "12KW", 0, "E1001");
//        myDataBaseHelper.addPile(79, "12KW", 0, "E1002");
//        myDataBaseHelper.addPile(79, "12KW", 0, "E1003");
//        myDataBaseHelper.addPile(79, "12KW", 0, "E1004");
//
//        myDataBaseHelper.addPile(72, "12KW", 0, "E201");
//        myDataBaseHelper.addPile(72, "12KW", 0, "E202");
//        myDataBaseHelper.addPile(72, "12KW", 0, "E203");
//        myDataBaseHelper.addPile(72, "12KW", 0, "E204");
//
//        myDataBaseHelper.addPile(77, "12KW", 0, "E301");
//        myDataBaseHelper.addPile(77, "12KW", 0, "E302");
//        myDataBaseHelper.addPile(77, "12KW", 0, "E303");
//        myDataBaseHelper.addPile(77, "12KW", 0, "E304");
//
//        myDataBaseHelper.addPile(86, "12KW", 0, "E401");
//        myDataBaseHelper.addPile(86, "12KW", 0, "E402");
//        myDataBaseHelper.addPile(86, "12KW", 0, "E403");
//        myDataBaseHelper.addPile(86, "12KW", 0, "E404");
//
//        myDataBaseHelper.addPile(15, "12KW", 0, "E501");
//        myDataBaseHelper.addPile(15, "12KW", 0, "E502");
//        myDataBaseHelper.addPile(15, "12KW", 0, "E503");
//        myDataBaseHelper.addPile(15, "12KW", 0, "E504");
//
//        myDataBaseHelper.addPile(7, "12KW", 0, "E601");
//        myDataBaseHelper.addPile(7, "12KW", 0, "E602");
//        myDataBaseHelper.addPile(7, "12KW", 0, "E604");
//
//        myDataBaseHelper.addPile(6, "12KW", 0, "E701");
//        myDataBaseHelper.addPile(6, "12KW", 0, "E702");
//        myDataBaseHelper.addPile(6, "12KW", 0, "E703");
//        myDataBaseHelper.addPile(6, "12KW", 0, "E704");
//
//        myDataBaseHelper.addPile(4161, "12KW", 0, "E801");
//        myDataBaseHelper.addPile(4161, "12KW", 0, "E802");
//        myDataBaseHelper.addPile(4161, "12KW", 0, "E803");
//        myDataBaseHelper.addPile(4161, "12KW", 0, "E804");
//
//        myDataBaseHelper.addPile(61, "12KW", 0, "E901");
//        myDataBaseHelper.addPile(61, "12KW", 0, "E902");
//        myDataBaseHelper.addPile(61, "12KW", 0, "E903");
//        myDataBaseHelper.addPile(61, "12KW", 0, "E904");
//
//        myDataBaseHelper.addPile(95, "12KW", 0, "E101");
//        myDataBaseHelper.addPile(95, "12KW", 0, "E102");
//        myDataBaseHelper.addPile(95, "12KW", 0, "E103");
//        myDataBaseHelper.addPile(95, "12KW", 0, "E104");
//
//        myDataBaseHelper.addPile(48, "12KW", 0, "E11");
//        myDataBaseHelper.addPile(48, "12KW", 0, "E11");
//        myDataBaseHelper.addPile(48, "12KW", 0, "E11");
//        myDataBaseHelper.addPile(48, "12KW", 0, "E11");
//
//        myDataBaseHelper.addPile(45, "12KW", 0, "E12");
//        myDataBaseHelper.addPile(45, "12KW", 0, "E12");
//
//        myDataBaseHelper.addPile(32, "12KW", 0, "E13");
//        myDataBaseHelper.addPile(32, "12KW", 0, "E13");
//        myDataBaseHelper.addPile(32, "12KW", 0, "E13");
//        myDataBaseHelper.addPile(32, "12KW", 0, "E13");
//
//        myDataBaseHelper.addPile(96, "12KW", 0, "F1001");
//        myDataBaseHelper.addPile(96, "12KW", 0, "F1002");
//        myDataBaseHelper.addPile(96, "12KW", 0, "F1003");
//        myDataBaseHelper.addPile(96, "12KW", 0, "F1004");
//
//        myDataBaseHelper.addPile(84, "12KW", 0, "F201");
//        myDataBaseHelper.addPile(84, "12KW", 0, "F202");
//        myDataBaseHelper.addPile(84, "12KW", 0, "F203");
//        myDataBaseHelper.addPile(84, "12KW", 0, "F204");
//
//        myDataBaseHelper.addPile(22, "12KW", 0, "F301");
//        myDataBaseHelper.addPile(22, "12KW", 0, "F303");
//        myDataBaseHelper.addPile(22, "12KW", 0, "F302");
//        myDataBaseHelper.addPile(22, "12KW", 0, "F304");
//
//        myDataBaseHelper.addPile(58, "12KW", 0, "F401");
//        myDataBaseHelper.addPile(58, "12KW", 0, "F402");
//        myDataBaseHelper.addPile(58, "12KW", 0, "F403");
//        myDataBaseHelper.addPile(58, "12KW", 0, "F404");
//
//        myDataBaseHelper.addPile(20, "12KW", 0, "F501");
//        myDataBaseHelper.addPile(20, "12KW", 0, "F502");
//        myDataBaseHelper.addPile(20, "12KW", 0, "F503");
//        myDataBaseHelper.addPile(20, "12KW", 0, "F504");
//
//        myDataBaseHelper.addPile(37, "12KW", 0, "F601");
//        myDataBaseHelper.addPile(37, "12KW", 0, "F602");
//        myDataBaseHelper.addPile(37, "12KW", 0, "F603");
//        myDataBaseHelper.addPile(37, "12KW", 0, "F604");
//
//        myDataBaseHelper.addPile(4117, "12KW", 0, "F701");
//        myDataBaseHelper.addPile(4117, "12KW", 0, "F702");
//        myDataBaseHelper.addPile(4117, "12KW", 0, "F703");
//
//        myDataBaseHelper.addPile(17, "12KW", 0, "F801");
//        myDataBaseHelper.addPile(17, "12KW", 0, "F802");
//        myDataBaseHelper.addPile(17, "12KW", 0, "F803");
//        myDataBaseHelper.addPile(17, "12KW", 0, "F804");
//
//        myDataBaseHelper.addPile(5, "12KW", 0, "F901");
//        myDataBaseHelper.addPile(5, "12KW", 0, "F902");
//        myDataBaseHelper.addPile(5, "12KW", 0, "F903");
//        myDataBaseHelper.addPile(5, "12KW", 0, "F904");
//
//        myDataBaseHelper.addPile(50, "12KW", 0, "F101");
//        myDataBaseHelper.addPile(50, "12KW", 0, "F102");
//        myDataBaseHelper.addPile(50, "12KW", 0, "F103");
//        myDataBaseHelper.addPile(50, "12KW", 0, "F104");
//
//        myDataBaseHelper.addPile(31, "12KW", 0, "F1101");
//        myDataBaseHelper.addPile(31, "12KW", 0, "F1102");
//        myDataBaseHelper.addPile(31, "12KW", 0, "F1103");
//        myDataBaseHelper.addPile(31, "12KW", 0, "F1104");
//
//        myDataBaseHelper.addPile(21, "12KW", 0, "F1201");
//        myDataBaseHelper.addPile(21, "12KW", 0, "F1202");
//        myDataBaseHelper.addPile(21, "12KW", 0, "F1203");
//        myDataBaseHelper.addPile(21, "12KW", 0, "F1204");
//
//        myDataBaseHelper.addPile(18, "12KW", 0, "F1301");
//        myDataBaseHelper.addPile(18, "12KW", 0, "F1302");
//
//        myDataBaseHelper.addPile(34, "12KW", 0, "G1001");
//        myDataBaseHelper.addPile(34, "12KW", 0, "G1002");
//        myDataBaseHelper.addPile(34, "12KW", 0, "G1003");
//        myDataBaseHelper.addPile(34, "12KW", 0, "G1004");
//
//        myDataBaseHelper.addPile(69, "12KW", 0, "G201");
//        myDataBaseHelper.addPile(69, "12KW", 0, "G202");
//        myDataBaseHelper.addPile(69, "12KW", 0, "G203");
//        myDataBaseHelper.addPile(69, "12KW", 0, "G204");
//
//        myDataBaseHelper.addPile(82, "12KW", 0, "G301");
//        myDataBaseHelper.addPile(82, "12KW", 0, "G302");
//        myDataBaseHelper.addPile(82, "12KW", 0, "G303");
//        myDataBaseHelper.addPile(82, "12KW", 0, "G304");
//
//        myDataBaseHelper.addPile(28, "12KW", 0, "G401");
//        myDataBaseHelper.addPile(28, "12KW", 0, "G402");
//        myDataBaseHelper.addPile(28, "12KW", 0, "G403");
//        myDataBaseHelper.addPile(28, "12KW", 0, "G404");
//
//        myDataBaseHelper.addPile(27, "12KW", 0, "G501");
//        myDataBaseHelper.addPile(27, "12KW", 0, "G502");
//        myDataBaseHelper.addPile(27, "12KW", 0, "G503");
//        myDataBaseHelper.addPile(27, "12KW", 0, "G504");
//
//        myDataBaseHelper.addPile(63, "12KW", 0, "G601");
//        myDataBaseHelper.addPile(63, "12KW", 0, "G602");
//        myDataBaseHelper.addPile(63, "12KW", 0, "G603");
//        myDataBaseHelper.addPile(63, "12KW", 0, "G604");
//
//        myDataBaseHelper.addPile(73, "12KW", 0, "G701");
//        myDataBaseHelper.addPile(73, "12KW", 0, "G702");
//        myDataBaseHelper.addPile(73, "12KW", 0, "G703");
//        myDataBaseHelper.addPile(73, "12KW", 0, "G704");
//
//        myDataBaseHelper.addPile(93, "12KW", 0, "G801");
//        myDataBaseHelper.addPile(93, "12KW", 0, "G802");
//        myDataBaseHelper.addPile(93, "12KW", 0, "G803");
//        myDataBaseHelper.addPile(93, "12KW", 0, "G804");
//
//        myDataBaseHelper.addPile(67, "12KW", 0, "G901");
//        myDataBaseHelper.addPile(67, "12KW", 0, "G902");
//        myDataBaseHelper.addPile(67, "12KW", 0, "G903");
//        myDataBaseHelper.addPile(67, "12KW", 0, "G904");
//
//        myDataBaseHelper.addPile(65, "12KW", 0, "G101");
//        myDataBaseHelper.addPile(65, "12KW", 0, "G102");
//
//        myDataBaseHelper.addPile(91, "12KW", 0, "G1101");
//        myDataBaseHelper.addPile(91, "12KW", 0, "G1102");
//        myDataBaseHelper.addPile(91, "12KW", 0, "G1103");
//        myDataBaseHelper.addPile(91, "12KW", 0, "G1104");
//
//        myDataBaseHelper.addPile(33, "12KW", 0, "G1201");
//        myDataBaseHelper.addPile(33, "12KW", 0, "G1202");
//        myDataBaseHelper.addPile(33, "12KW", 0, "G1203");
//        myDataBaseHelper.addPile(33, "12KW", 0, "G1204");
//
//        myDataBaseHelper.addPile(4116, "12KW", 0, "G1301");
//        myDataBaseHelper.addPile(4116, "12KW", 0, "G1302");
//        myDataBaseHelper.addPile(4116, "12KW", 0, "G1303");
//
//
//        myDataBaseHelper.addPile(16, "12KW", 0, "H1001");
//        myDataBaseHelper.addPile(16, "12KW", 0, "H1002");
//        myDataBaseHelper.addPile(16, "12KW", 0, "H1003");
//
//        myDataBaseHelper.addPile(4129, "12KW", 0, "H201");
//        myDataBaseHelper.addPile(4129, "12KW", 0, "H202");
//        myDataBaseHelper.addPile(4129, "12KW", 0, "H203");
//        myDataBaseHelper.addPile(4129, "12KW", 0, "H204");
//
//        myDataBaseHelper.addPile(29, "12KW", 0, "H301");
//        myDataBaseHelper.addPile(29, "12KW", 0, "H302");
//        myDataBaseHelper.addPile(29, "12KW", 0, "H303");
//        myDataBaseHelper.addPile(29, "12KW", 0, "H304");
//
//        myDataBaseHelper.addPile(4180, "12KW", 0, "H401");
//        myDataBaseHelper.addPile(4180, "12KW", 0, "H402");
//        myDataBaseHelper.addPile(4180, "12KW", 0, "H403");
//        myDataBaseHelper.addPile(4180, "12KW", 0, "H404");
//
//        myDataBaseHelper.addPile(80, "12KW", 0, "H501");
//        myDataBaseHelper.addPile(80, "12KW", 0, "H502");
//        myDataBaseHelper.addPile(80, "12KW", 0, "H503");
//        myDataBaseHelper.addPile(80, "12KW", 0, "H504");
//
//        myDataBaseHelper.addPile(49, "12KW", 0, "H601");
//        myDataBaseHelper.addPile(49, "12KW", 0, "H602");
//        myDataBaseHelper.addPile(49, "12KW", 0, "H603");
//        myDataBaseHelper.addPile(49, "12KW", 0, "H604");
//
//        myDataBaseHelper.addPile(4155, "12KW", 0, "H701");
//        myDataBaseHelper.addPile(4155, "12KW", 0, "H702");
//        myDataBaseHelper.addPile(4155, "12KW", 0, "H703");
//        myDataBaseHelper.addPile(4155, "12KW", 0, "H704");
//
//        myDataBaseHelper.addPile(55, "12KW", 0, "H801");
//        myDataBaseHelper.addPile(55, "12KW", 0, "H802");
//        myDataBaseHelper.addPile(55, "12KW", 0, "H803");
//        myDataBaseHelper.addPile(55, "12KW", 0, "H804");
//
//        myDataBaseHelper.addPile(71, "12KW", 0, "H901");
//        myDataBaseHelper.addPile(71, "12KW", 0, "H902");
//        myDataBaseHelper.addPile(71, "12KW", 0, "H903");
//        myDataBaseHelper.addPile(71, "12KW", 0, "H904");
//
//        myDataBaseHelper.addPile(30, "12KW", 0, "H101");
//        myDataBaseHelper.addPile(30, "12KW", 0, "H102");
//        myDataBaseHelper.addPile(30, "12KW", 0, "H103");
//        myDataBaseHelper.addPile(30, "12KW", 0, "H104");
//
//        myDataBaseHelper.addPile(4154, "12KW", 0, "H1101");
//        myDataBaseHelper.addPile(4154, "12KW", 0, "H1102");
//        myDataBaseHelper.addPile(4154, "12KW", 0, "H1103");
//        myDataBaseHelper.addPile(4154, "12KW", 0, "H1104");
//
//        myDataBaseHelper.addPile(54, "12KW", 0, "H1201");
//        myDataBaseHelper.addPile(54, "12KW", 0, "H1202");
//        myDataBaseHelper.addPile(54, "12KW", 0, "H1203");
//        myDataBaseHelper.addPile(54, "12KW", 0, "H1204");
//
//        myDataBaseHelper.addPile(57, "12KW", 0, "H1301");
//        myDataBaseHelper.addPile(57, "12KW", 0, "H1302");
//        myDataBaseHelper.addPile(57, "12KW", 0, "H1303");
//        myDataBaseHelper.addPile(57, "12KW", 0, "H1304");
//
//        myDataBaseHelper.addPile(92, "12KW", 0, "I1001");
//        myDataBaseHelper.addPile(92, "12KW", 0, "I1002");
//        myDataBaseHelper.addPile(92, "12KW", 0, "I1003");
//        myDataBaseHelper.addPile(92, "12KW", 0, "I1004");
//
//        myDataBaseHelper.addPile(4123, "12KW", 0, "I201");
//        myDataBaseHelper.addPile(4123, "12KW", 0, "I202");
//        myDataBaseHelper.addPile(4123, "12KW", 0, "I203");
//        myDataBaseHelper.addPile(4123, "12KW", 0, "I204");
//
//        myDataBaseHelper.addPile(23, "12KW", 0, "I301");
//        myDataBaseHelper.addPile(23, "12KW", 0, "I302");
//        myDataBaseHelper.addPile(23, "12KW", 0, "I303");
//        myDataBaseHelper.addPile(23, "12KW", 0, "I304");
//
//        myDataBaseHelper.addPile(4162, "12KW", 0, "I401");
//        myDataBaseHelper.addPile(4162, "12KW", 0, "I402");
//        myDataBaseHelper.addPile(4162, "12KW", 0, "I403");
//        myDataBaseHelper.addPile(4162, "12KW", 0, "I404");
//
//        myDataBaseHelper.addPile(62, "12KW", 0, "I501");
//        myDataBaseHelper.addPile(62, "12KW", 0, "I502");
//        myDataBaseHelper.addPile(62, "12KW", 0, "I503");
//        myDataBaseHelper.addPile(62, "12KW", 0, "I504");
//
//        myDataBaseHelper.addPile(4199, "12KW", 0, "I601");
//        myDataBaseHelper.addPile(4199, "12KW", 0, "I602");
//        myDataBaseHelper.addPile(4199, "12KW", 0, "I603");
//        myDataBaseHelper.addPile(4199, "12KW", 0, "I604");
//
//        myDataBaseHelper.addPile(99, "12KW", 0, "I701");
//        myDataBaseHelper.addPile(99, "12KW", 0, "I702");
//        myDataBaseHelper.addPile(99, "12KW", 0, "I703");
//        myDataBaseHelper.addPile(99, "12KW", 0, "I704");
//
//        myDataBaseHelper.addPile(70, "12KW", 0, "I801");
//        myDataBaseHelper.addPile(70, "12KW", 0, "I802");
//        myDataBaseHelper.addPile(70, "12KW", 0, "I803");
//        myDataBaseHelper.addPile(70, "12KW", 0, "I804");
//
//        myDataBaseHelper.addPile(4153, "12KW", 0, "I901");
//        myDataBaseHelper.addPile(4153, "12KW", 0, "I902");
//        myDataBaseHelper.addPile(4153, "12KW", 0, "I903");
//        myDataBaseHelper.addPile(4153, "12KW", 0, "I904");
//
//        myDataBaseHelper.addPile(53, "12KW", 0, "I101");
//        myDataBaseHelper.addPile(53, "12KW", 0, "I102");
//        myDataBaseHelper.addPile(53, "12KW", 0, "I103");
//        myDataBaseHelper.addPile(53, "12KW", 0, "I104");
//
//        myDataBaseHelper.addPile(64, "12KW", 0, "I1101");
//        myDataBaseHelper.addPile(64, "12KW", 0, "I1102");
//        myDataBaseHelper.addPile(64, "12KW", 0, "I1103");
//        myDataBaseHelper.addPile(64, "12KW", 0, "I1104");
//
//        myDataBaseHelper.addPile(41, "12KW", 0, "I1201");
//        myDataBaseHelper.addPile(41, "12KW", 0, "I1202");
//        myDataBaseHelper.addPile(41, "12KW", 0, "I1203");
//        myDataBaseHelper.addPile(41, "12KW", 0, "I1204");
//
//        myDataBaseHelper.addPile(4160, "12KW", 0, "I1301");
//        myDataBaseHelper.addPile(4160, "12KW", 0, "I1302");
//        myDataBaseHelper.addPile(4160, "12KW", 0, "I1303");
//        myDataBaseHelper.addPile(4160, "12KW", 0, "I1304");
//
//        myDataBaseHelper.addPile(60, "12KW", 0, "J1001");
//        myDataBaseHelper.addPile(60, "12KW", 0, "J1002");
//        myDataBaseHelper.addPile(60, "12KW", 0, "J1003");
//        myDataBaseHelper.addPile(60, "12KW", 0, "J1004");
//
//        myDataBaseHelper.addPile(4200, "12KW", 0, "J201");
//        myDataBaseHelper.addPile(4200, "12KW", 0, "J202");
//        myDataBaseHelper.addPile(4200, "12KW", 0, "J203");
//        myDataBaseHelper.addPile(4200, "12KW", 0, "J204");
//
//        myDataBaseHelper.addPile(100, "12KW", 0, "J301");
//        myDataBaseHelper.addPile(100, "12KW", 0, "J302");
//        myDataBaseHelper.addPile(100, "12KW", 0, "J303");
//        myDataBaseHelper.addPile(100, "12KW", 0, "J304");
//
//        myDataBaseHelper.addPile(4111, "12KW", 0, "J401");
//        myDataBaseHelper.addPile(4111, "12KW", 0, "J402");
//        myDataBaseHelper.addPile(4111, "12KW", 0, "J403");
//        myDataBaseHelper.addPile(4111, "12KW", 0, "J404");
//
//        myDataBaseHelper.addPile(11, "12KW", 0, "J501");
//        myDataBaseHelper.addPile(11, "12KW", 0, "J502");
//        myDataBaseHelper.addPile(11, "12KW", 0, "J503");
//        myDataBaseHelper.addPile(11, "12KW", 0, "J504");
//
//        myDataBaseHelper.addPile(4187, "12KW", 0, "J601");
//        myDataBaseHelper.addPile(4187, "12KW", 0, "J602");
//        myDataBaseHelper.addPile(4187, "12KW", 0, "J603");
//        myDataBaseHelper.addPile(4187, "12KW", 0, "J604");
//
//        myDataBaseHelper.addPile(87, "12KW", 0, "J701");
//        myDataBaseHelper.addPile(87, "12KW", 0, "J702");
//        myDataBaseHelper.addPile(87, "12KW", 0, "J703");
//        myDataBaseHelper.addPile(87, "12KW", 0, "J704");

//
//        List<Station> stationList = myDataBaseHelper.getAllStationData();
//        for (Station station : stationList) {
//            Random random = new Random();//创建Random对象
//            int randomInt = random.nextInt(5) + 1;// 生成1到5的随机整数
//            for (int i = 0; i < randomInt; i++) {
//                // 给每个充电站随机生成1~5个充电桩
//                myDataBaseHelper.addPile(station.getId(), i + 12 + "KW", 0, "A1001" + i);
//            }
//        }

//        Bundle bundle = this.getIntent().getExtras();
//        int id = bundle.getInt("stationId");
//        pileList = myDataBaseHelper.getPileByStationId(id);
////        Log.d("pileList", "initPile: "+pileList);
    }


}
