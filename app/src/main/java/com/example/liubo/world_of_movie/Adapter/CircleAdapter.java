package com.example.liubo.world_of_movie.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;

import org.json.JSONArray;

/**
 * Created by Liubo on 2018/1/21.
 */

public class CircleAdapter extends BaseAdapter {
    private JSONArray getData;
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;

    public CircleAdapter(Context context,JSONArray getData) {
        this.getData = getData;
        this.context = context;
        this.inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return getData.length();
    }

    @Override
    public Object getItem(int position) {
        return getData.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circleadapter_item, null);
            holder = new Holder();
            holder.user_icon = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.data = (TextView) convertView.findViewById(R.id.data);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.gridView = (GridView) convertView.findViewById(R.id.grid);
            holder.pinglun = (ImageView)convertView.findViewById(R.id.iv_pinglun);
            holder.praise = (ImageView)convertView.findViewById(R.id.iv_praise);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        return convertView;
    }

    public class Holder {
        ImageView user_icon;
        TextView name;
        TextView data;
        TextView text;
        GridView gridView;
        ImageView pinglun;
        ImageView praise;
    }

}
