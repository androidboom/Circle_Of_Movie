package com.example.liubo.world_of_movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Bean.CircleInfo;
import com.example.liubo.world_of_movie.Bean.UsersInfo;
import com.example.liubo.world_of_movie.R;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Liubo on 2018/1/21.
 */

public class CircleAdapter extends BaseAdapter {
    private List<CircleInfo> getData;
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;

    public CircleAdapter(Context context,List<CircleInfo> getData) {
        this.getData = getData;
        this.context = context;
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
        final String strid = getData.get(position).getMoments_id();

        holder.pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("signup_userid",strid);
                intent.putExtras(bundle);
                intent.setClass(context, UsersInfo.class);
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
    }

}
