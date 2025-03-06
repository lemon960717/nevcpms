package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nevcpms.util.Code;
import com.mob.MobSDK;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private String realCode;
    private MyDataBaseHelper myDataBaseHelper;

    private boolean granted=false;//是否同意用户协议

    private EditText phoneNumber;
    private EditText password_first;
    private EditText password_again;
    private EditText image_code;

    private Button register;
    private Button get_smsCode;

    private TextView registerPrivacy;

    private ImageView show_code;

    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();

        myDataBaseHelper=new MyDataBaseHelper(this,"LBS.db",null,1);

        show_code.setImageBitmap(Code.getInstance().createBitmap());
        realCode=Code.getInstance().getCode();
    }

    private void initView() {
        phoneNumber=findViewById(R.id.phone_number);
        password_first=findViewById(R.id.password);
        password_again=findViewById(R.id.password_again);

        image_code=findViewById(R.id.image_code);

        register=findViewById(R.id.register);

        show_code=findViewById(R.id.showCode);

        registerPrivacy=findViewById(R.id.privacy_register);

        checkBox=findViewById(R.id.checked);

        //设置点击事件
        show_code.setOnClickListener(this);
        register.setOnClickListener(this);
        registerPrivacy.setOnClickListener(this);

        //复选框点击变化事件
        checkBox.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showCode:
                show_code.setImageBitmap(Code.getInstance().createBitmap());
                realCode=Code.getInstance().getCode();
                break;

            case R.id.privacy_register:
                //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
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


            case R.id.register:
                String phone_number=phoneNumber.getText().toString().trim();
                String password1=password_first.getText().toString().trim();
                String password2=password_again.getText().toString().trim();
                String imageCode=image_code.getText().toString().trim();

                if(granted){
                    if(!TextUtils.isEmpty(phone_number) && !TextUtils.isEmpty(password1)&&!TextUtils.isEmpty(password2) && !TextUtils.isEmpty(imageCode) ){
                        if(password1.equals(password2)){
                            if(imageCode.equals(realCode)){
                                //往数据库中添加一条用户数据
                                myDataBaseHelper.add(phone_number,password1);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(this,  "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(this,"图形验证码输入错误，请再次尝试",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this,"信息未填写完整，请完善",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"请先同意用户隐私条款！",Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (checkBox.getId()){
            case R.id.checked:
                if(isChecked){
                    granted=true;
                }

                //回传用户隐私授权结果至Mob平台
                MobSDK.submitPolicyGrantResult(granted, null);
                break;
        }
    }
}