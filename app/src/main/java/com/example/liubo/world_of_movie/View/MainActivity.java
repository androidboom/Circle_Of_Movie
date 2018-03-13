package com.example.liubo.world_of_movie.View;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.liubo.world_of_movie.BaseView.MyAlarmView;

import com.example.liubo.world_of_movie.IM.ChatActivity;
import com.example.liubo.world_of_movie.R;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout main_surround;
    private RelativeLayout main_content;
    private RelativeLayout main_icon;
    private RelativeLayout main_circle;
    private RelativeLayout main_me;
    private LinearLayout mian_content_container;
    private FragmentContent fragmentconcent;
    private FragmentSurround fragmentsurround;
    private FragmentMain fragmentmain;
    private FragmentCircle fragmentcircle;
    private FragmentMe fragmentme;
    private ImageView main_content_view;
    private ImageView main_surround_view;
    private ImageView main_icon_movie;
    private ImageView main_circle_view;
    private ImageView main_me_view;
    private TextView main_content_text;
    private TextView main_surround_text;
    private TextView main_icon_text;
    private TextView main_circle_text;
    private TextView main_me_text;
    private MyAlarmView swvWave;
    private FragmentManager fragmentManager;
    private String signup_userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mian_content_container,fragmentmain,"f3");
        main_icon_movie.setImageResource(R.drawable.tab_main_h2x);
        swvWave.start();
        swvWave.setVisibility(View.VISIBLE);
        main_icon_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
        setListener();
    }

    public void initView() {
        Intent intent = new Intent();
        signup_userid = intent.getStringExtra("LOGIN");
        swvWave = (MyAlarmView) findViewById(R.id.shuibo);
        swvWave.handleDelay(85);
        //fragment显示窗口
        mian_content_container = (LinearLayout)findViewById(R.id.mian_content_container);
        //底部控件，包含图片和汉字
        main_content = (RelativeLayout)findViewById(R.id.main_content);
        main_surround = (RelativeLayout)findViewById(R.id.main_surround);
        main_icon = (RelativeLayout)findViewById(R.id.main_icon);
        main_circle = (RelativeLayout)findViewById(R.id.main_circle);
        main_me = (RelativeLayout)findViewById(R.id.main_me);
        //底部控件，图片
        main_content_view = (ImageView)findViewById(R.id.main_content_view);
        main_surround_view = (ImageView)findViewById(R.id.main_surround_view);
        main_icon_movie = (ImageView)findViewById(R.id.main_icon_movie);
        main_circle_view = (ImageView)findViewById(R.id.main_circle_view);
        main_me_view = (ImageView)findViewById(R.id.main_me_view);
        //底部控件，图片
        main_content_text = (TextView)findViewById(R.id.main_content_text);
        main_surround_text = (TextView)findViewById(R.id.main_surround_text);
        main_icon_text = (TextView)findViewById(R.id.main_icon_text);
        main_circle_text = (TextView)findViewById(R.id.main_circle_text);
        main_me_text = (TextView)findViewById(R.id.main_me_text);
        //初始化各个fragment和fragment事物
        fragmentconcent = new FragmentContent();
        fragmentsurround = new FragmentSurround();
        fragmentcircle = new FragmentCircle();
        fragmentme = new FragmentMe();
        fragmentmain = new FragmentMain();

        Bundle bundle = new Bundle();
        bundle.putString("signup_userid",signup_userid);

        fragmentconcent.setArguments(bundle);
        fragmentsurround.setArguments(bundle);
        fragmentcircle.setArguments(bundle);
        fragmentme.setArguments(bundle);
        fragmentmain.setArguments(bundle);
    }

    public void setListener(){
        main_content.setOnClickListener(MyListener);
        main_surround.setOnClickListener(MyListener);
        main_icon.setOnClickListener(MyListener);
        main_circle.setOnClickListener(MyListener);
        main_me.setOnClickListener(MyListener);
        fragmentconcent.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MainActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            resetButton();
            switch (v.getId()){
                case R.id.main_content:
                    fragmentTransaction.replace(R.id.mian_content_container,fragmentconcent,"f1");
                    main_content_view.setImageResource(R.drawable.tab_chat_h2x);
                    main_content_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
                    break;
                case R.id.main_surround:
                    fragmentTransaction.replace(R.id.mian_content_container,fragmentsurround,"f2");
                    main_surround_view.setImageResource(R.drawable.tab_contact_h2x);
                    main_surround_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
                    break;
                case R.id.main_icon:
                    fragmentTransaction.replace(R.id.mian_content_container,fragmentmain,"f3");
                    main_icon_movie.setImageResource(R.drawable.tab_main_h2x);
                    swvWave.start();
                    swvWave.setVisibility(View.VISIBLE);
                    main_icon_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
                    break;
                case R.id.main_circle:
                    fragmentTransaction.replace(R.id.mian_content_container,fragmentcircle,"f4");
                    main_circle_view.setImageResource(R.drawable.tab_server_h2x);
                    main_circle_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
                    break;
                case R.id.main_me:
                    fragmentTransaction.replace(R.id.mian_content_container,fragmentme,"f5");
                    main_me_view.setImageResource(R.drawable.tab_me_h2x);
                    main_me_text.setTextColor(getResources().getColor(R.color.ssf_main_color));
                    break;
            }
            fragmentTransaction.commit();
        }
    };

    private void resetButton() {
        swvWave.stop();
        swvWave.setVisibility(View.GONE);

        main_content_view.setImageResource(R.drawable.tab_chat_n2x);
        main_content_text.setTextColor(getResources().getColor(R.color.fragment_text));

        main_surround_view.setImageResource(R.drawable.tab_contact_n2x);
        main_surround_text.setTextColor(getResources().getColor(R.color.fragment_text));

        main_icon_movie.setImageResource(R.drawable.tab_main_n2x);
        main_icon_text.setTextColor(getResources().getColor(R.color.fragment_text));

        main_circle_view.setImageResource(R.drawable.tab_server_n2x);
        main_circle_text.setTextColor(getResources().getColor(R.color.fragment_text));

        main_me_view.setImageResource(R.drawable.tab_me_n2x);
        main_me_text.setTextColor(getResources().getColor(R.color.fragment_text));
    }


}
