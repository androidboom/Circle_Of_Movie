package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.BaseView.MyScrollView;
import com.example.liubo.world_of_movie.Login.ForgetPassword;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Login.Sign;
import com.example.liubo.world_of_movie.Me.UpadteActivity;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentMe extends Fragment {
    private View headerView;
    private ImageView imgView;
    private View view;
    private MyScrollView scrollview;
    private MyApplication app;
    private TextView name;
    private TextView note;
    private TextView realname;
    private TextView signup_userid;
    private TextView birth;
    private TextView sex;
    private Button seting;
    private Button version;
    private Button aboutus;
    private Button logout;
    private Button update;
    private String strname;
    private String strnote;
    private String strrealname;
    private String strbirth;
    private String strsex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,container,false);
        init();
        setListener();
        return view;
    }

    private void init() {
        scrollview = (MyScrollView)view.findViewById(R.id.scrollview);
        headerView = view.findViewById(R.id.lay_header);
        imgView = (ImageView) view.findViewById(R.id.civ_avatar);
        name = (TextView)view.findViewById(R.id.name);
        note = (TextView)view.findViewById(R.id.note);

        realname = (TextView)view.findViewById(R.id.realname);
        signup_userid= (TextView)view.findViewById(R.id.signup_userid);
        birth = (TextView)view.findViewById(R.id.birth);
        sex = (TextView)view.findViewById(R.id.sex);

        seting = (Button)view.findViewById(R.id.seting);
        version = (Button)view.findViewById(R.id.version);
        aboutus = (Button)view.findViewById(R.id.aboutus);
        logout = (Button)view.findViewById(R.id.logout);
        update = (Button)view.findViewById(R.id.update);

        strname = name.getText().toString();
        strnote = note.getText().toString();
        strrealname = realname.getText().toString();
        strbirth = birth.getText().toString();
        strsex = sex.getText().toString();

        headerView.post(new Runnable() {
            @Override
            public void run() {
                scrollview.setHeaderView(headerView, imgView);
            }
        });
    }

    public void setListener(){
        seting.setOnClickListener(MyListener);
        version.setOnClickListener(MyListener);
        aboutus.setOnClickListener(MyListener);
        logout.setOnClickListener(MyListener);
        update.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.seting:
                    break;
                case R.id.version:
                    break;
                case R.id.aboutus:
                    break;
                case R.id.update:
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",strname);
                    bundle.putString("note",strnote);
                    bundle.putString("realname",strrealname);
                    bundle.putString("birth",strbirth);
                    bundle.putString("sex",strsex);
                    bundle.putString("signup_userid",MainActivity.signup_userid);
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(), UpadteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.logout:
                    break;
            }
        }
    };

    public void request() {

        // 创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(app.getValue()) // 设置网络请求 Url
                // 增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                // 增加返回值为Gson的支持
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 创建网络请求接口的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 对发送请求进行封装
        Call<String> call = request.getString("", "");

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("login", "影视圈登陆成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("login", "影视圈登陆失败" + "onFailure = \n" + t.toString());
            }
        });
    }



}
