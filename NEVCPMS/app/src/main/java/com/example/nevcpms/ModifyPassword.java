package com.example.nevcpms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModifyPassword extends AppCompatActivity implements View.OnClickListener{

    private EditText originalPassword;
    private EditText newPassword1;
    private EditText newPassword2;
    private Button commitModify;
    private TextView account;

    private String phoneNumber_MP;

    private MyDataBaseHelper myDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_layout);

        myDataBaseHelper=new MyDataBaseHelper(this,"LBS.db",null,1);

        initView();
    }

    private void initView() {
        originalPassword=findViewById(R.id.original_password);
        newPassword1=findViewById(R.id.new_password_1);
        newPassword2=findViewById(R.id.new_password_2);
        commitModify=findViewById(R.id.commit_modify);
        account=findViewById(R.id.account);

        Intent intent=getIntent();
        phoneNumber_MP=intent.getStringExtra("phoneNumber");

        account.setText(phoneNumber_MP);

        //设置点击事件
        commitModify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit_modify:
                String originalPw=originalPassword.getText().toString().trim();
                String newPw1=newPassword1.getText().toString().trim();
                String newPw2=newPassword2.getText().toString().trim();

                String pW=myDataBaseHelper.findPasswordByPhone(phoneNumber_MP);

                if(!TextUtils.isEmpty(originalPw)&&!TextUtils.isEmpty(newPw1)&&!TextUtils.isEmpty(newPw2)){
                    if(newPw1.equals(newPw2)){
                        if(originalPw.equals(pW)){
                            myDataBaseHelper.updatePassword(newPw1,phoneNumber_MP);
                            finish();
                            Toast.makeText(this,  "修改成功！", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this,"原密码验证错误，请重新输入！",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this,"两次密码填写不一致！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"信息未填写完整，请完善",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}