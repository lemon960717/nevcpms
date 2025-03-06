package com.example.nevcpms.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nevcpms.LoginActivity;
import com.example.nevcpms.ModifyPassword;
import com.example.nevcpms.MyAppointmentActivity;
import com.example.nevcpms.MyCollectionActivity;
import com.example.nevcpms.R;
import com.example.nevcpms.bean.MyAppointment;
import com.example.nevcpms.util.CircleImageView;
import com.example.nevcpms.util.CommonData;

public class CFragment extends Fragment implements View.OnClickListener {

    public View view;

    private LinearLayout modifyPw;
    private LinearLayout callUs;
    private LinearLayout my_appointment;
    private TextView username;
    private LinearLayout logout;
    private LinearLayout collect;
    private LinearLayout howToUse;

    private String phone_str;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_c, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        //头像的初始化
        CircleImageView circleImageView = view.findViewById(R.id.header);
        circleImageView.setImageResource(R.drawable.user1);


        callUs = view.findViewById(R.id.call_us);
        my_appointment = view.findViewById(R.id.my_appointment);
        howToUse = view.findViewById(R.id.useInfo);
        username = view.findViewById(R.id.Username);
        modifyPw = view.findViewById(R.id.modifyPassword);
        logout = view.findViewById(R.id.logout);
        collect = view.findViewById(R.id.my_collection);

//        Intent intent = getActivity().getIntent();
//        phone_str = intent.getStringExtra("username");

        phone_str = CommonData.getInstance().getPhoneNum();
        username.setText(phone_str);

        callUs.setOnClickListener(this);
        my_appointment.setOnClickListener(this);
        modifyPw.setOnClickListener(this);
        logout.setOnClickListener(this);
        collect.setOnClickListener(this);
        howToUse.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modifyPassword:
                Intent intent2 = new Intent(getActivity(), ModifyPassword.class);
                intent2.putExtra("phoneNumber", phone_str);
                startActivity(intent2);
                break;
            case R.id.call_us:
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:13599351356"));
                startActivity(intent1);
                break;
            case R.id.my_appointment:
                Intent intent3 = new Intent(getContext(), MyAppointmentActivity.class);
                intent3.putExtra("phone_str", phone_str);
                startActivity(intent3);
                break;
            case R.id.useInfo:
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(getContext());
                //    设置Title的内容
                dialog1.setTitle("                    软件说明");
                //    设置Content来显示一个信息
                dialog1.setMessage("本软件功能简洁、简单，就不另做说明了~" +
                        "本按钮只是为了占位，使界面协调。" +
                        "有疑惑请拨打我的电话~");

                //    设置一个PositiveButton
                dialog1.setPositiveButton("已阅读", (dialog2, which) -> {
                });

                dialog1.show();
                break;

            //注销登录
            case R.id.logout:
                sharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent_logout = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent_logout);
                getActivity().finish();
                break;
            case R.id.my_collection:
                //传账号
                Intent intent = new Intent(getContext(), MyCollectionActivity.class);
                intent.putExtra("phone_str", phone_str);
                startActivity(intent);
                break;
        }
    }


}