package com.example.liubo.world_of_movie.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liubo.world_of_movie.Bean.SignIMUser;
import com.example.liubo.world_of_movie.Bean.SignUser;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/1/21.
 */

public class Sign extends AppCompatActivity {
    private EditText signup_userid;
    private EditText signup_pw;
    private Button signin_btn;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private EditText signup_name;
    private EditText signup_idcard;
    private MyApplication app;
    private String IM_userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);
        init();
        setListener();
    }

    public void init(){
        app = (MyApplication) getApplication(); // 获得CustomApplication对象
        signin_btn = (Button) findViewById(R.id.signin_btn);
        signup_userid = (EditText)findViewById(R.id.signup_userid);
        signup_pw = (EditText)findViewById(R.id.signup_pw);
        signup_name = (EditText)findViewById(R.id.signup_name);
        signup_idcard = (EditText)findViewById(R.id.signup_idcard);
        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("注册");
    }

    public void setListener(){
        signin_btn.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn:
                case R.id.signin_btn:
                    request();
            }
        }
    };

    public void request() {

        IM_userid = "im" + signup_userid.getText().toString();

        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.VALUE) // 设置网络请求 Url
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送请求进行封装
        Call<String> call = request.getString(signup_userid.getText().toString(),
                signup_pw.getText().toString(),signup_name.getText().toString(),
                signup_idcard.getText().toString());

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("sign", "注册影视圈账户成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                signin();

            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("sign", "注册影视圈账户失败" + "onFailure = \n" + t.toString());
                Toast.makeText(Sign.this, "请重新注册", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signin(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(IM_userid.trim(),
                            signup_pw.getText().toString().trim());
                    Log.e("sign","IM注册成功");
                    requestIM();
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("sign","IM注册失败" + e.getErrorCode() + e.getMessage());
                }
            }
        }).start();
    }

    public void requestIM() {
        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.VALUE) // 设置网络请求 Url
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送请求进行封装
        Call<String> call = request.getString(signup_userid.getText().toString()
                ,IM_userid, signup_pw.getText().toString());

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("sign", "IM上传成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                Toast.makeText(Sign.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("sign", "IM上传失败" + "onFailure = \n" + t.toString());
            }
        });
    }

}