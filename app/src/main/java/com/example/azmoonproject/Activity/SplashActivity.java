package com.example.azmoonproject.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.R;

public class SplashActivity extends AppCompatActivity {
    private TextView txtLoading;
    private Handler handler;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        utils = new Utils(SplashActivity.this,SplashActivity.this);
        init();
        threadAnimSplash();
        handler = new Handler();
        handler.postDelayed(() -> {
            if ((Boolean) utils.getSharedPreferences("isLogged", false)) {
                utils.goTo(FieldActivity.class);//null=صفحه اصلی
            } else {
                utils.goTo( LoginActivity.class);
            }
        }, 3000);
    }

    private void threadAnimSplash() {
        Thread thread = new Thread(new Runnable() {
            String str = "";
            byte level = 1;
            boolean flag = true;

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                        if (str.length() < 3 && flag == true) {
                            str += ".";
                            if (str.length() == 3)
                                level++;
                        } else {
                            flag = false;
                            str = str.substring(1);
                            if (str.length() == 0) {
                                flag = true;
                                level++;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(() -> {
                        if (level == 4)
                            level = 0;
                        if (level <= 1)
                            txtLoading.setGravity(Gravity.END);
                        if (level >= 2)
                            txtLoading.setGravity(Gravity.START);
                        txtLoading.setText(str);
                    });
                }
            }
        });
        thread.start();
    }

    private void init() {
        txtLoading = findViewById(R.id.activity_splash_txt2);
    }

}


