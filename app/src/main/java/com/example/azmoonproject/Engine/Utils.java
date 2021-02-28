package com.example.azmoonproject.Engine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Engine.RecyclerAdapter.RecyclerViewAdapter;
import com.example.azmoonproject.Model.Levels;

import java.text.DecimalFormat;


public class Utils {
    private Context context;
    private Activity activity;


    public Utils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Utils(Context context) {
        this.context = context;
    }


    public String farsiNumberConvert(String number) {
        String[][] mChar = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}

        };
        for (String[] num : mChar) {
            number = number.replace(num[0], num[1]);
        }
        return number;
    }

    public Object getSharedPreferences(String key, Object _default) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("Azmoon", Context.MODE_PRIVATE);
        if (_default instanceof String) {
            return sharedpreferences.getString(key, (String) _default);
        } else if (_default instanceof Boolean) {
            return sharedpreferences.getBoolean(key, (Boolean) _default);
        } else if (_default instanceof Float) {
            return sharedpreferences.getFloat(key, (Float) _default);
        } else if (_default instanceof Integer) {
            return sharedpreferences.getInt(key, (Integer) _default);
        } else if (_default instanceof Long) {
            return sharedpreferences.getLong(key, (Long) _default);
        }else if (_default instanceof Byte) {
            return sharedpreferences.getInt(key, (Byte) _default);
        }
        return null;
    }

    public void setSharedPreferences(String key, Object data) {
        SharedPreferences.Editor editor = context.getSharedPreferences("Azmoon", Context.MODE_PRIVATE).edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else if (data instanceof Byte) {
            editor.putInt(key, (Byte) data);
        }
        editor.apply();
    }

    //  Activity => Context
    public void goTo(Class _class) {
        activity.startActivity(new Intent(context, _class));
        activity.finish();
    }

    public void addRecyclerView(RecyclerView rcv1, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter) {
//        RecyclerView rcv1 = activity.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public void addRecyclerView(int recyclerViewId, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter) {
        RecyclerView rcv1 = activity.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public void addRecyclerView(int recyclerViewId, RecyclerView.LayoutManager layoutManager, RecyclerViewAdapter adapter, View view) {
        RecyclerView rcv1 = view.findViewById(recyclerViewId);
        rcv1.setLayoutManager(layoutManager);
        rcv1.setAdapter(adapter);
    }

    public String splitDigits(Object number) {
        return new DecimalFormat("###,###,###").format(number);
    }

    public String toPersianNumber4(String english) {
        final char[] chars = english.toCharArray();
        for (short i = 0; i < (short) chars.length; i++)
            if (chars[i] <= '9' && chars[i] >= '0')
                chars[i] = (char) (chars[i] + 1728);
        return new String(chars);
    }

}
