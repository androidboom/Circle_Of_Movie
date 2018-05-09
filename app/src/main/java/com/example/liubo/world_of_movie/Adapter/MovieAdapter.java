package com.example.liubo.world_of_movie.Adapter;


import android.content.Context;
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

import com.example.liubo.world_of_movie.Bean.MovieDiscussInfo;
import com.example.liubo.world_of_movie.Bean.MovieInfo;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;

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

public class MovieAdapter extends BaseAdapter {
    private List<MovieDiscussInfo> getData;
    private Context context;
    private LayoutInflater inflater;
    private Holder holder;

    public MovieAdapter(Context context, List<MovieDiscussInfo> getData) {
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
            convertView = inflater.inflate(R.layout.movieadapter_item, null);
            holder = new Holder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

//        String str= getData.get(position).getMoments_date();
//        String newstr = str.substring(5,16);
//        holder.name.setText(getData.get(position).getMoments_user());
//        holder.date.setText(newstr);
        holder.name.setText(getData.get(position).getDis_username());
        holder.text.setText(getData.get(position).getDis_content());

        return convertView;
    }

    public class Holder {
        TextView name;
        TextView text;
    }

}
