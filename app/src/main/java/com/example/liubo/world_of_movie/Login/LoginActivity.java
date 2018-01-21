package com.example.liubo.world_of_movie.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;


/**
 * Created by Liubo on 2017/12/31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText signup_username;
    private EditText signup_pw;
    private Button signup_btn;
    private TextView signin_btn;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        setListener();
    }

    public void init(){
        signup_btn = (Button) findViewById(R.id.signup_btn);
        signin_btn = (TextView) findViewById(R.id.signin_btn);
        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("登录");
    }

    public void setListener(){
        signup_btn.setOnClickListener(MyListener);
        signin_btn.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn:
                case R.id.signin_btn:
            }
        }
    };

}
