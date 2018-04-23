package com.example.liubo.world_of_movie;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

/**
 * Created by Liubo on 2018/3/7.
 */

public class MyApplication extends Application {

//    private static final String VALUE = "http://10.7.82.115:8080/springmvc/";
//    public static final String VALUE = "http://192.168.31.215:8080/springmvc/";
//    public static final String VALUE = "http://172.16.1.171:8080/springmvc/";
    public static final String VALUE = "http://118.89.240.98:8080/springmvc/";
    public String value;

    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
        options.setAutoLogin(false);
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().setDebugMode(true);
    }

}
