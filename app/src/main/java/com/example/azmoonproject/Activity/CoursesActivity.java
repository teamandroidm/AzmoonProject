package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.azmoonproject.Model.Terms;
import com.example.azmoonproject.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.io.File;
import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog dialog;
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    Button custom_dialog_button_courses_payment, custom_dialog_button_courses_cancel;
    TextView custom_dialog_text_courses_type, custom_dialog_text_courses_price;
    Toolbar toolbar;
    ImageView back;
    Data data;
    Dialog logOutDialog;
    Button logOutDialogBtnNo, logOutDialogBtnYes;
    Utils utils;
    MyReceiver myReceiver;
    int factorId = 0;
    int paymentId = 0;
    int price = 0;
    Boolean result = false;
    private NavigationView activity_courses_navigation_view;
    private DrawerLayout activity_courses_drawer;
    private ImageView activity_courses_menu_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_buy_courses);
        utils = new Utils(getApplicationContext(), CoursesActivity.this);
        setUpView();
        setSupportActionBar(toolbar);
        setTitle(null);
        final Utils utils = new Utils(getApplicationContext(), CoursesActivity.this);
        // termsItemList();
        activity_courses_navigation_view.bringToFront();
        activity_courses_navigation_view.setNavigationItemSelectedListener(CoursesActivity.this);
        data = new Data(CoursesActivity.this);
        data.sendRequestByPostMethodCourses(new OnResult() {
            @Override
            public void success(Object... objects) {
                termsArrayList = (ArrayList<Terms>) objects[0];
                setRecyclerViewCourses(utils);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursesActivity.this, FieldActivity.class);
                startActivity(intent);
            }
        });
        int x = (Integer) utils.getSharedPreferences("fieldId", 0);
        termsArrayList = fillTearm((byte) x);


        setOnClickImageView();
        //درگاه پرداخت
        Uri uri = getIntent().getData();

        ZarinPal.getPurchase(this).verificationPayment(uri, (isPaymentSuccess, refID, paymentRequest) -> {
            if (isPaymentSuccess) {// زمانی که از درگاه پرداخت برمیگردد و پرداخت انجام شده
//                    refID //شماره تراکنش
                data.sendRequestByPostMethodPaymentresult(true, paymentId, factorId, new OnResult() {
                    @Override
                    public void success(Object... objects) {
                        result = (Boolean) objects[0];
                    }
                });
            } else {// زمانی که از درگاه پرداخت برمیگردد و پرداخت انجام نشده
                Toast.makeText(CoursesActivity.this, "پرداخت انجام نشد!", Toast.LENGTH_SHORT).show();
            }
        });

        // ورودی تابع به این شکل باید باشید. قیمت بر حسب تومان و نوع long است پس بعد از قیمت حرف l را بگذارید.
        //myPayment(500l, "محصول آزمایشی");
    }

    private void setOnClickImageView() {
        activity_courses_menu_img.setOnClickListener(view -> {
            if (activity_courses_drawer.isDrawerVisible(GravityCompat.START)) {
                activity_courses_drawer.closeDrawer(GravityCompat.START);
            } else activity_courses_drawer.openDrawer(GravityCompat.START);
        });
    }

    private void setUpView() {
        custom_dialog_button_courses_payment = dialog.findViewById(R.id.custom_dialog_button_courses_payment);
        custom_dialog_button_courses_cancel = dialog.findViewById(R.id.custom_dialog_button_courses_cancel);
        custom_dialog_text_courses_type = dialog.findViewById(R.id.custom_dialog_text_courses_type);
        custom_dialog_text_courses_price = dialog.findViewById(R.id.custom_dialog_text_courses_price);
        activity_courses_navigation_view = findViewById(R.id.activity_courses_navigation_view);
        activity_courses_drawer = findViewById(R.id.activity_courses_drawer);
        activity_courses_menu_img = findViewById(R.id.activity_courses_menu_img);
        toolbar = findViewById(R.id.activity_courses_toolbar);
        back = findViewById(R.id.back);
        logOutDialog = new Dialog(CoursesActivity.this);
        logOutDialog.setContentView(R.layout.custom_dialog_logout);
        logOutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logOutDialogBtnNo = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_no);
        logOutDialogBtnYes = logOutDialog.findViewById(R.id.custom_dialog_logout_btn_yes);
        myReceiver = new MyReceiver();
    }

    private void showCustomDialod() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void setRecyclerViewCourses(final Utils utils) {

        utils.addRecyclerView(R.id.activity_courses_recyclerview, new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false),
                new RecyclerViewAdapter(getApplicationContext(), R.layout.activity_courses_list_item, termsArrayList.size(), new RecyclerViewMethod() {

                    @Override
                    public void onItem(RecyclerViewAdapter.ViewHolder holder, int position, View itemView) {
                        Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.drawable.tourists).into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_name)).setText(termsArrayList.get(position).getTermName() + "");
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_price)).setText(utils.splitDigits(termsArrayList.get(position).getPrice()) + "");
                        Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.drawable.computer).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));

                        final Button activity_courses_button_cart = itemView.findViewById(R.id.activity_courses_button_cart);
                        final LinearLayout activity_courses_layout_validity = itemView.findViewById(R.id.activity_courses_layout_validity);
                        final LinearLayout activity_courses_layout_item = itemView.findViewById(R.id.activity_courses_layout_item);
                        if (termsArrayList.get(position).isTermStatus() == true) {
                            activity_courses_layout_validity.setVisibility(View.VISIBLE);
                            activity_courses_button_cart.setVisibility(View.GONE);
                            activity_courses_layout_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    utils.goTo(AzmoonsActivity.class);
                                    utils.setSharedPreferences("termId", termsArrayList.get(position).getTermId());
                                    utils.setSharedPreferences("testTime", termsArrayList.get(position).getTestTime());
                                    utils.setSharedPreferences("numberQuestionOfLevel", termsArrayList.get(position).getNumberQuestionOfLevel());
                                }
                            });

                        } else {
                            activity_courses_layout_validity.setVisibility(View.GONE);
                        }
                        // TODO: 2/11/2021 ItemClickListener

                        activity_courses_button_cart.setOnClickListener(view -> {
                            showCustomDialod();
                            custom_dialog_text_courses_type.setText(termsArrayList.get(position).getTermName());
                            custom_dialog_text_courses_price.setText(utils.splitDigits(termsArrayList.get(position).getPrice()));
                            custom_dialog_button_courses_payment.setOnClickListener(view1 -> {

                                data.sendRequestByPostMethodFactor((Integer) utils.getSharedPreferences("userId", 0), (Integer) utils.getSharedPreferences("termId", 0), new OnResult() {
                                    @Override
                                    public void success(Object... objects) {
                                        factorId = (int) objects[0];
                                    }
                                });

                                myPayment((long) termsArrayList.get(position).getPrice(), termsArrayList.get(position).getTermName());
                                price = termsArrayList.get(position).getPrice();
                                dialog.dismiss();
                                if (result == true) {
                                    activity_courses_layout_validity.setVisibility(View.VISIBLE);
                                    activity_courses_button_cart.setVisibility(View.GONE);
                                }
                            });
                            custom_dialog_button_courses_cancel.setOnClickListener(view12 -> dialog.dismiss());
                        });
                    }
                }));
    }

    public ArrayList<Terms> fillTearm(byte fieldId) {
        ArrayList<Terms> arrayList = new ArrayList<>();
        for (int i = 0; i < termsArrayList.size(); i++) {
            if (termsArrayList.get(i).getFieldId() == fieldId) {
                arrayList.add(termsArrayList.get(i));
            }
        }
        return arrayList;
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
                Intent intent = new Intent(CoursesActivity.this, ProfileActivity.class);
                startActivity(intent);
                activity_courses_drawer.closeDrawer(GravityCompat.START);
                activity_courses_navigation_view.setCheckedItem(null);
                break;
            case R.id.item_logout:
                setDialogLogOut();
                activity_courses_navigation_view.setCheckedItem(null);
                break;
            case R.id.item_courses:
                utils.goTo(CoursesActivity.class);
                activity_courses_drawer.closeDrawer(GravityCompat.START);
                activity_courses_navigation_view.setCheckedItem(null);
                break;
            case R.id.item_home1:
                utils.goTo(FieldActivity.class);
                activity_courses_drawer.closeDrawer(GravityCompat.START);
                activity_courses_navigation_view.setCheckedItem(null);
                break;

        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        if (activity_courses_drawer.isDrawerVisible(GravityCompat.START)) {
            activity_courses_drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();

    }

    private void myPayment(Long amount, String description) {
        ZarinPal zarinPal = ZarinPal.getPurchase(this);
        PaymentRequest paymentRequest = ZarinPal.getPaymentRequest();
        paymentRequest.setAmount(amount);// قیمت
        paymentRequest.setMerchantID("810179d8-0e5f-11e9-9708-005056a205be");
        paymentRequest.setDescription(description);// توضیحات
        paymentRequest.setCallbackURL("azmoon://yahoo");

        zarinPal.startPayment(paymentRequest, (status, authority, paymentGatewayUri, intent) -> {
            if (status == 100) {
                data.sendRequestByPostMethodPayment(price, factorId, "پرداخت ", authority, status, new OnResult() {
                    @Override
                    public void success(Object... objects) {
                        paymentId = (int) objects[0];
                        // Log.i("boolean", "result " + result);
                    }
                });

                //زمانی که به درگاه پرداخت برود
                startActivity(intent);
            } else {
                //زمانی که به درگاه پرداخت وصل نشود(خطا داشته باشد)
                Toast.makeText(CoursesActivity.this, "خطا در درخواست پرداخت!", Toast.LENGTH_SHORT).show();
            }
        });
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
}
