package com.example.liubo.world_of_movie.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.View.MainActivity;
import com.example.liubo.world_of_movie.View.TestActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Liubo on 2017/12/31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText signup_username;
    private EditText signup_pw;
    private Button signup_btn;
    private Button signin_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
        setListener();
    }

    public void initView() {
        signup_username = (EditText)findViewById(R.id.signup_username);
        signup_pw = (EditText)findViewById(R.id.signup_pw);
        signup_btn = (Button)findViewById(R.id.signup_btn);
        signin_btn = (Button)findViewById(R.id.signin_btn);
    }

    public void setListener(){
        signup_btn.setOnClickListener(MyListener);
        signin_btn.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn:
                    signup();
                    break;
                case R.id.signin_btn:
                    signin();
            }
        }
    };

    //注册方法
    private void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(signup_username.getText().toString().trim(),
                            signup_pw.getText().toString().trim());
                    Log.e("test","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("test","注册失败" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        }).start();
    }

    private void signin(){
        EMClient.getInstance().login(signup_username.getText().toString().trim(), signup_pw.getText().toString().trim(), new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.e("test","登录失败" + i + "," + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

}
