package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Adapter.CircleAdapter;
import com.example.liubo.world_of_movie.Circle.SubmitCircle;
import com.example.liubo.world_of_movie.IM.ChatActivity;
import com.example.liubo.world_of_movie.Login.ForgetPassword;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.Login.Sign;
import com.example.liubo.world_of_movie.R;
import com.hyphenate.easeui.EaseConstant;

import org.json.JSONArray;
import org.w3c.dom.Text;

/**
 * Created by Liubo on 2017/12/27.
 */

public class FragmentCircle extends Fragment {
    private View view;
    private ListView lv;
    private JSONArray Data;
    private Button test;
    private EditText testa;
    private String mainsignup_userid;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_circle,null);
        init();
        setListener();
        return view;
    }

    public void init(){
        lv = (ListView)view.findViewById(R.id.lv);
        test = (Button) view.findViewById(R.id.test);
        testa = (EditText)view.findViewById(R.id.aaa);
        CircleAdapter circleAdapter = new CircleAdapter(getActivity(),Data);
        mainsignup_userid = getArguments().getString("signup_userid");
        right_add = (ImageView)view.findViewById(R.id.right_add);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        //left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("圈子");
        //lv.setAdapter(circleAdapter);
    }

    public void setListener(){
        test.setOnClickListener(MyListener);
        right_add.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.test:
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, testa.getText().toString()));
                    break;
                case R.id.right_add:
                    View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow, null);
                    TextView poptext = (TextView) popupView.findViewById(R.id.poptext);
                    // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
                    PopupWindow window = new PopupWindow(popupView, 300, 150);
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
                            intent.setClass(getActivity(), SubmitCircle.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("signup_userid",mainsignup_userid);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

}
