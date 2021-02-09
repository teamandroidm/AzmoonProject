package com.example.azmoonproject.Data;

import android.app.Application;
import android.content.Context;

import com.example.azmoonproject.Model.Users;

public class G extends Application {
    public  static Users user=new Users();
    public  static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
