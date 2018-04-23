package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Bean.UsersInfo;
import com.example.liubo.world_of_movie.IM.ChatActivity;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Me.AboutUsActivity;
import com.example.liubo.world_of_movie.Me.UpadteActivity;
import com.example.liubo.world_of_movie.Me.VersionActivity;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/1/21.
 */

public class UserInfo extends AppCompatActivity {
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private ImageView imgView;
    private TextView name;
    private TextView note;
    private TextView realname;
    private TextView signup_userid;
    private TextView birth;
    private TextView sex;
    private List<UsersInfo> listViews;
    private String mainsignup_userid;
    private Button chat;
    private MyApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinfo);
        init();
        request();
    }

    public void init(){
        Bundle bundle = this.getIntent().getExtras();
        mainsignup_userid = bundle.getString("signup_userid");

        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        title = (TextView)findViewById(R.id.title);
        title.setText("详情");

        imgView = (ImageView)findViewById(R.id.civ_avatar);
        name = (TextView)findViewById(R.id.name);
        note = (TextView)findViewById(R.id.note);
        realname = (TextView)findViewById(R.id.realname);
        signup_userid= (TextView)findViewById(R.id.signup_userid);
        birth = (TextView)findViewById(R.id.birth);
        sex = (TextView)findViewById(R.id.sex);

        chat = (Button)findViewById(R.id.chat);
    }

    public void setListener(){
        left_back.setOnClickListener(MyListener);
        chat.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_back:
                    finish();
                    break;
                case R.id.chat:
                    startActivity(new Intent(UserInfo.this, ChatActivity.class).
                            putExtra(EaseConstant.EXTRA_USER_ID,"im" + mainsignup_userid));
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
        Call<String> call = request.getnewinfo(mainsignup_userid);

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("resume", "刷新个人信息成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                Gson gson = new Gson();
                Type type = new TypeToken<List<UsersInfo>>() {}.getType();
                Object fromjson = gson.fromJson(response.body(),type);
                listViews = (List<UsersInfo>) fromjson;

                name.setText(listViews.get(0).getUsername());
                note.setText(listViews.get(0).getSignature());
                realname.setText(listViews.get(0).getReal_name());
                birth.setText(listViews.get(0).getBirthday());
                sex.setText(listViews.get(0).getSex());
                signup_userid.setText(listViews.get(0).getUserid());
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("resume", "刷新个人信息失败" + "onFailure = \n" + t.toString());
            }
        });
    }
}
