package com.example.liubo.world_of_movie.IM;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liubo.world_of_movie.Login.ForgetPassword;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.Sign;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.View.MainActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/3/8.
 */

public class IMTest extends AppCompatActivity {
    private EditText signup_userid;
    private EditText signup_pw;
    private Button im_signin;
    private Button im_signup;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private TextView forget_pw;
    private MyApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imtest_layout);
        init();
        setListener();
    }

    public void init(){
        im_signin = (Button) findViewById(R.id.im_signin);
        im_signup = (Button) findViewById(R.id.im_signup);
        signup_userid = (EditText) findViewById(R.id.signup_userid);
        signup_pw = (EditText) findViewById(R.id.signup_pw);
        right_add = (ImageView)findViewById(R.id.right_add);
        forget_pw = (TextView)findViewById(R.id.forget_pw);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("IM登录");
    }

    public void setListener(){
        im_signin.setOnClickListener(MyListener);
        im_signup.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.im_signup:
                    signup();
                    break;
                case R.id.im_signin:
                    signin();
                    break;
            }
        }
    };

    public void signin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(signup_userid.getText().toString().trim(),
                            signup_pw.getText().toString().trim());
                    Log.e("IM","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("IM","注册失败" + e.getErrorCode() + e.getMessage());
                }
            }
        }).start();
        EMClient.getInstance().logout(true);
    }

    public void signup(){
        EMClient.getInstance().login(signup_userid.getText().toString().trim(),
                signup_pw.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent();
                        intent.setClass(IMTest.this,MainActivity.class);
                        startActivity(intent);
                        Log.e("IM","登陆成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.e("IM","登录失败" + i + "," + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
    }
}

