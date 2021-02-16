package com.example.azmoonproject.Engine;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;

import com.example.azmoonproject.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyReceiver extends BroadcastReceiver {

    Handler handler = new Handler();
    Dialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(() -> {
            if (!isOnline() && dialog == null) {
                handler.post(() -> {
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.custom_dialog_notconnection);
                    dialog.setCancelable(false);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.findViewById(R.id.custom_dialog_button_ok).setOnClickListener(view ->
                            ((Activity) context).finishAffinity());
                    dialog.show();
                });
            }
        }).start();
    }

    public boolean isOnline() {
        try {
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(sockaddr, 2000);
            sock.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}