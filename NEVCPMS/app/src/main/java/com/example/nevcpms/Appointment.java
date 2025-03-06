package com.example.nevcpms;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nevcpms.bean.Pile;
import com.example.nevcpms.util.CommonData;

import java.util.Calendar;

public class Appointment extends AppCompatActivity implements View.OnClickListener {

    Button btnStartTime;
    Button btnEndTime;
    TextView tvStarTime;
    TextView tvEndTime;
    TextView appointment;

    TextView appointment_time;
    //    TextView isUsed_cancel;
    Button btn_sure;

    String starTime;
    String endTime;

    private int startHour = 0, startMinute = 0;
    private int endHour = 0, endMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pile_appointment);
        btnStartTime = findViewById(R.id.btn_startTime);
        tvStarTime = findViewById(R.id.start_time);

        btnEndTime = findViewById(R.id.btn_endTime);
        tvEndTime = findViewById(R.id.end_time);
        btn_sure = findViewById(R.id.btn_sure);

        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        btn_sure.setOnClickListener(this);

        appointment_time = findViewById(R.id.appointment_time);
        appointment = findViewById(R.id.appointment);
//        isUsed_cancel = findViewById(R.id.isUsed_cancel);

//        appointment_time.setText("您预约的时间是:" + tvStarTime.toString() + "-" + tvEndTime.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startTime:
                showTimePicker(true);
                break;
            case R.id.btn_endTime:
                showTimePicker(false);
                break;
            case R.id.btn_sure:
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(this, "LBS.db", null, 1);

                Bundle bundle = this.getIntent().getExtras();
                String phone = CommonData.getInstance().getPhoneNum();
                int pileId = bundle.getInt("pileId");
                myDataBaseHelper.addAppoint(phone, pileId, starTime, endTime, 1);
                myDataBaseHelper.updatePile(pileId, 1);

                finish();
        }
    }

    private void showTimePicker( boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isStartTime) {
                    startHour = hourOfDay;
                    startMinute = minute;
                    tvStarTime.setText(startHour + ":" + startMinute);

                    starTime = startHour + ":" + startMinute;
                    Log.d("starTime", "onTimeSet: " + starTime);
                } else {
                    endHour = hourOfDay;
                    endMinute = minute;
                    tvEndTime.setText(endHour + ":" + endMinute);
                    endTime = endHour + ":" + endMinute;
                    Log.d("endTime", "onTimeSet: " + endTime);
                }
//                updateSelectedTime();
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void updateSelectedTime() {
        if (validateTime()) {
            String selectedTime = String.format("选择的时段: %02d:%02d - %02d:%02d", startHour, startMinute, endHour, endMinute);
            Intent intent = new Intent(Appointment.this, MyAppointmentActivity.class);
            intent.putExtra("selectedTime", selectedTime);
            startActivity(intent);
        }
    }

    private boolean validateTime() {
        if (startHour > endHour || (startHour == endHour && startMinute >= endMinute)) {
            Toast.makeText(this, "结束时间必须晚于开始时间", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}


