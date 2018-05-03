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
import com.example.liubo.world_of_movie.Bean.UsersInfo;
import com.example.liubo.world_of_movie.Login.ForgetPassword;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Login.Sign;
import com.example.liubo.world_of_movie.Me.AboutUsActivity;
import com.example.liubo.world_of_movie.Me.UpadteActivity;
import com.example.liubo.world_of_movie.Me.VersionActivity;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.Utils.LoginSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

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
    private TextView name;
    private TextView note;
    private TextView realname;
    private TextView signup_userid;
    private TextView birth;
    private TextView sex;
    private Button version;
    private Button aboutus;
    private Button logout;
    private Button update;
    private String strname;
    private String strnote;
    private String strrealname;
    private String strbirth;
    private String strsex;
    private String mainsignup_userid;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private List<UsersInfo>listViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,container,false);
        init();
        request();
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

        version = (Button)view.findViewById(R.id.version);
        aboutus = (Button)view.findViewById(R.id.aboutus);
        logout = (Button)view.findViewById(R.id.logout);
        update = (Button)view.findViewById(R.id.update);

        right_add = (ImageView)view.findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("我");

//        mainsignup_userid = getArguments().getString("LOGIN");
        mainsignup_userid = LoginSharedPreferences.getString(getActivity(), "id", "");
        signup_userid.setText(mainsignup_userid);

        headerView.post(new Runnable() {
            @Override
            public void run() {
                scrollview.setHeaderView(headerView, imgView);
            }
        });
    }

    public void setListener(){
        version.setOnClickListener(MyListener);
        aboutus.setOnClickListener(MyListener);
        logout.setOnClickListener(MyListener);
        update.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.version:
                    Intent intent1 = new Intent();
                    intent1.setClass(getActivity(), VersionActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.aboutus:
                    Intent intent2 = new Intent();
                    intent2.setClass(getActivity(), AboutUsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.update:
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",strname);
                    bundle.putString("note",strnote);
                    bundle.putString("realname",strrealname);
                    bundle.putString("birth",strbirth);
                    bundle.putString("sex",strsex);
                    bundle.putString("signup_userid",mainsignup_userid);
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(), UpadteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.logout:
//                    EMClient.getInstance().logout(true);    //同步方法

                    EMClient.getInstance().logout(false, new EMCallBack() {

                        @Override
                        public void onSuccess() {
                            Intent intent3 = new Intent();
                            intent3.setClass(getActivity(),LoginActivity.class);
                            startActivity(intent3);
                            LoginSharedPreferences.putString(getActivity(),"login","false");
                            LoginSharedPreferences.putString(getActivity(),"id",null);
                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }

                        @Override
                        public void onError(int code, String error) {

                        }
                    });

                        getActivity().onBackPressed();//销毁自己

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
                if(response != null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<UsersInfo>>() {}.getType();
                    Object fromjson = gson.fromJson(response.body(),type);
                    listViews = (List<UsersInfo>) fromjson;

                    name.setText(listViews.get(0).getReal_name());
                    note.setText(listViews.get(0).getSignature());
                    realname.setText(listViews.get(0).getUsername());
                    birth.setText(listViews.get(0).getBirthday());
                    sex.setText(listViews.get(0).getSex());

                    strname = name.getText().toString();
                    strnote = note.getText().toString();
                    strrealname = realname.getText().toString();
                    strbirth = birth.getText().toString();
                    strsex = sex.getText().toString();
                }
            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("resume", "刷新个人信息失败" + "onFailure = \n" + t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        request();
    }


}
