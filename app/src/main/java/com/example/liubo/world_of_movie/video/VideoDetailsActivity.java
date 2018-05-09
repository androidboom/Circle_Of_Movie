package com.example.liubo.world_of_movie.video;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.liubo.world_of_movie.Adapter.MovieAdapter;
import com.example.liubo.world_of_movie.Bean.MovieDiscussInfo;
import com.example.liubo.world_of_movie.Bean.MovieInfo;
import com.example.liubo.world_of_movie.Circle.SubmitCircle;
import com.example.liubo.world_of_movie.Circle.SubmitMovie;
import com.example.liubo.world_of_movie.Login.GetRequest_Interface;
import com.example.liubo.world_of_movie.MyApplication;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.Utils.CommonTools;
import com.example.liubo.world_of_movie.Utils.DisplayUtil;
import com.example.liubo.world_of_movie.Utils.LoginSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.SocketHandler;

import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class VideoDetailsActivity extends AppCompatActivity{

    private List<VideoInfo> audioInfo;
    private String urlPath;
    private TextView video_details_title;
    private TextView video_details_pro;
    private TextView video_details_d;
    private int position;
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private ListView lv;
    private List<MovieInfo> listViews;
    private LinearLayout commentLinear;
    private EditText commentEdit;
    private Button commentButton;
    private String mainsignup_userid;
    private String positionid;
    private String commentEdittext;
    private ImageView right_add;
    private View popupView;
    private PopupWindow window;
    private ImageView left_back;
    private TextView title;
    private String movieid;
    private List<MovieDiscussInfo> listViewsnew;
    private LinearLayout layout_praise;
    private ImageView iv_praise;
    private TextView tv_praise;
    private String dianzannum;
    private TextView share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_details);

        init();
        movieid = (String)getIntent().getSerializableExtra("movieid");
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
        lv = (ListView)findViewById(R.id.lv);

        commentLinear = (LinearLayout)findViewById(R.id.commentLinear);
        commentEdit = (EditText)findViewById(R.id.commentEdit);
        commentButton = (Button)findViewById(R.id.commentButton);

        mainsignup_userid = LoginSharedPreferences.getString(this, "id", "");

        right_add = (ImageView)findViewById(R.id.right_add);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("影视中心");

        layout_praise = (LinearLayout)findViewById(R.id.layout_praise);
        iv_praise = (ImageView)findViewById(R.id.iv_praise);
        tv_praise = (TextView)findViewById(R.id.tv_praise);

        share = (TextView)findViewById(R.id.share);

//        movieid = audioInfo.get(position).getId();

        setListener();

        request();

    }

    public void setListener(){
        right_add.setOnClickListener(MyListener);
        layout_praise.setOnClickListener(MyListener);
        share.setOnClickListener(MyListener);

    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.right_add:
                    popupView = VideoDetailsActivity.this.getLayoutInflater().inflate(R.layout.popupwindow, null);
                    TextView poptext = (TextView) popupView.findViewById(R.id.poptext);
                    // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                    window = new PopupWindow(popupView, 300, 150);
                    // TODO: 2016/5/17 设置动画
                    window.setAnimationStyle(R.style.popup_window_anim);
                    // TODO: 2016/5/17 设置背景颜色
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                    // TODO: 2016/5/17 设置可以获取焦点
                    window.setFocusable(true);
                    // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
                    window.setOutsideTouchable(true);
                    // TODO：更新popupwindow的状态
                    window.update();
                    // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                    window.showAsDropDown(right_add, 0, 40);
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(VideoDetailsActivity.this, SubmitMovie.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("signup_userid",mainsignup_userid);
                            bundle.putString("movie",movieid);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            window.dismiss();
                        }
                    });
                    break;
                case R.id.layout_praise:
                    String s = tv_praise.getText().toString();
                    int a = Integer.parseInt(s);
                    int a1 = a + 1;
                    String s1 = String.valueOf(a1);
                    tv_praise.setText(s1);
                    iv_praise.setBackgroundResource(R.drawable.common_praise3x);
                    requestpiaise();
                    break;
                case R.id.share:
                    Intent intent = new Intent();
                    intent.setClass(VideoDetailsActivity.this, SubmitCircle.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("signup_userid",mainsignup_userid);
                    intent.putExtras(bundle);
                    startActivity(intent);
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

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("scan", "获取圈子成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                if(response != null){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<MovieInfo>>() {}.getType();
                    Object fromjson = gson.fromJson(response.body(),type);
                    listViews = (List<MovieInfo>) fromjson;
                    Log.v("liuboyeye", "listViews" + String.valueOf(listViews));
                    for(int i = 0;i < listViews.size();i++){
                        Log.v("liuboyeye", "id" + listViews.get(i).getId());
                        Log.v("liuboyeye","movieid" + movieid);
                        if (listViews.get(i).getId().equals(movieid)){
                            listViewsnew =  listViews.get(i).getListDiscuss();
                            dianzannum = listViews.get(i).getPraise();
                            tv_praise.setText(dianzannum);
                            MovieAdapter movieAdapter = new MovieAdapter(VideoDetailsActivity.this,listViewsnew);
                            lv.setAdapter(movieAdapter);
                        }
                    }
                }

            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("scan", "获取圈子失败" + "onFailure = \n" + t.toString());
            }
        });
    }

    public void requestpiaise() {

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
        Call<String> call = request.praisemovie(movieid);

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


    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus){
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                if(isFocus)  {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    commentLinear.setVisibility(View.VISIBLE);
                }else{
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(),0);
                    commentLinear.setVisibility(View.GONE);
                }
            }
        }, 100);
    }

    public void requestcomment() {

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
        Call<String> call = request.addmovie(mainsignup_userid,positionid,commentEdittext);

        // 发送网络请求(异步)
        call.enqueue(new Callback<String>() {
            // 请求成功时回调
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.v("pinglun", "发表评论成功" + "response.message() = " + response.message() + "\n" +
                        "response.body() = " + response.body());
                request();

            }

            // 请求失败时回调
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("pinglun", "发表评论失败" + "onFailure = \n" + t.toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}