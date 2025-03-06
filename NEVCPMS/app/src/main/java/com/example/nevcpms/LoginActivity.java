package com.example.nevcpms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nevcpms.bean.User;
import com.example.nevcpms.util.CommonData;

import java.util.ArrayList;

import cn.jiguang.verifysdk.api.AuthPageEventListener;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.JVerifyUIClickCallback;
import cn.jiguang.verifysdk.api.JVerifyUIConfig;
import cn.jiguang.verifysdk.api.VerifyListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText pN;//电话号码
    private EditText pW;//密码

    private Button login;
    private Button go_to_Register;
    private Button go_to_smsLogin;
    private Button phoneVerify;

    private MyDataBaseHelper myDataBaseHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        initView();

        myDataBaseHelper=new MyDataBaseHelper(this,"LBS.db",null,1);

        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] str = new String[2];
            str[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                str[1] = Manifest.permission.READ_PHONE_NUMBERS;
            } else {
                str[1] = Manifest.permission.READ_PHONE_STATE;
            }
            requestPermissions(str, 100);
        }
    }

    private void initView() {
        //初始化控件
        pN=findViewById(R.id.phone_number_login);
        pW=findViewById(R.id.password_login);
        login=findViewById(R.id.login);
        go_to_Register=findViewById(R.id.go_to_register);
        go_to_smsLogin=findViewById(R.id.go_to_sms_login);
        phoneVerify=findViewById(R.id.phone_verify);

        //设置点击事件监听
        login.setOnClickListener(this);
        go_to_Register.setOnClickListener(this);
        go_to_smsLogin.setOnClickListener(this);
        phoneVerify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                result = checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS);
            } else {
                result = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            }
            if (result != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "[2025],msg = 当前缺少权限", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        switch (v.getId()){
            case R.id.go_to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                //finish();
                break;
            case R.id.go_to_sms_login:
//                startActivity(new Intent(this, SMSVerify.class));
                startActivity(new Intent(this, sms_login.class));
                //finish();
                break;
            case R.id.login:
                String phoneNumber=pN.getText().toString().trim();
                String password=pW.getText().toString().trim();
                if(!TextUtils.isEmpty(phoneNumber)&&!TextUtils.isEmpty(password)){
                    ArrayList<User> userArrayList=myDataBaseHelper.getAllUserData();
                    boolean match=false;

                    for (int i = 0; i < userArrayList.size(); i++) {
                        User user = userArrayList.get(i);
                        if (phoneNumber.equals(user.getPhone_number()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }

                    if (match) {
                        Toast.makeText(this, "登录成功,"+phoneNumber+",欢迎您！", Toast.LENGTH_SHORT).show();
                        //本次登录的账号信息保存到sharedpreference中
                        //用于下次的自动登录
                        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        String _phone=phoneNumber;
                        String _password=password;


                        editor.putString("phone", _phone);
                        editor.putString("password", _password);
                        editor.commit();

                        //把账号信息传到MainActivity
                        Intent intent = new Intent(this,MainActivity.class);
                        intent.putExtra("username", phoneNumber);

                        CommonData.getInstance().setPhoneNum(phoneNumber);

                        startActivity(intent);

                        //销毁此Activity
                        finish();

                    } else {
                        Toast.makeText(getBaseContext(), "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getBaseContext(), "请完整填写用户名和密码", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.phone_verify:
                loginAuth(false);
                break;

        }
    }

    private void loginAuth(boolean isDialogMode) {
        boolean verifyEnable = JVerificationInterface.checkVerifyEnable(this);
        if (!verifyEnable) {
            Toast.makeText(this, "[2025],msg = 当前网络环境不支持认证", Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();

        setUIConfig(isDialogMode);
        //autoFinish 可以设置是否在点击登录的时候自动结束授权界面
        JVerificationInterface.loginAuth(this, true, new VerifyListener() {
            @Override
            public void onResult(final int code, final String content, final String operator) {
                Log.d("LoginActivity", "[" + code + "]message=" + content + ", operator=" + operator);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "[" + code + "]message=" + content + ", operator=" + operator, Toast.LENGTH_SHORT).show();
                        dismissLoadingDialog();
                    }
                });
            }
        }, new AuthPageEventListener() {
            @Override
            public void onEvent(int cmd, String msg) {
                Log.d("LoginActivity", "[onEvent]. [" + cmd + "]message=" + msg);
            }
        });

    }

    private void setUIConfig(boolean isDialogMode) {
        JVerifyUIConfig portrait = getPortraitConfig(isDialogMode);
        JVerifyUIConfig landscape = getLandscapeConfig(isDialogMode);

        //支持授权页设置横竖屏两套config，在授权页中触发横竖屏切换时，sdk自动选择对应的config加载。
        JVerificationInterface.setCustomUIWithConfig(portrait, landscape);
    }

    private JVerifyUIConfig getPortraitConfig(boolean isDialogMode) {
        JVerifyUIConfig.Builder configBuilder = new JVerifyUIConfig.Builder();

        //自定义按钮示例1
        ImageView mBtn = new ImageView(this);
        mBtn.setImageResource(R.drawable.ic_mine);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        //自定义按钮示例2
        ImageView mBtn2 = new ImageView(this);
        mBtn2.setImageResource(R.drawable.ic_mine);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        ImageView loadingView = new ImageView(getApplicationContext());
        loadingView.setImageResource(R.drawable.umcsdk_load_dot_white);
        RelativeLayout.LayoutParams loadingParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        loadingView.setLayoutParams(loadingParam);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.umcsdk_anim_loading);

        if (isDialogMode) {
            //窗口竖屏
            mLayoutParams1.setMargins(dp2Pix(this, 100), dp2Pix(this, 250.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 250.0f), dp2Pix(this, 100.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);

            //自定义返回按钮示例
            ImageButton sampleReturnBtn = new ImageButton(getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);

            configBuilder.setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                    .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(130)
                    .setSloganOffsetY(160)
                    .setLogBtnOffsetY(184)
                    .setLogBtnWidth(200)
                    .setNumberSize(18)
                    .setPrivacyState(false)
                    .setNavTransparent(false)
                    .setPrivacyOffsetY(5)
                    .setNeedCloseAnim(true)
                    .setNeedStartAnim(true)
                    .overridePendingTransition(R.anim.activity_slide_enter_bottom, R.anim.activity_slide_exit_bottom)
                    .setDialogTheme(300, 390, 0, 0, false)
                    .setLoadingView(loadingView, animation)
                    .enableHintToast(true, Toast.makeText(getApplicationContext(), "checkbox未选中，自定义提示", Toast.LENGTH_SHORT))
                    .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addCustomView(sampleReturnBtn, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            dismissLoadingDialog();
                        }
                    });
        } else {
            //全屏竖屏
            mLayoutParams1.setMargins(dp2Pix(this, 100), dp2Pix(this, 400.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 400.0f), dp2Pix(this, 100.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);

            //导航栏按钮示例
            Button navBtn = new Button(this);
            navBtn.setText("导航栏按钮");
            navBtn.setTextColor(0xff3a404c);
            navBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            RelativeLayout.LayoutParams navBtnParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            navBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            navBtn.setLayoutParams(navBtnParam);

            ImageButton sampleReturnBtn = new ImageButton(getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.ic_mine);

            configBuilder.setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setPrivacyNavReturnBtnPath("qq")
                    .setNeedCloseAnim(true)
                    .setNeedStartAnim(true)
                    .overridePendingTransition(R.anim.activity_slide_enter_bottom, R.anim.activity_slide_exit_bottom)
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                    .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(50)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(190)
                    .setSloganOffsetY(220)
                    .setLogBtnOffsetY(254)
                    .setNumberSize(18)
                    .setPrivacyState(false)
                    .setNavTransparent(true)
                    .setStatusBarHidden(false)
                    .setStatusBarTransparent(true)
                    .setStatusBarDarkMode(true)
                    .setPrivacyOffsetY(35)
                    .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addNavControlView(navBtn, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "导航栏自定义按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return configBuilder.build();
    }

    private JVerifyUIConfig getLandscapeConfig(boolean isDialogMode) {
        JVerifyUIConfig.Builder configBuilder = new JVerifyUIConfig.Builder();

        //自定义按钮示例1
        ImageView mBtn = new ImageView(this);
        mBtn.setImageResource(R.drawable.ic_mine);
        RelativeLayout.LayoutParams mLayoutParams1 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        //自定义按钮示例2
        ImageView mBtn2 = new ImageView(this);
        mBtn2.setImageResource(R.drawable.ic_mine);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(dp2Pix(getApplicationContext(), 40), dp2Pix(getApplicationContext(), 40));
        mLayoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

        if (isDialogMode) {
            //窗口横屏
            mLayoutParams1.setMargins(dp2Pix(this, 200), dp2Pix(this, 235.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 235.0f), dp2Pix(this, 200.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);

            //自定义返回按钮示例
            ImageButton sampleReturnBtn = new ImageButton(getApplicationContext());
            sampleReturnBtn.setImageResource(R.drawable.umcsdk_return_bg);
            RelativeLayout.LayoutParams returnLP = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            returnLP.setMargins(10, 10, 0, 0);
            sampleReturnBtn.setLayoutParams(returnLP);

            configBuilder.setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                    .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(25)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(120)
                    .setSloganOffsetY(155)
                    .setLogBtnOffsetY(180)
                    .setPrivacyOffsetY(10)
                    .setDialogTheme(500, 350, 0, 0, false)
                    .enableHintToast(true, null)
                    .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    }).addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                @Override
                public void onClicked(Context context, View view) {
                    Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                }
            })
                    .addCustomView(sampleReturnBtn, true, null);
        } else {
            //全屏横屏
            mLayoutParams1.setMargins(dp2Pix(this, 200), dp2Pix(this, 100.0f), 0, 0);
            mBtn.setLayoutParams(mLayoutParams1);

            mLayoutParams2.setMargins(0, dp2Pix(this, 100.0f), dp2Pix(this, 200.0f), 0);
            mBtn2.setLayoutParams(mLayoutParams2);

            //导航栏按钮示例
            Button navBtn = new Button(this);
            navBtn.setText("导航栏按钮");
            navBtn.setTextColor(0xff3a404c);
            navBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            RelativeLayout.LayoutParams navBtnParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            navBtnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            navBtn.setLayoutParams(navBtnParam);

            configBuilder
                    .setAuthBGImgPath("main_bg")
                    .setNavColor(0xff0086d0)
                    .setNavText("登录")
                    .setNavTextColor(0xffffffff)
                    .setNavReturnImgPath("umcsdk_return_bg")
                    .setLogoWidth(70)
                    .setLogoHeight(70)
                    .setLogoHidden(false)
                    .setNumberColor(0xff333333)
                    .setLogBtnText("本机号码一键登录")
                    .setLogBtnTextColor(0xffffffff)
                    .setLogBtnImgPath("umcsdk_login_btn_bg")
                    //私条款url为网络网页或本地网页地址(sd卡的地址，需自行申请sd卡读权限)，
                    // 如：assets下路径："file:///android_asset/t.html"，
                    // sd卡下路径："file:"+Environment.getExternalStorageDirectory().getAbsolutePath() +"/t.html"
                    .setAppPrivacyOne("应用自定义服务条款一", "https://www.jiguang.cn/about")
                    .setAppPrivacyTwo("应用自定义服务条款二", "https://www.jiguang.cn/about")
                    .setAppPrivacyColor(0xff666666, 0xff0085d0)
                    .setUncheckedImgPath("umcsdk_uncheck_image")
                    .setCheckedImgPath("umcsdk_check_image")
                    .setSloganTextColor(0xff999999)
                    .setLogoOffsetY(30)
                    .setLogoImgPath("logo_cm")
                    .setNumFieldOffsetY(150)
                    .setSloganOffsetY(185)
                    .setLogBtnOffsetY(210)
                    .setPrivacyOffsetY(10)
                    .setPrivacyState(false)
                    .setNavTransparent(true)
                    .setStatusBarTransparent(true)
                    .setStatusBarDarkMode(true)
                    .addCustomView(mBtn, true, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "自定义的按钮1，会finish授权页", Toast.LENGTH_SHORT).show();
                        }
                    }).addCustomView(mBtn2, false, new JVerifyUIClickCallback() {
                @Override
                public void onClicked(Context context, View view) {
                    Toast.makeText(context, "自定义的按钮2，不会finish授权页", Toast.LENGTH_SHORT).show();
                }
            })
                    .addNavControlView(navBtn, new JVerifyUIClickCallback() {
                        @Override
                        public void onClicked(Context context, View view) {
                            Toast.makeText(context, "导航栏自定义按钮", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return configBuilder.build();
    }


    public void showLoadingDialog() {
        dismissLoadingDialog();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        alertDialog.show();
        alertDialog.setContentView(R.layout.loading_alert);
        alertDialog.setCanceledOnTouchOutside(false);
    }

    public void dismissLoadingDialog() {
        if (null != alertDialog && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    private int dp2Pix(Context context, float dp) {
        try {
            float density = context.getResources().getDisplayMetrics().density;
            return (int) (dp * density + 0.5F);
        } catch (Exception e) {
            return (int) dp;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
    }
}