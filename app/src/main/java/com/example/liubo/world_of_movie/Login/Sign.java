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
 * Created by Liubo on 2018/1/21.
 */

public class Sign extends AppCompatActivity {
    private EditText signup_username;
    private EditText signup_pw;
    private Button signin_btn;
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_layout);
        init();
        setListener();
    }

    public void init(){
        signin_btn = (Button) findViewById(R.id.signin_btn);
        right_add = (ImageView)findViewById(R.id.right_add);
        signup_username = (EditText)findViewById(R.id.signup_username);
        signup_pw = (EditText)findViewById(R.id.signup_pw);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        left_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.title);
        title.setText("注册");
    }

    public void setListener(){
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