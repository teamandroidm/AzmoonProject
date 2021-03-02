package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Data.OnResult;
import com.example.azmoonproject.Engine.MyReceiver;
import com.example.azmoonproject.Engine.RecyclerAdapter.RecyclerViewAdapter;
import com.example.azmoonproject.Engine.RecyclerAdapter.RecyclerViewMethod;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Fields;
import com.example.azmoonproject.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class FieldActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final Data date = new Data();
    Dialog dialog, exitDialog;
    Dialog logOutDialog;
    Button logOutDialogBtnNo, logOutDialogBtnYes;
    boolean doubleBackPress = false;
    Data data;
    Utils utils;
    ArrayList<Fields> fieldArrayList = new ArrayList<>();
    MyReceiver myReceiver;
    private Button exitDialogBtnNo, exitDialogBtnYes, custom_dialog_button_ok;
    private Toolbar toolbar;
    private NavigationView activity_field_navigation_view;
    private DrawerLayout drawerLayout;
    private ImageView activity_field_menu_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        data = new Data(FieldActivity.this);
        dialog = new Dialog(FieldActivity.this);
        exitDialog = new Dialog(FieldActivity.this);
        dialog.setContentView(R.layout.custom_dialog_field);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.setContentView(R.layout.custom_dialog_exit);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        utils = new Utils(getApplicationContext(), FieldActivity.this);
        setUpView();
        setSupportActionBar(toolbar);
        setTitle(null);
        final Utils utils = new Utils(getApplicationContext(), FieldActivity.this);
        // fieldItemList();
        activity_field_navigation_view.bringToFront();
        activity_field_navigation_view.setNavigationItemSelectedListener(FieldActivity.this);
        setOnClickImageView();
        data.sendRequestByPostMethodField(new OnResult() {
            @Override
            public void success(Object... objects) {
                fieldArrayList = (ArrayList<Fields>) objects[0];
                Log.i("DDDD", "e " + fieldArrayList.size());
                setRecyclerViewField(utils);
            }
        });
    }

    private void setOnClickImageView() {
        activity_field_menu_img.setOnClickListener(view -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else drawerLayout.openDrawer(GravityCompat.START);
        });
    }


    private void setUpView() {
        custom_dialog_button_ok = dialog.findViewById(R.id.custom_dialog_button_ok);
        exitDialogBtnNo = exitDialog.findViewById(R.id.custom_dialog_exit_btn_no);
        exitDialogBtnYes = exitDialog.findViewById(R.id.custom_dialog_exit_btn_yes);
        toolbar = findViewById(R.id.activity_field_toolbar);
        activity_field_navigation_view = findViewById(R.id.activity_field_navigation_view);
        drawerLayout = findViewById(R.id.main_drawer);
        activity_field_menu_img = findViewById(R.id.activity_field_menu_img);
        logOutDialog = new Dialog(FieldActivity.this);
        logOutDialog.setContentView(R.layout.custom_dialog_logout);
        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialogBtnNo = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_no);
        logOutDialogBtnYes = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_yes);
        myReceiver = new MyReceiver();
    }

    private void showCustomDialod() {
        dialog.show();

    }

    private void setRecyclerViewField(final Utils utils) {
        utils.addRecyclerView(R.id.activity_field_recyclerview, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(), R.layout.activity_field_list_item, fieldArrayList.size(), new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, final int position, final View itemView) {
                        ((TextView) itemView.findViewById(R.id.activity_field_text_name)).setText(fieldArrayList.get(position).getFeildName() + "");
                        // final LinearLayout activity_field_layout= itemView.findViewById(R.id.activity_field_layout);
                        Picasso.get().load(new File(fieldArrayList.get(position).getImageName())).error(R.drawable.logocourses).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_field_image_courses));
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int x = (int) utils.getSharedPreferences("fieldId", 0);
                                if (x == fieldArrayList.get(position).getFeildId()) {
                                    //   utils.setSharedPreferences("fieldId", ((fieldArrayList.get(position).getFeildId())));
                                    Intent intent=new Intent(FieldActivity.this,CoursesActivity.class);
                                    startActivity(intent);
                                  //  utils.goTo(CoursesActivity.class);

                                } else {
                                    showCustomDialod();
                                    //    activity_field_layout.setBackgroundColor(R.drawable.background_list_feild_purpel);
                                    custom_dialog_button_ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }));

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackPress) {

                setDialogExit();

                return;
            }
            this.doubleBackPress = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPress = false;
                }
            }, 2000);
        }
    }


    //region dialog exit
    private void setDialogExit() {
        exitDialog.setCancelable(false);
        exitDialog.show();
        exitDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        exitDialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
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
                Intent intent = new Intent(FieldActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.item_logout:
                setDialogLogOut();

                break;
            case R.id.item_courses:
                Intent intent1=new Intent(FieldActivity.this,CoursesActivity.class);
                startActivity(intent1);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //endregion
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }
}
