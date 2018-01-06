package com.example.liubo.world_of_movie.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.View.MainActivity;


/**
 * Created by Liubo on 2017/12/31.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText signup_username;
    private EditText signup_pw;
    private Button signup_btn;
    private Button signin_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setListener();
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
