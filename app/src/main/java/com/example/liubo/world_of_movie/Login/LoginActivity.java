package com.example.liubo.world_of_movie.Login;

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

import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.View.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by Liubo on 2017/12/31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText signup_userid;
    private EditText signup_pw;
    private Button signup_btn;
    private TextView signin_btn;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private TextView forget_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        setListener();
    }

    public void init(){
        signup_btn = (Button) findViewById(R.id.signup_btn);
        signin_btn = (TextView) findViewById(R.id.signin_btn);
        signup_userid = (EditText) findViewById(R.id.signup_userid);
        signup_pw = (EditText) findViewById(R.id.signup_pw);
        right_add = (ImageView)findViewById(R.id.right_add);
        forget_pw = (TextView)findViewById(R.id.forget_pw);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("登录");
    }

    public void setListener(){
        signup_btn.setOnClickListener(MyListener);
        signin_btn.setOnClickListener(MyListener);
        forget_pw.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn:
                    request();
                    break;
                case R.id.signin_btn:
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,Sign.class);
                    startActivity(intent);
                    break;
                case R.id.forget_pw:
                    Intent intent1 = new Intent();
                    intent1.setClass(LoginActivity.this,ForgetPassword.class);
                    startActivity(intent1);
                    break;
            }
        }
    };

    public void request() {

        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.215:8080/springmvc/") // 设置网络请求 Url
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送请求进行封装
        Call<String> call = request.getString(signup_userid.getText().toString(), signup_pw.getText().toString());

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("login", "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                intent.putExtra("LOGIN",signup_userid.getText().toString());
                startActivity(intent);
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("login", "onFailure = \n" + t.toString());
            }
        });
    }

}
