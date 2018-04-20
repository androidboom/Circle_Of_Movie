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
import android.widget.Toast;

import com.example.liubo.world_of_movie.Bean.AlterPSW;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/3/6.
 */

public class ForgetPassword extends AppCompatActivity {
    private TextView title;
    private ImageView left_back;
    private ImageView right_add;
    private Button alterpasswordbtn;
    private EditText alter_name;
    private EditText alter_userid;
    private EditText alter_pw;
    private MyApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_layout);
        init();
        setListener();
    }

    public void init(){
        app = (MyApplication) getApplication(); // 获得CustomApplication对象
        alterpasswordbtn = (Button) findViewById(R.id.alterpasswordbtn);
        alter_userid = (EditText) findViewById(R.id.alter_userid);
        alter_pw = (EditText) findViewById(R.id.alter_pw);
        alter_name = (EditText) findViewById(R.id.alter_name);
        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("找回密码");
    }

    public void setListener(){
        alterpasswordbtn.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.alterpasswordbtn:
                    request();
                    break;
            }
        }
    };

    public void request() {

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
        Call<String> call = request.get(alter_userid.getText().toString(), alter_pw.getText().toString(),
                alter_name.getText().toString());

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("login", "修改密码" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                Toast.makeText(ForgetPassword.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("login", "修改密码" + "onFailure = \n" + t.toString());
            }
        });
    }


}
