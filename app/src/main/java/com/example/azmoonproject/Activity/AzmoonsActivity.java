package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Engine.MyReceiver;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Levels;
import com.example.azmoonproject.MyAdapter;
import com.example.azmoonproject.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class AzmoonsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar tlb1;
    ImageView imgBack;
    ImageView img1;
    private ImageView activity_azmoons_img;
    private DrawerLayout activity_azmoons_drawer;
    private NavigationView activity_azmoons_navigation_view;
    TextView txt1;
    Dialog logOutDialog;
    Button logOutDialogBtnNo, logOutDialogBtnYes;
    RecyclerView rcl;
    ArrayList<Levels> questionLevelItemArrayList;
    Levels levelItem;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azmoons);

        utils=new Utils(getApplicationContext(), AzmoonsActivity.this);
        // init
        setInit();

        // Tool Bar
        setSupportActionBar(tlb1);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setLogo(null);

        // List
        questionLevelItemArrayList = new ArrayList<>();
        fillList();
        MyAdapter myAdapter = new MyAdapter(AzmoonsActivity.this, R.layout.item_question_level, questionLevelItemArrayList, AzmoonsActivity.this);
        rcl.setLayoutManager(new GridLayoutManager(this, 2));
        rcl.setAdapter(myAdapter);
        activity_azmoons_navigation_view.bringToFront();
        activity_azmoons_navigation_view.setNavigationItemSelectedListener(AzmoonsActivity.this);
        setOnClickImageView();


        // Image On Click
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setInit() {
        tlb1 = findViewById(R.id.activity_question_tlb1);
        imgBack = findViewById(R.id.activity_question_img_back);
        img1 = findViewById(R.id.activity_question_img1);
        txt1 = findViewById(R.id.activity_question_txt1);
        rcl = findViewById(R.id.activity_question_rcl1);
        activity_azmoons_drawer = findViewById(R.id.activity_azmoons_drawer);
        activity_azmoons_navigation_view = findViewById(R.id.activity_azmoons_navigation_view);
        activity_azmoons_img = findViewById(R.id.activity_azmoons_img);
        logOutDialog = new Dialog(AzmoonsActivity.this);
        logOutDialog.setContentView(R.layout.custom_dialog_logout);
        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialogBtnNo = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_no);
        logOutDialogBtnYes = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_yes);
    }

    private void fillList() {
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)3,"آزمون 1", R.drawable.checkmark_52px, Color.argb(255, 255, 118, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)3,"آزمون 2", R.drawable.checkmark_52px, Color.argb(255, 255, 118, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)3,"آزمون 3", R.drawable.checkmark_52px, Color.argb(255, 255, 118, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)3,"آزمون 4", R.drawable.checkmark_52px, Color.argb(255, 255, 118, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)1,"آزمون 5", R.drawable.unlockicon,Color.argb(255, 0, 0, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)2,"آزمون 6", R.drawable.lockicon,Color.argb(130, 0, 0, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)2,"آزمون 7", R.drawable.lockicon,Color.argb(130, 0, 0, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)2,"آزمون 8", R.drawable.lockicon,Color.argb(130, 0, 0, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)2,"آزمون 9", R.drawable.lockicon,Color.argb(130, 0, 0, 0)));
//        questionLevelItemArrayList.add(new QuestionLevelItem((byte)2,"آزمون 10", R.drawable.lockicon,Color.argb(130, 0, 0, 0)));

        Data data = new Data();
        questionLevelItemArrayList = data.getLevel();
    }
    private void setOnClickImageView() {
        activity_azmoons_img.setOnClickListener(view -> {
            if (activity_azmoons_drawer.isDrawerVisible(GravityCompat.START)) {
                activity_azmoons_drawer.closeDrawer(GravityCompat.START);
            } else activity_azmoons_drawer.openDrawer(GravityCompat.START);
        });
    }
    private void setDialogLogOut() {

        logOutDialog.setCancelable(false);
        logOutDialog.show();
        logOutDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutDialog.dismiss();
            }
        });
        logOutDialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.setSharedPreferences("isLogged", false);
                utils.goTo(LoginActivity.class);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_account:
                Intent intent = new Intent(AzmoonsActivity.this, ProfileActivity.class);
                startActivity(intent);
                activity_azmoons_drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_logout:
                setDialogLogOut();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}