package com.example.liubo.world_of_movie.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.Adapter.CircleAdapter;
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
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)view.findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)view.findViewById(R.id.title);
        title.setText("圈子");
        //lv.setAdapter(circleAdapter);
    }

    public void setListener(){
        test.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.test:
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, testa.getText().toString()));
                    break;
            }
        }
    };

}
