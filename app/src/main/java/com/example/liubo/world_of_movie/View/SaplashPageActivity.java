package com.example.liubo.world_of_movie.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liubo.world_of_movie.BaseView.CountDownProgressView;
import com.example.liubo.world_of_movie.Login.LoginActivity;
import com.example.liubo.world_of_movie.R;
import com.example.liubo.world_of_movie.Utils.LoginSharedPreferences;

/**
 * Created by Liubo on 2018/4/27.
 */

public class SaplashPageActivity extends AppCompatActivity {
    private CountDownProgressView progressView;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saplash_pageactivity);
        initview();


    }

    private void initview() {
        progressView = (CountDownProgressView) findViewById(R.id.saplashTimeBtn);

        if (ContextCompat.checkSelfPermission(SaplashPageActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请的权限
            ActivityCompat.requestPermissions(SaplashPageActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //如果有权限了需要处理的逻辑
            login = LoginSharedPreferences.getString(this, "login", "");
        }

        init();

    }

    /**
     *申请权限的回调
     */
    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //获取了权限后需要处理的逻辑
                    login = LoginSharedPreferences.getString(this, "login", "");
                } else {
                    Toast.makeText(this, "你拒绝了这个权限", Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }

    private void init() {
        progressView.start();

        progressView.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                if (progress == 0) {
                    goAct();
                }
            }
        });
        progressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAct();
            }
        });
    }

    private void goAct() {
        if (!TextUtils.isEmpty(login)){
            if (!login.equals("true")){
                startActivity(new Intent(SaplashPageActivity.this,LoginActivity.class));
            }else {
                startActivity(new Intent(SaplashPageActivity.this,MainActivity.class));
            }
        }else {
            startActivity(new Intent(SaplashPageActivity.this,LoginActivity.class));
        }
        progressView.stop();
        finish();
    }
}
