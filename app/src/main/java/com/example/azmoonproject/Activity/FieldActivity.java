package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FieldActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final Data date = new Data();
    Dialog dialog, exitDialog;
    Dialog logOutDialog;
    Button logOutDialogBtnNo, logOutDialogBtnYes;
    boolean doubleBackPress = false;
    Utils utils;
    ArrayList<Fields> fieldArrayList = new ArrayList<>();
    private Button exitDialogBtnNo, exitDialogBtnYes, custom_dialog_button_ok;
    private Toolbar toolbar;
    private NavigationView activity_field_navigation_view;
    private DrawerLayout drawerLayout;
    private ImageView activity_field_menu_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

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
        setRecyclerViewField(utils);
        fieldItemList();
        activity_field_navigation_view.bringToFront();
        activity_field_navigation_view.setNavigationItemSelectedListener(FieldActivity.this);
        setOnClickImageView();
    }

    private void setOnClickImageView() {
        activity_field_menu_img.setOnClickListener(view -> {
            if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    private void fieldItemList() {
        String computer = picPath(R.drawable.computer);
        String tourists = picPath(R.drawable.tourists);
        String accounting = picPath(R.drawable.accounting);

        fieldArrayList.add(new Fields((byte) 1, "کامپیوتر", computer, true));
        fieldArrayList.add(new Fields((byte) 2, "گردشگری", tourists, false));
        fieldArrayList.add(new Fields((byte) 3, "حسابداری", accounting, false));
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
    }

    private void showCustomDialod() {
        dialog.show();

    }

    private void setRecyclerViewField(final Utils utils) {
        utils.addRecyclerView(R.id.activity_field_recyclerview, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(), R.layout.activity_field_list_item, 3, new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, final int position, final View itemView) {
                        ((TextView) itemView.findViewById(R.id.activity_field_text_name)).setText(fieldArrayList.get(position).getFieldName() + "");
                        // final LinearLayout activity_field_layout= itemView.findViewById(R.id.activity_field_layout);
                        Picasso.get().load(new File(fieldArrayList.get(position).getImageName())).error(R.drawable.tourists).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_field_image_courses));

                        itemView.setOnClickListener(view -> date.sendIdField(1, fieldArrayList.get(position).getFieldId(), new OnResult() {
                            @Override
                            public void success(Object... objects) {
                                if ((Boolean) objects[0]) {
                                    Intent intent = new Intent(FieldActivity.this, CoursesActivity.class);
                                    intent.putExtra("fieldId", fieldArrayList.get(position).getFieldId());
                                    startActivity(intent);
                                    //utils.goTo(CoursesActivity.class,new PutExtra("fieldId",fieldArrayList.get(position).getFieldId()));

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

                        }));

                    }
                }));

    }

    public String picPath(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        File mfile1 = getExternalFilesDir(null);
        String filename = res + ".png";
        File mfile2 = new File(mfile1, filename);
        try {
            FileOutputStream fileOutputStream;
            fileOutputStream = new FileOutputStream(mfile2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = mfile1.getAbsolutePath() + "/" + filename;

        return path;
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
                finish();
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
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    //endregion

}
