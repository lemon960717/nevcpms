package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nevcpms.bean.User;
import com.mob.MobSDK;
import com.mob.tools.utils.SharePrefrenceHelper;

import java.util.ArrayList;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.UserInterruptException;

//语音短信验证码功能已实现
// 现在sms_login活动中试验，以免影响到注册模块的功能
//本模块决定弃用，改用更先进的一键验证本机手机号登录功能
//另创建activity
public class sms_login extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private boolean granted=false;

    private static final String TAG = "VerifyActivity";
    private static final String[] DEFAULT_COUNTRY = new String[]{"中国", "42", "86"};
    private static final int COUNTDOWN = 60;
    private static final String TEMP_CODE = "1319972";
    private static final String KEY_START_TIME = "start_time";
    private static final int REQUEST_CODE_VERIFY = 1001;
    private Toast toast;
    private Handler handler;
    private EventHandler eventHandler;
    private int currentSecond;
    private SharePrefrenceHelper helper;
    private String currentPrefix;
    private String currentId;

    private CheckBox checkBoxSMS;
    private boolean grantedSMS=false;
    private TextView privacySMS;
    private Button smsLogin;
    private Button getSMS;

    private EditText phone;
    private EditText checkCode;
    private MyDataBaseHelper myDataBaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_login_layout);

        MobSDK.submitPolicyGrantResult(true, null);
        myDataBaseHelper=new MyDataBaseHelper(this,"LBS.db",null,1);
        initView();

        //默认使用中国区号
        currentId = DEFAULT_COUNTRY[1];
        currentPrefix = DEFAULT_COUNTRY[2];
        helper = new SharePrefrenceHelper(this);
        helper.open("sms_sp");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VERIFY) {
            checkCode.setText("");
            phone.setText("");
            // 重置"获取验证码"按钮
            getSMS.setText("获取验证码");

            getSMS.setEnabled(true);


            //checkCode.setText("获取验证码");
            //checkCode.setEnabled(true);
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
    }

    private void initView() {
        checkBoxSMS=findViewById(R.id.checkedSMS);
        privacySMS=findViewById(R.id.privacy_sms_login);
        smsLogin=findViewById(R.id.sms_login);
        getSMS=findViewById(R.id.getSMS);
        phone=findViewById(R.id.phoneInSMS);
        checkCode=findViewById(R.id.checkCode);

        checkBoxSMS.setOnCheckedChangeListener(this);
        privacySMS.setOnClickListener(this);
        smsLogin.setOnClickListener(this);
        getSMS.setOnClickListener(this);



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (checkCode != null) {
                    if (currentSecond > 0) {
                        getSMS.setText("获取验证码"+ " (" + currentSecond + "s)");
                        getSMS.setEnabled(false);
                        currentSecond--;
                        handler.sendEmptyMessageDelayed(0, 1000);
                    } else {
                        getSMS.setText("获取验证码");
                        getSMS.setEnabled(true);
                    }
                }
            }
        };

        eventHandler = new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //提交验证成功，跳转成功页面，否则toast提示
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                //往数据库中添加一条用户数据
                                String phoneNum=phone.getText().toString();

                                ArrayList<User> userArrayList=myDataBaseHelper.getAllUserData();
                                for(int i=0;i<userArrayList.size();i++){
                                    //如果数据库中已有该手机号，则无事发生
                                    if(userArrayList.get(i).getPhone_number().equals(phoneNum)){
                                        break;
                                    }else { //否则，将该手机号加入数据库，密码为默认值6位0
                                        if(i==userArrayList.size()-1){
                                            myDataBaseHelper.add(phoneNum,"000000");
                                        }
                                    }
                                }


                                //往sharePreference中存储登录状态
                                sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                String _phone=phoneNum;
                                String _password="000000";
                                editor.putString("phone", _phone);
                                editor.putString("password", _password);
                                editor.commit();

                                //跳转登录
                                Intent intent=new Intent(sms_login.this,MainActivity.class);
                                intent.putExtra("username",phoneNum);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE || event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                currentSecond = COUNTDOWN;
                                handler.sendEmptyMessage(0);
                                helper.putLong(KEY_START_TIME, System.currentTimeMillis());
                            } else {
                                if (data != null && (data instanceof UserInterruptException)) {
                                    // 由于此处是开发者自己决定要中断发送的，因此什么都不用做
                                    return;
                                }
                            }
                        }
                    });
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getSMS:

                //获取验证码间隔时间小于1分钟，进行toast提示，在当前页面不会有这种情况，但是当点击验证码返回上级页面再进入会产生该情况
                long startTime = helper.getLong(KEY_START_TIME);
                //SMSSDK.getVoiceVerifyCode(currentPrefix, phone.getText().toString().trim());//为语音验证
                SMSSDK.getVerificationCode(currentPrefix, phone.getText().toString().trim(), null, null);//为短信验证
                break;

            case R.id.sms_login:
                SMSSDK.submitVerificationCode(currentPrefix, phone.getText().toString().trim(), checkCode.getText().toString());
//                String phoneNum=phone.getText().toString();
//                Intent intent=new Intent();
                break;

            case R.id.privacy_sms_login:
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder dialog = new AlertDialog.Builder(sms_login.this);
                //    设置Title的内容
                dialog.setTitle("                    用户隐私条款");
                //    设置Content来显示一个信息
                dialog.setMessage("\t\t"+"本软件尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，本软件会按照本隐私权政策的规定使用和披露您的个人信息。但本软件将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，本软件不会将这些信息对外披露或向第三方提供。本软件会不时更新本隐私权政策。您在同意本软件服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于本软件服务使用协议不可分割的一部分。\n" +
                        "\n" +
                        "1.适用范围\n" +
                        "\n" +
                        "在您使用本软件网络服务，本软件自动接收并记录的您的手机上的信息，包括但不限于您的健康数据、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；\n" +
                        "\n" +
                        "2.信息的使用\n" +
                        "\n" +
                        "在获得您的数据之后，本软件会将其上传至服务器，以生成您的排行榜数据，以便您能够更好地使用服务。\n" +
                        "\n" +
                        "3.信息披露\n" +
                        "\n" +
                        "本软件不会将您的信息披露给不受信任的第三方。\n" +
                        "\n" +
                        "根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；\n" +
                        "\n" +
                        "如您出现违反中国有关法律、法规或者相关规则的情况，需要向第三方披露；\n" +
                        "\n" +
                        "4.信息存储和交换本软件收集的有关您的信息和资料将保存在本软件及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或本软件收集信息和资料所在地的境外并在境外被访问、存储和展示。\n" +
                        "更多详情请阅读http://www.mob.com/about/policy" );

                //    设置一个PositiveButton
                dialog.setPositiveButton("已阅读", (dialog1, which) -> {
                });

                dialog.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (checkBoxSMS.getId()){
            case R.id.checkedSMS:
                if(isChecked){
                    granted=true;
                }
                //回传用户隐私授权结果至Mob平台
                MobSDK.submitPolicyGrantResult(granted, null);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}