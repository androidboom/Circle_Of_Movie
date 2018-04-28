package com.example.liubo.world_of_movie.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.Me.UpadteActivity;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.video.VideoAdapter;
import com.example.liubo.world_of_movie.video.VideoDetailsActivity;
import com.example.liubo.world_of_movie.video.VideoInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Liubo on      2017/12/27.
 */

public class FragmentMain extends Fragment {
    private EditText editText;
    private ImageButton search_btn;
    private ListView video_list;
    private View view;
    private VideoInfo videoinfo;
    private List<VideoInfo>  listViews;
    private VideoAdapter madapter;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main,null);
        request();
        init();
        setListener();
        video_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("xyn", "onItemClick: ");
                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra("videoinfo", (Serializable) listViews);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        return view;
    }

    private void init(){
        editText =view.findViewById(R.id.query);
        search_btn=view.findViewById(R.id.search_clear);
        video_list = view.findViewById(R.id.video_list);
    }

    public void setListener(){
        search_btn.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_clear:
                    search_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
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
        Call<String> call = request.getVideoInfo();
        Log.d("xyn", "request: ");

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response != null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<VideoInfo>>() {}.getType();
                    Object fromjson = gson.fromJson(response.body(),type);
                    listViews = (List<VideoInfo>) fromjson;
                    if (listViews!=null) {
                        madapter = new VideoAdapter(getActivity(), listViews);
                        video_list.setAdapter(madapter);
                    }
                    Log.d("xyn", "onResponse: "+listViews);
                }
            }
            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
