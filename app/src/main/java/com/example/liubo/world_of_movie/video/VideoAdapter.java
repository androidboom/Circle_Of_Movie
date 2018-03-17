package com.example.liubo.world_of_movie.video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liubo.world_of_movie.R;

import java.util.List;

/**
 * Created by xingyanna on 2018/3/12.
 */

public class VideoAdapter extends BaseAdapter {
    private List<VideoInfo> list;
    private Context context;
    private LayoutInflater mInflater;
    //private ArrayList<VideoActivity.LoadedImage> photos = new ArrayList<VideoActivity.LoadedImage>();
    public VideoAdapter(Context context ,List<VideoInfo> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        /*Dialog dialog = new Dialog(context);
        dialog.setContentView();*/
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    /*public void addPhoto(VideoActivity.LoadedImage image){
        photos.add(image);
    }*/
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder hold = null;
        if (convertView==null) {
            hold = new ViewHolder();
            convertView = mInflater.inflate(R.layout.video_list_item, null);
            hold.img = (ImageView) convertView.findViewById(R.id.video_img);
            hold.title = (TextView) convertView.findViewById(R.id.video_title);
            hold.pro = convertView.findViewById(R.id.video_pro);
            hold.director = convertView.findViewById(R.id.video_director);
            hold.performer = convertView.findViewById(R.id.video_head);
            convertView.setTag(hold);
        }else{
            hold = (ViewHolder) convertView.getTag();
        }
        hold.title.setText(list.get(position).getName());
        hold.pro.setText(list.get(position).getContent());
        hold.director.setText(list.get(position).getDirector());
        hold.performer.setText(list.get(position).getPerformer());
       // hold.img.setImageBitmap(getImage(list.get(position).getPic_url()));
       /* hold.tv_title.setText(list.get(position).getTitle());
        long min = list.get(position).getDuration() /1000 / 60;
        long sec = list.get(position).getDuration() /1000 % 60;
        hold.tv_time.setText(min+":"+sec);
        hold.img.setImageBitmap(photos.get(position).getBitmap());*/
        Glide.with(context)
                .load(list.get(position).getPic_url())
                .into(hold.img);
        return convertView;
    }
    /*public static Bitmap getImage(String path){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            System.out.println("tdw1");
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
    private final class ViewHolder{
        private ImageView img;
        private TextView title;
        private TextView pro;
        private TextView performer;
        private TextView director;
    }
}

