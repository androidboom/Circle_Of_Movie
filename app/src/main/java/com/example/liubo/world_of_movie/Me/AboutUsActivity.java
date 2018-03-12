package com.example.liubo.world_of_movie.Me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liubo.world_of_movie.R;

/**
 * Created by Liubo on 2018/3/12.
 */

public class AboutUsActivity extends AppCompatActivity {
    private ImageView right_add;
    private ImageView left_back;
    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus_layout);
        init();
        setListener();
    }

    public void init(){
        right_add = (ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        left_back = (ImageView)findViewById(R.id.left_back);
        title = (TextView)findViewById(R.id.title);
        title.setText("关于我们");
    }

    public void setListener(){
        left_back.setOnClickListener(MyListener);
    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.left_back:
                    finish();
                    break;
            }
        }
    };
}
