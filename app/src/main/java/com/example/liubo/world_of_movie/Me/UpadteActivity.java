package com.example.liubo.world_of_movie.Me;

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

import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2018/3/11.
 */

public class UpadteActivity extends AppCompatActivity {
    private EditText newname;
    private EditText newnote;
    private EditText newrealname;
    private EditText newbirth;
    private EditText newsex;
    private Button submit;
    private String signup_userid;
    private MyApplication app;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);
        init();
        setListener();
    }

    private void init() {
        newname = (EditText)findViewById(R.id.newname);
        newnote = (EditText)findViewById(R.id.newnote);
        newrealname = (EditText)findViewById(R.id.newrealname);
        newbirth = (EditText)findViewById(R.id.newbirth);
        newsex = (EditText)findViewById(R.id.newsex);
        submit = (Button)findViewById(R.id.submit);

        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("修改信息");

        Intent intent = new Intent();
        Bundle bundle = intent.getExtras();

        signup_userid = bundle.getString("signup_userid");
//        newname.setText(String.valueOf(bundle.getString("name")));
//        newnote.setText(String.valueOf(bundle.getString("note")));
//        newrealname.setText(String.valueOf(bundle.getString("realname")));
//        newbirth.setText(String.valueOf(bundle.getString("birth")));
//        newsex.setText(String.valueOf(bundle.getString("sex")));
//        signup_userid = bundle.getString("signup_userid");
    }

    public void setListener(){
        submit.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.submit:
                    request();
                    break;

            }
        }
    };

    public void request() {

        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.7.82.115:8080/springmvc/") // 设置网络请求 Url
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送请求进行封装
        Call<String> call = request.getinfo(signup_userid, newname.getText().toString(),newrealname.getText().toString()
                ,newbirth.getText().toString(),newsex.getText().toString(),newnote.getText().toString());

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("submit", "上传个人信息成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("submit", "上传个人信息失败" + "onFailure = \n" + t.toString());
            }
        });
    }

}
