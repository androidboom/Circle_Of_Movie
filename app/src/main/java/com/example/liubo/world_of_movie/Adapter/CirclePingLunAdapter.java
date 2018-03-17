package com.example.liubo.world_of_movie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Bean.CircleInfo;
import com.example.liubo.world_of_movie.Bean.DiscussInfo;
import com.example.liubo.world_of_movie.Bean.UsersInfo;
import com.example.liubo.world_of_movie.R;

import java.util.List;

/**
 * Created by Liubo on 2018/1/21.
 */

public class CirclePingLunAdapter extends BaseAdapter {
    private List<DiscussInfo> getData;
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;

    public CirclePingLunAdapter(Context context, List<DiscussInfo> getData) {
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
            convertView = inflater.inflate(R.layout.circlepinglunadapter_item, null);
            holder = new Holder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.name.setText(getData.get(position).getDis_username());
        holder.text.setText(getData.get(position).getDis_content());

        return convertView;
    }

    public class Holder {
        TextView name;
        TextView text;
    }

}
