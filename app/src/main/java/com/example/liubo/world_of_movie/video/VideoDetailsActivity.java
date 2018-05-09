package com.example.liubo.world_of_movie.video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.Utils.CommonTools;
import com.example.liubo.world_of_movie.Utils.DisplayUtil;
import java.util.List;
import cn.jzvd.JZVideoPlayerStandard;


public class VideoDetailsActivity extends AppCompatActivity{

    private List<VideoInfo> audioInfo;
    private String urlPath;
    private TextView video_details_title;
    private TextView video_details_pro;
    private TextView video_details_d;
    private int position;
    private JZVideoPlayerStandard jzVideoPlayerStandard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_details);

        init();
        audioInfo = (List<VideoInfo>) getIntent().getSerializableExtra("videoinfo");
        position = (int) getIntent().getSerializableExtra("position");
        Log.d("xyn", "onCreate: "+audioInfo.get(position).getMovie_url());
        video_details_d.setText(audioInfo.get(position).getPerformer());
        video_details_pro.setText(audioInfo.get(position).getContent());
        video_details_title.setText(audioInfo.get(position).getName());

        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.jc_player);
        jzVideoPlayerStandard.setUp(audioInfo.get(position).getMovie_url()
                , JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, audioInfo.get(position).getName());
        //jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

        /*initView();
        loadData();*/
    }
    @Override
    public void onBackPressed() {
        if (jzVideoPlayerStandard.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        jzVideoPlayerStandard.releaseAllVideos();
    }


    private void init(){
        // mVideoView =findViewById(R.id.videoview);
        video_details_d = findViewById(R.id.video_details_d);
        video_details_pro = findViewById(R.id.video_details_pro);
        video_details_title = findViewById(R.id.video_details_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}