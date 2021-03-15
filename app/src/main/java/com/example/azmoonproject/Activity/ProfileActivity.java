package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Data.OnResult;
import com.example.azmoonproject.Engine.DateConverter;
import com.example.azmoonproject.Engine.MyReceiver;
import com.example.azmoonproject.Engine.RecyclerAdapter.RecyclerViewAdapter;
import com.example.azmoonproject.Engine.RecyclerAdapter.RecyclerViewMethod;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Factors;
import com.example.azmoonproject.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Utils utils;
    TextView txtNameFamily;
    RecyclerView rcv1;
    ImageView imgBack;
    LinearLayout emptyFactorLa;
    MaterialToolbar materialToolbar;
    Dialog changePassDialog, logOutDialog,dialogAbout,dialogCall;
    TextInputEditText edtPass, edtNewPass, edtNewPassAgain;
    TextInputLayout edtLaPass, edtLaNewPass, edtLaNewPassAgain;
    Button changePassBtn, changePassDialogBtnNo, changePassDialogBtnYes,custom_dialog_button_ok3, logOutDialogBtnNo, logOutDialogBtnYes,custom_dialog_button_ok2;
    Data data;
    MyReceiver myReceiver;
    ArrayList<Factors> factors = new ArrayList<>();
    // for convert miladi date to shamsi date
    DateConverter dateConverter = new DateConverter();
    private DrawerLayout activity_profile_drawer;
    private NavigationView activity_profile_navigation_view;
    private ImageView activity_profile_menu_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        changePassDialog = new Dialog(ProfileActivity.this);
        changePassDialog.setContentView(R.layout.custom_dialog_acticity_profile_change_pass);
        changePassDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init();
        data = new Data(ProfileActivity.this);
        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogChangePass();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data.getFactors(new OnResult() {
            @Override
            public void success(Object... objects) {
                factors = (ArrayList<Factors>) objects[0];
                Log.i("taggg", factors.size() + "");
                isEmptyFactors();
            }

        });


        // fill user name and family
        data.getNameFamily(new OnResult() {
            @Override
            public void success(Object... objects) {
                txtNameFamily.setText((String) objects[0]);
            }
        });


        setSupportActionBar(materialToolbar);
        setTitle(null);
        activity_profile_navigation_view.bringToFront();
        activity_profile_navigation_view.setNavigationItemSelectedListener(ProfileActivity.this);
        setOnClickImageView();
    }

    private void setOnClickImageView() {
        activity_profile_menu_img.setOnClickListener(view -> {
            if (activity_profile_drawer.isDrawerVisible(GravityCompat.START)) {
                activity_profile_drawer.closeDrawer(GravityCompat.START);
            } else activity_profile_drawer.openDrawer(GravityCompat.START);
        });
    }


    @Override
    public void onBackPressed() {
        finish();
//        if (activity_profile_drawer.isDrawerVisible(GravityCompat.START)) {
//            activity_profile_drawer.closeDrawer(GravityCompat.START);
//        } else
//            super.onBackPressed();
    }


    //region dialog log out
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
    //endregion


    private void setDialogChangePass() {
        changePassDialog.setCancelable(false);
        changePassDialog.show();
        // edit texts set empty
        edtNewPassAgain.setText("");
        edtNewPass.setText("");
        edtNewPass.setText("");

        changePassDialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassDialog.dismiss();
            }
        });

        //  region change Pass with volley
        changePassDialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtNewPass.getText().toString().equals(edtNewPassAgain.getText().toString()) == false) {
                    edtLaNewPass.setError("رمزهای وارد شده با هم برابر نیستند");
                    edtLaNewPassAgain.setError("رمزهای وارد شده با هم برابر نیستند");

                } else {
                    edtLaNewPass.setError("");
                    edtLaNewPassAgain.setError("");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            data.NewPassword(edtPass.getText().toString(), edtNewPass.getText().toString(), new OnResult() {
                                @Override
                                public void success(Object... objects) {
                                    if ((boolean) objects[0])
                                        changePassDialog.dismiss();
                                    else
                                        edtLaPass.setError("رمزعبور اشتباه است!");

                                }
                            });

                        }
                    });
                    thread.start();
                }

            }
        });
        // endregion

    }


    private void isEmptyFactors() {
        Log.i("tag111", factors.size() + "");
        if (factors.size() == 0) {
            rcv1.setVisibility(View.GONE);
            emptyFactorLa.setVisibility(View.VISIBLE);
        } else {
            emptyFactorLa.setVisibility(View.GONE);
            rcv1.setVisibility(View.VISIBLE);
            setRecyclerViewFactor(utils);

        }

    }

    private void setRecyclerViewFactor(final Utils utils) {

        utils.addRecyclerView(rcv1, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(),
                        R.layout.rcv_item_activity_profile, factors.size(), new RecyclerViewMethod() {
                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, int position, View itemView) {
                        TextView courseName = itemView.findViewById(R.id.rcv_item_activity_profile_txt_course_name);
                        TextView price = itemView.findViewById(R.id.rcv_item_activity_profile_txt_price);
                        TextView date = itemView.findViewById(R.id.rcv_item_activity_profile_txt_date);
                        TextView validityDate = itemView.findViewById(R.id.rcv_item_activity_profile_txt_validity_date);

                        courseName.setText(factors.get(position).getTermName());
                        price.setText(utils.toPersianNumber4(utils.splitDigits(factors.get(position).getPrice())));
                        Log.i("date", factors.get(position).getFinallyDate() + "");
                        dateConverter.gregorianToPersian(Integer.parseInt(factors.get(position).getFinallyDate().substring(0, 4)), Integer.parseInt(factors.get(position).getFinallyDate().substring(5, 7)), Integer.parseInt(factors.get(position).getFinallyDate().substring(8, 10)));
                        date.setText(utils.toPersianNumber4(dateConverter.toString()));
                        // add day in finally date
                        Calendar calendar = Calendar.getInstance();
                        try {
                            Date format = new SimpleDateFormat("yyyy-MM-dd").parse(factors.get(position).getFinallyDate());
                            calendar.setTime(format);
                            calendar.add(Calendar.DATE, factors.get(position).getValidateTime());
                            dateConverter.gregorianToPersian(Integer.parseInt(new SimpleDateFormat("yyyy").format(calendar.getTime())), Integer.parseInt(new SimpleDateFormat("MM").format(calendar.getTime())), Integer.parseInt(new SimpleDateFormat("dd").format(calendar.getTime())));

                            validityDate.setText(utils.toPersianNumber4(dateConverter.toString()));

                        } catch (ParseException e) {
                            validityDate.setText(" " + e);

                        }


                    }
                }));

    }

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

    private void init() {
        rcv1 = findViewById(R.id.activity_profile_rcv_factor);
        materialToolbar = findViewById(R.id.activity_profile_toolbar);
        txtNameFamily = findViewById(R.id.activity_profile_txt_user_name);
        changePassBtn = findViewById(R.id.activity_profile_btn_change_pass);
        changePassDialogBtnNo = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_btn_no);
        changePassDialogBtnYes = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_btn_yes);
        logOutDialog = new Dialog(ProfileActivity.this);
        logOutDialog.setContentView(R.layout.custom_dialog_logout);
        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialogBtnNo = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_no);
        logOutDialogBtnYes = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_yes);
        edtNewPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_newpass);
        edtNewPassAgain = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_newpass_again);
        edtPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edt_pass);
        edtLaPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_pass);
        edtLaNewPass = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_newpass);
        edtLaNewPassAgain = changePassDialog.findViewById(R.id.custom_dialog_activity_profile_change_pass_edtla_newpass_again);
        imgBack = findViewById(R.id.activity_profile_toolbar_img_back);
        emptyFactorLa = findViewById(R.id.activity_profile_empty_layout);
        activity_profile_drawer = findViewById(R.id.activity_profile_drawer);
        activity_profile_navigation_view = findViewById(R.id.activity_profile_navigation_view);
        activity_profile_menu_img = findViewById(R.id.activity_profile_menu_img);
        utils = new Utils(getApplicationContext(), ProfileActivity.this);
        dialogAbout = new Dialog(ProfileActivity.this);
        dialogAbout.setContentView(R.layout.custom_dialog_about);
        dialogAbout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        custom_dialog_button_ok2=dialogAbout.findViewById(R.id.custom_dialog_button_ok2);
        dialogCall = new Dialog(ProfileActivity.this);
        dialogCall.setContentView(R.layout.custom_dialog_call);
        dialogCall.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        custom_dialog_button_ok3 = dialogCall.findViewById(R.id.custom_dialog_button_ok3);

        myReceiver = new MyReceiver();

    }
    private void setDialogAbout() {

        dialogAbout.setCancelable(false);
        dialogAbout.show();
        custom_dialog_button_ok2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAbout.dismiss();
            }
        });
    }
    private void setDialogCall() {

        dialogCall.setCancelable(false);
        dialogCall.show();
        custom_dialog_button_ok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCall.dismiss();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_home1:
                utils.goTo(FieldActivity.class);
                activity_profile_drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.item_logout:
                setDialogLogOut();

                break;
            case R.id.item_courses:
                utils.goTo(CoursesActivity.class);
                activity_profile_drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.item_about_academy:
                setDialogAbout();

                break;
            case R.id.tem_contact_us:
                setDialogCall();

                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
