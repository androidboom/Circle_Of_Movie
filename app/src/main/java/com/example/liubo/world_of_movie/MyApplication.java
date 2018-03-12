package com.example.liubo.world_of_movie;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

/**
 * Created by Liubo on 2018/3/7.
 */

public class MyApplication extends Application {

    //private static final String VALUE = "http://10.7.82.115:8080/springmvc/";
    private static final String VALUE = "http://192.168.31.215:8080/springmvc/";
    private String value;

    @Override
    public void onCreate() {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量

        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().setDebugMode(true);
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
