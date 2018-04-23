package com.example.liubo.world_of_movie.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class LoginSharedPreferences {

  public static SharedPreferences sp;
    private static String NAME = "circle";

    public static void putString(Context context, String key, String value) {
        if(sp==null){
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key,
                                   String defValue) {
        if(sp==null){
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if(sp==null){
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if(sp==null){
            sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }


}
