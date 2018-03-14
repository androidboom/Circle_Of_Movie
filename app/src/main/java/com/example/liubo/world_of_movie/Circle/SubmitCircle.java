package com.example.liubo.world_of_movie.Circle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Me.AboutUsActivity;
import com.example.liubo.world_of_movie.Me.UpadteActivity;
import com.example.liubo.world_of_movie.Me.VersionActivity;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.View.MainActivity;
import com.hyphenate.chat.EMClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/3/13.
 */

public class SubmitCircle extends AppCompatActivity {
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private EditText content;
    private MyApplication app;
    private String signup_userid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitcircle_layout);
        init();
        setListener();
    }

    public void init(){
        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setBackgroundResource(R.drawable.submit);
        left_back = (ImageView)findViewById(R.id.left_back);
        title = (TextView)findViewById(R.id.title);
        title.setText("");
        content = (EditText)findViewById(R.id.content);

        Bundle bundle = this.getIntent().getExtras();
        signup_userid = bundle.getString("signup_userid");

    }

    public void setListener(){
        left_back.setOnClickListener(MyListener);
        right_add.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_back:
                    finish();
                    break;
                case R.id.right_add:
                    //request();
                break;
            }
        }
    };

//    public void request() {
//
//        // 创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(app.getValue()) // 设置网络请求 Url
//                // 增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
//                // 增加返回值为Gson的支持
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // 创建网络请求接口的实例
//        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
//
//        // 对发送请求进行封装
//        Call<String> call = request.getString();
//
//        // 发送网络请求(异步)
//        call.enqueue(new Callback<String>() {
//            // 请求成功时回调
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Log.v("login", "圈子发表成功" + "response.message() = " + response.message() + "\n" +
//                        "response.body() = " + response.body());
//            }
//
//            // 请求失败时回调
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.v("login", "圈子发表成功登陆失败" + "onFailure = \n" + t.toString());
//            }
//        });
//    }
}
