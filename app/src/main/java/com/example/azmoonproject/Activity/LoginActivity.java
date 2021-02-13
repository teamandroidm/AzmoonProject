package com.example.azmoonproject.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Data.OnResult;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btnLogin;
    private TextInputLayout edtLUserName, edtLPassword;
    private TextInputEditText edtUserName, edtPassword;
    private CheckBox checkBox;
    private Utils utils;
    private Data data;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new Utils(LoginActivity.this, LoginActivity.this);
        init();
        data = new Data(LoginActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserName.getText().toString().length() > 0 && edtPassword.getText().toString().length() > 0) {
                    data.Login(edtUserName.getText().toString(), edtPassword.getText().toString(), new OnResult() {
                        @Override
                        public void success(Object... objects) {
                            if (!((Boolean) objects[0])) {
                                if ((Boolean) objects[1]) { //ورود کاربر
                                    if (checkBox.isChecked()) {  // زمانی که روی مرا به خاطر بسپار کلیک کرده است
                                        utils.setSharedPreferences("isLogged", true);
                                    }
                                    utils.goTo(FieldActivity.class);
                                } else {
                                    Toast.makeText(LoginActivity.this, "کاربری با این نام کاربری و رمز عبور وجود ندارد", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "خطا در اتصال به شبکه", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "نام کاربری یا رمزعبور خالی است!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void init() {
        toolbar = findViewById(R.id.activity_login_toolbar);
        edtPassword = findViewById(R.id.activity_login_edtPassword);
        edtUserName = findViewById(R.id.activity_login_edtUserName);
        edtLUserName = findViewById(R.id.activity_login_edtLUserName);
        edtLPassword = findViewById(R.id.activity_login_edtLPassword);
        btnLogin = findViewById(R.id.activity_login_btnLogin);
        checkBox = findViewById(R.id.activity_login_chk1);
    }


}
