package com.example.liubo.world_of_movie.video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.Utils.CommonTools;
import com.example.liubo.world_of_movie.Utils.DisplayUtil;

import java.util.List;


public class VideoDetailsActivity extends Activity {

    private List<VideoInfo> audioInfo;
    private String urlPath;
    private TextView video_details_title;
    private TextView video_details_pro;
    private TextView video_details_d;
    private int position;


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

        initView();
        loadData();
    }
    private void initView(){
        // ButterKnife.bind(this);
        initFakeStatusBarHeight(true);
    }

    /*private void initWidget(){
        initWebViewSetting(mWebView);
    }*/
    /*private void setupListener(){
        setupWebViewListener();
    }*/

    private void loadData(){
        View view = findViewById(R.id.activity_video_rl);
        initVideoMode(view);
        // String url = "http://www.wezeit.com/wap/297121.html";
        // loadWebviewUrl(url);
    }
   /* protected void loadWebviewUrl(String url){
        DebugTools.d("js2 discovery2 jump3 vote2 news2 current url: " + url);
        if(!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }
    }*/

    protected int mPixelInsetTop;
    protected void initFakeStatusBarHeight(boolean isNewsPage){
        View statusbarBgLayout = (View)findViewById(R.id.statusbar_bg_layout);
        if(statusbarBgLayout == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPixelInsetTop = CommonTools.getStatusbarHeight(this);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)statusbarBgLayout.getLayoutParams();
            params.height = mPixelInsetTop;
            statusbarBgLayout.setLayoutParams(params);
            statusbarBgLayout.setBackgroundResource(R.color.black);
        }else{
            mPixelInsetTop = 0;
            statusbarBgLayout.setVisibility(View.GONE);
        }
    }

    //----------videoview----------------
    private VideoViewHolderControl.VideoViewHolder mVideoHolder;
    private VideoView mVideoView;
    private VideoViewHolderControl mVideoControl;
    private void initVideoMode(View view){
        showFullScreen(false);
        mVideoView = (VideoView) view.findViewById(R.id.videoview);
        mVideoHolder = new VideoViewHolderControl.VideoViewHolder(view);
        mVideoHolder.imgIv.setImageResource(R.mipmap.index);
        mVideoControl = new VideoViewHolderControl(mVideoHolder, mVideoView, audioInfo.get(position).getMovie_url());
        setupVideoControlListener(mVideoControl);
        mVideoControl.setup();
        setVideoViewLayout(false);
    }

    private void setupVideoControlListener(VideoViewHolderControl control){
        control.setOnVideoControlListener(new VideoViewHolderControl.OnVideoControlProxy() {
            @Override
            public void onCompletion() {
                //  DebugTools.d("video2 onCompletion");
                setFullScreen(false);
            }

            @Override
            public void onClickHalfFullScreen() {
                boolean isFull = isFullScreen();
                setFullScreen(!isFull);
            }

            @Override
            public void onError(int code, String msg) {

            }

        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            initHalfFullState(true);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            initHalfFullState(false);
        }
    }

    private void initHalfFullState(boolean isFull){
        // DebugTools.d("video2 initHalfFullState isFull: " + isFull);
        setVideoViewLayout(isFull);
        showFullScreen(isFull);
    }


    //---------videoview fullscreen---------
    private void showFullScreen(boolean isFullScreen){
        if(isFullScreen){
//		      //不显示程序的标题栏
            hideNavigationBar();
        }else{
            showNavigationBar();
        }
    }

    protected void setFullScreen(boolean isFull){
        if(isFull){
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }else{
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    protected boolean isFullScreen(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void showNavigationBar(){
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public void hideNavigationBar() {
        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN; // hide status bar

        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }

    private void setVideoViewLayout(boolean isFull){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mVideoHolder.videoRl.getLayoutParams();
        RelativeLayout.LayoutParams controlParams = (RelativeLayout.LayoutParams)mVideoHolder.mediaControl.getLayoutParams();
        RelativeLayout.LayoutParams indexImageParams = (RelativeLayout.LayoutParams)mVideoHolder.imgIv.getLayoutParams();

        int videoMarginTop = (int)getResources().getDimension(R.dimen.library_video_video_margin_top) + mPixelInsetTop;
        if(isFull){
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            params.setMargins(0, 0, 0, 0);

            controlParams.setMargins(0, 0, 0, 0);

            indexImageParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            indexImageParams.setMargins(0, 0, 0, 0);
        }else{
            params.height = DisplayUtil.dip2px(this, 202);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.setMargins(0, videoMarginTop, 0, 0);

            controlParams.setMargins(0, 0, 0, 0);

            indexImageParams.height = DisplayUtil.dip2px(this, 202);
            indexImageParams.setMargins(0, 0, 0, 0);

        }
        mVideoHolder.videoRl.setLayoutParams(params);
        mVideoHolder.mediaControl.setLayoutParams(controlParams);
        mVideoHolder.imgIv.setLayoutParams(indexImageParams);
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

/*
public class VideoDetailsActivity extends Activity{

    private List<VideoInfo> audioInfo;
    private VideoView videoView;
    private String urlPath;
    private  int position;
    private TextView video_details_title;
    private TextView video_details_pro;
    private TextView video_details_d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.video_details);

        init();
         audioInfo = (List<VideoInfo>) getIntent().getSerializableExtra("videoinfo");
        position = (int) getIntent().getSerializableExtra("position");
        Log.d("xyn", "onCreate: "+audioInfo.get(position).getMovie_url());

        video_details_d.setText(audioInfo.get(position).getPerformer());
        video_details_pro.setText(audioInfo.get(position).getContent());
        video_details_title.setText(audioInfo.get(position).getName());


        urlPath = audioInfo.get(position).getMovie_url();
        final Uri uri = Uri.parse( urlPath );
        //设置视频路径
        videoView.setVideoURI(uri);
        //开始播放视频
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }

    private void init(){
        video_details_d = findViewById(R.id.video_details_d);
        videoView = findViewById(R.id.videoview);
        video_details_pro = findViewById(R.id.video_details_pro);
        video_details_title = findViewById(R.id.video_details_title);
    }
}
*/
