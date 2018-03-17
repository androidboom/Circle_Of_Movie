package com.example.liubo.world_of_movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Bean.CircleInfo;
import com.example.liubo.world_of_movie.Bean.DiscussInfo;
import com.example.liubo.world_of_movie.Bean.UsersInfo;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.R;

import org.json.JSONArray;

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

public class CircleAdapter extends BaseAdapter {
    private List<CircleInfo> getData;
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;
    private Handler handler;
    private String moments;
    public static int flag = 0;

    public CircleAdapter(Context context,List<CircleInfo> getData,Handler handler) {
        this.getData = getData;
        this.context = context;
        this.handler = handler;
        this.inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return getData.size();
    }

    @Override
    public Object getItem(int position) {
        return getData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circleadapter_item, null);
            holder = new Holder();
            holder.user_icon = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.date = (TextView) convertView.findViewById(R.id.data);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            //holder.gridView = (GridView) convertView.findViewById(R.id.grid);
            holder.pinglun = (View)convertView.findViewById(R.id.layout_pinglun);
            holder.praise = (View)convertView.findViewById(R.id.layout_praise);
            holder.tv_praise = (TextView)convertView.findViewById(R.id.tv_praise);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_praise);
            holder.lv_pinglun = (ListView)convertView.findViewById(R.id.lv_pinglun);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        String str= getData.get(position).getMoments_date();
        String newstr = str.substring(5,16);
        holder.name.setText(getData.get(position).getMoments_user());
        holder.date.setText(newstr);
        holder.text.setText(getData.get(position).getMoments_content());
        holder.tv_praise.setText(getData.get(position).getMoments_praise());

        List<DiscussInfo> listdata = getData.get(position).getListDiscuss();
        CirclePingLunAdapter circlePingLunAdapter = new CirclePingLunAdapter(context,listdata);
        holder.lv_pinglun.setAdapter(circlePingLunAdapter);

        holder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (flag == 0) {
                    // 第一次单击触发的事件
                    moments = getData.get(position).getMoments_id();
                    String s = holder.tv_praise.getText().toString();
                    int a = Integer.parseInt(s);
                    int a1 = a + 1;
                    String s1 = String.valueOf(a1);
                    holder.tv_praise.setText(s1);
                    holder.imageView.setBackgroundResource(R.drawable.common_praise3x);
                    requestpiaise();
                    flag = 1;
//                } else {
//                    // 第二次单击buttont改变触发的事件
//                    Message message = new Message();
//                    message.what = 3;
//                    message.obj = getData.get(position).getMoments_id();
//                    handler.sendMessage(message);
//                    String s = holder.tv_praise.getText().toString();
//                    int a = Integer.parseInt(s);
//                    int a1 = a - 1;
//                    String s1 = String.valueOf(a1);
//                    holder.tv_praise.setText(s1);
//                    holder.imageView.setBackgroundResource(R.drawable.common_praise3x_nor);
//                    flag = 0;
//                }
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 2;
                message.obj = getData.get(position).getMoments_id();
                handler.sendMessage(message);
            }
        });

        holder.user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 2;
                message.obj = getData.get(position).getMoments_id();
                handler.sendMessage(message);
            }
        });

        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 1;
                message.obj = getData.get(position).getMoments_id();
                handler.sendMessage(message);
            }
        });

        return convertView;
    }

    public class Holder {
        ImageView user_icon;
        TextView name;
        TextView date;
        TextView text;
        //GridView gridView;
        View pinglun;
        View praise;
        TextView tv_praise;
        ImageView imageView;
        ListView lv_pinglun;
    }

    public void requestpiaise() {

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
        Call<String> call = request.praise(moments);

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("pinglun", "点赞成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());

            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("pinglun", "点赞失败" + "onFailure = \n" + t.toString());
            }
        });
    }

}
