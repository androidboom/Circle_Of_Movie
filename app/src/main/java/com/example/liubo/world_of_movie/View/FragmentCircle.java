package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Adapter.CircleAdapter;
import com.example.liubo.world_of_movie.Bean.CircleInfo;
import com.example.liubo.world_of_movie.Circle.SubmitCircle;
import com.example.liubo.world_of_movie.IM.ChatActivity;
import com.example.liubo.world_of_movie.Login.ForgetPassword;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Login.Sign;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.EaseConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

public class FragmentCircle extends Fragment {
    private View view;
    private ListView lv;
    private String mainsignup_userid;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;
    private MyApplication app;
    private List<CircleInfo> listViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle,null);
        init();
        setListener();
        request();
        return view;
    }

    public void init(){
        lv = (ListView)view.findViewById(R.id.lv);
        mainsignup_userid = getArguments().getString("LOGIN");
        right_add = (ImageView)view.findViewById(R.id.right_add);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("圈子");

    }

    public void setListener(){
        right_add.setOnClickListener(MyListener);

    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.right_add:
                    final View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow, null);
                    TextView poptext = (TextView) popupView.findViewById(R.id.poptext);
                    // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                    PopupWindow window = new PopupWindow(popupView, 300, 150);
                    // TODO: 2016/5/17 设置动画
                    window.setAnimationStyle(R.style.popup_window_anim);
                    // TODO: 2016/5/17 设置背景颜色
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                    // TODO: 2016/5/17 设置可以获取焦点
                    window.setFocusable(true);
                    // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                    window.setOutsideTouchable(true);
                    // TODO：更新popupwindow的状态
                    window.update();
                    // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                    window.showAsDropDown(right_add, 0, 40);
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), SubmitCircle.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("signup_userid",mainsignup_userid);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
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
        Call<String> call = request.scan();

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("scan", "获取圈子成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                Gson gson = new Gson();
                Type type = new TypeToken<List<CircleInfo>>() {}.getType();
                Object fromjson = gson.fromJson(response.body(),type);
                listViews = (List<CircleInfo>) fromjson;
                CircleAdapter circleAdapter = new CircleAdapter(getActivity(),listViews);
                lv.setAdapter(circleAdapter);

            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("scan", "获取圈子失败" + "onFailure = \n" + t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        request();
    }
}
