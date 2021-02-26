package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Dialog dialog;
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    // ArrayList<Terms> arrayList = new ArrayList<>();
    Button custom_dialog_button_courses_payment, custom_dialog_button_courses_cancel;
    TextView custom_dialog_text_courses_type, custom_dialog_text_courses_price;
    Toolbar toolbar;
    ImageView back;
    Data data;
    Dialog logOutDialog;
    Button logOutDialogBtnNo, logOutDialogBtnYes;
    Utils utils;
    private NavigationView activity_courses_navigation_view;
    private DrawerLayout activity_courses_drawer;
    private ImageView activity_courses_menu_img;
    MyReceiver myReceiver;
    int factorId = 0;
    int paymentId = 0;
    Boolean result = false;

//    public static String splitDigits(int number) {
//        return new DecimalFormat("###,###,###").format(number);
//    }

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
                // Log.i("%%%%%%%", "e " + termsArrayList.size());
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
//        Bundle extrns = getIntent().getExtras();
//        byte x = (byte) ((byte) extrns.getByte("fieldId"));
        int x = (Integer) utils.getSharedPreferences("fieldId", 0);
        //Toast.makeText(CoursesActivity.this,x + "",Toast.LENGTH_LONG).show();
        termsArrayList = fillTearm((byte) x);


        setOnClickImageView();
        //درگاه پرداخت
        Uri uri = getIntent().getData();

        ZarinPal.getPurchase(this).verificationPayment(uri, (isPaymentSuccess, refID, paymentRequest) -> {
            if (isPaymentSuccess) {// زمانی که از درگاه پرداخت برمیگردد و پرداخت انجام شده
//                    refID //شماره تراکنش
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


//    private void termsItemList() {
//        String java = picPath(R.drawable.java);
//        String edit = picPath(R.drawable.edit);
//        String excel = picPath(R.drawable.excel);
//        String access = picPath(R.drawable.access);
//        String powerpoint = picPath(R.drawable.powerpoint);
//        String word = picPath(R.drawable.word);
//        String tourists = picPath(R.drawable.tourists);
//        String accounting = picPath(R.drawable.accounting);
//        String hashtag = picPath(R.drawable.hashtag);
//        termsArrayList.add(new Terms(1, "جاوا", java, 20000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(2, "پاورپوینت", powerpoint, 30000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(3, "اکسل", excel, 40000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(4, "اکسس", access, 12000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(5, "سی شارپ", hashtag, 18000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(6, "ورد", word, 25000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(7, "سی پلاس پلاس", edit, 32000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(1, "هلو", accounting, 20000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "محک", accounting, 30000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(3, "قیاس", accounting, 22000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(4, "شایگان", accounting, 18000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "تدبیر", accounting, 15000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(3, "پیوست", accounting, 40000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(4, "آرین سیستم", accounting, 82000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "مدیریت جهانگردی", tourists, 30000, true, 50, (byte) 50, (byte) 2));
//        termsArrayList.add(new Terms(3, "جغرافبا", tourists, 22000, true, 50, (byte) 50, (byte) 2));
//        termsArrayList.add(new Terms(4, "مسافرتی", tourists, 18000, true, 50, (byte) 50, (byte) 2));
//    }

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
                        // Log.i("re", "e " + termsArrayList.size());
                        Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.drawable.tourists).into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_name)).setText(termsArrayList.get(position).getTermName() + "");
                        ((TextView) itemView.findViewById(R.id.activity_courses_text_price)).setText(utils.splitDigits(termsArrayList.get(position).getPrice()) + "");
                        //    Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.mipmap.logocourses).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));
                        Picasso.get().load(new File(termsArrayList.get(position).getImageName())).error(R.drawable.computer).fit().centerCrop().into((ImageView) itemView.findViewById(R.id.activity_courses_image_courses));

                        final Button activity_courses_button_cart = itemView.findViewById(R.id.activity_courses_button_cart);
                        final LinearLayout activity_courses_layout_validity = itemView.findViewById(R.id.activity_courses_layout_validity);
                        final LinearLayout activity_courses_layout_item = itemView.findViewById(R.id.activity_courses_layout_item);

                        activity_courses_layout_validity.setVisibility(View.GONE);
                        // TODO: 2/11/2021 ItemClickListener
                        activity_courses_layout_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                utils.goTo(AzmoonsActivity.class);
                                utils.setSharedPreferences("termId", termsArrayList.get(position).getTermId());
                                utils.setSharedPreferences("testTime", termsArrayList.get(position).getTestTime());
                                utils.setSharedPreferences("numberQuestionOfLevel", termsArrayList.get(position).getNumberQuestionOfLevel());


                            }
                        });

                        activity_courses_button_cart.setOnClickListener(view -> {
                            showCustomDialod();
                            custom_dialog_button_courses_payment.setOnClickListener(view1 -> {

                                data.sendRequestByPostMethodFactor("http://mehdi899.ir/APi/FactorApi/PostFactorId", 2, 1, new OnResult() {
                                    @Override
                                    public void success(Object... objects) {
                                        // Log.i("NNN",objects.length+"");
                                        factorId = (int) objects[0];
                                        //  Log.i("rexx", "e " + factorId);
                                    }
                                });

                                // Toast.makeText(CoursesActivity.this,date + "", Toast.LENGTH_LONG).show();
                                myPayment((long) termsArrayList.get(position).getPrice(), termsArrayList.get(position).getTermName());
                                data.sendRequestByPostMethodPayment("http://mehdi899.ir/api/PaymentApi/AddPayment", termsArrayList.get(position).getPrice(), factorId, "پرداخت ", " 10000", 100, new OnResult() {
                                    @Override
                                    public void success(Object... objects) {
                                        paymentId = (int) objects[0];
                                        // Log.i("boolean", "result " + result);
                                    }
                                });
                                data.sendRequestByPostMethodPaymentresult("http://mehdi899.ir/api/PaymentApi/PaymentResult", true, paymentId, factorId, new OnResult() {
                                    @Override
                                    public void success(Object... objects) {
                                        result = (Boolean) objects[0];
                                    }
                                });

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
        //Toast.makeText(CoursesActivity.this,arrayList.size(),Toast.LENGTH_LONG).show();
        return arrayList;
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
                break;
            case R.id.item_logout:
                setDialogLogOut();
                break;
            case R.id.item_courses:
                utils.goTo(CoursesActivity.class);
                activity_courses_drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.item_home1:
                utils.goTo(FieldActivity.class);
                activity_courses_drawer.closeDrawer(GravityCompat.START);
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
