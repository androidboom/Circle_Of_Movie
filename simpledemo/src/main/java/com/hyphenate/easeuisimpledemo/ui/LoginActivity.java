package com.hyphenate.easeuisimpledemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeuisimpledemo.R;
import com.hyphenate.exceptions.HyphenateException;

public class LoginActivity extends EaseBaseActivity{
    private EditText usernameView;
    private EditText pwdView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if(EMClient.getInstance().isLoggedInBefore()){
            //enter to main activity directly if you logged in before.
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        
        setContentView(R.layout.activity_login);
        usernameView = (EditText) findViewById(R.id.et_username);
        pwdView = (EditText) findViewById(R.id.et_password);
        Button loginBtn = (Button) findViewById(R.id.btn_login);
        Button btn_logup = (Button) findViewById(R.id.btn_logup);
        
        loginBtn.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //login
                EMClient.getInstance().login(usernameView.getText().toString(), pwdView.getText().toString(), new EMCallBack() {
                    
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                    
                    @Override
                    public void onProgress(int progress, String status) {
                        
                    }
                    
                    @Override
                    public void onError(int code, String error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "login failed", 0).show();
                            }
                        });
                    }
                });
            }
        });

        btn_logup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(usernameView.getText().toString().trim(),
                                    pwdView.getText().toString().trim());
                            Log.e("sign","IM注册成功");
                            EMClient.getInstance().logout(true);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            Log.e("sign","IM注册失败" + e.getErrorCode() + e.getMessage());
                        }
                    }
                }).start();
            }
        });

    }

}
