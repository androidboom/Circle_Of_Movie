package com.example.liubo.world_of_movie.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.liubo.world_of_movie.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class LocationAdapter extends BaseAdapter {

    private final List<PoiInfo> data;
    private final Context context;

    public LocationAdapter(List<PoiInfo> data, Context context) {
        this.data=data;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (data!=null){
           return data.size();
        }else {

            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.location_list_item, null);
        //TextView tv_location = (TextView) view.findViewById(R.id.tv_location);
        TextView tv_location_address = (TextView) view.findViewById(R.id.tv_location_address);
        TextView tv_location_name = (TextView) view.findViewById(R.id.tv_location_name);
        //tv_location.setText(data.get(position).location+"");
        tv_location_address.setText(data.get(position).address);
        tv_location_name.setText(data.get(position).name);

        return view;
    }
}
