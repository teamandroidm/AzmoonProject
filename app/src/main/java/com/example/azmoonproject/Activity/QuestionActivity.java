package com.example.azmoonproject.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Engine.MyReceiver;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Levels;
import com.example.azmoonproject.Model.Questions;
import com.example.azmoonproject.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {
    private RadioGroup rgAnswer;
    private Questions[] questions;
    private Button btnPreviousQuestion, btnNetQuestion, btnEndTest;
    private TextView txtTime, txtnumberQuestionOfLevel, txtCounter, txtTextQuestion;
    private CircularProgressBar circularProgressBar;
    private int time, timeTookTest;
    private byte[] answers;
    private ProgressBar prgCount;
    private Data data;
    private byte wrongNumber = 0, correctNumber = 0, nineteen = 0, counter = 0, status;
    private boolean isEndExam = false, doubleBackPress = false;
    private Bundle bundle;
    private Utils utils;
    private MyReceiver myReceiver;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setupView();
        myReceiver = new MyReceiver();
        bundle = getIntent().getExtras();
        utils = new Utils(this);
        setData();


        btnPreviousQuestion.setOnClickListener(v -> {
            if (counter >= 1) {
                rgAnswer.clearCheck();
                txtCounter.setText(String.format("%s%s", (--counter < 9) ? "0" : "", counter + 1));
                prgCount.setProgress(counter + 1, true);
                radioButtonSetText(counter);
                if (status == 1) {
                    if (answers[counter] != -1)
                        ((RadioButton) rgAnswer.getChildAt(answers[counter])).setChecked(true);
                    if (isEndExam) //end time exam
                        changeColorRadioEndTimeExam();  // set color radioButton true answer and false answer
                } else {
                    restColorRadioButton();
                    changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1); //only set color radioButton true answer
                }
            }
        });

        btnNetQuestion.setOnClickListener(v -> {
            if (counter < (questions.length) - 1) {
                rgAnswer.clearCheck();
                txtCounter.setText(String.format("%s%s", (++counter < 9) ? "0" : "", counter + 1));
                prgCount.setProgress(counter + 1, true);
                radioButtonSetText(counter);
                if (status == 1) {
                    if (answers[counter] != -1)
                        ((RadioButton) rgAnswer.getChildAt(answers[counter])).setChecked(true);
                    if (isEndExam) //end time exam
                        changeColorRadioEndTimeExam();  // set color radioButton true answer and false answer
                } else {
                    restColorRadioButton();
                    changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1); //only set color radioButton true answer
                }
            }
        });

        rgAnswer.setOnCheckedChangeListener((group, checkedId) -> {
            if (answers != null && checkedId != -1 && !isEndExam)
                answers[counter] = (byte) group.indexOfChild(findViewById(checkedId));
        });

        btnEndTest.setOnClickListener(v -> {
            if (isEndExam || status == 2)
                finish();
            else {
                dialogEndTimeExam();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setData() {
        status = bundle.getByte("status"); // get TermId and Leve from TestActivity
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("درحال دریافت اطلاعات");
        progressDialog.setCancelable(false);
        progressDialog.show();

        data.getQuestions(bundle.getInt("termId"), bundle.getInt("level"), objects -> {
            if (objects[0] != null) {
                questions = (Questions[]) objects[0];
                txtnumberQuestionOfLevel.setText(String.format("%s/", questions.length));
                prgCount.setMax(questions.length);
                prgCount.setProgress(1);
                radioButtonSetText((byte) 0);
                if (status == 1) {
                    exam(bundle.getInt("testTime"));
                } else if (status == 2) {
                    btnEndTest.setText("");
                    circularProgressBar.setVisibility(View.GONE);
                    txtTime.setVisibility(View.GONE);
                    enableRadioGroup(false);
                    changeGreenColorRadioButton(questions[counter].getTrueAnswer()); //only set color radioButton true answer
                }
            } else {
                Toast.makeText(this, "دریافت اطلاعات با خطا مواجع شد لطفا دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                finish();
            }
            progressDialog.dismiss();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeGreenColorRadioButton(int index) {
        ((RadioButton) rgAnswer.getChildAt(index)).setChecked(true);
        rgAnswer.getChildAt(index).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.color_green)));
        ((RadioButton) rgAnswer.getChildAt(index)).setButtonDrawable(R.drawable.bg_ic_check);
        ((RadioButton) rgAnswer.getChildAt(index)).setButtonTintList(ColorStateList.valueOf(getColor(R.color.color_green)));
        ((RadioButton) rgAnswer.getChildAt(index)).setTextColor(ColorStateList.valueOf(getColor(R.color.color_green)));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeColorRadioEndTimeExam() {
        restColorRadioButton();
        if (answers[counter] == -1) {
            changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1);
        } else if (questions[counter].getTrueAnswer() - 1 == answers[counter]) {
            changeGreenColorRadioButton(answers[counter]);
        } else {
            rgAnswer.getChildAt(answers[counter]).setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter])).setButtonTintList(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter])).setTextColor(ColorStateList.valueOf(getColor(R.color.color_red)));
            ((RadioButton) rgAnswer.getChildAt(answers[counter])).setChecked(false);
            ((RadioButton) rgAnswer.getChildAt(answers[counter])).setButtonDrawable(R.drawable.bg_ic_cancel);

            //true answer change color
            changeGreenColorRadioButton(questions[counter].getTrueAnswer() - 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void restColorRadioButton() {
        for (int i = 0; i < rgAnswer.getChildCount(); i++) {    //reset textColor and background and buttonTint radioButton
            rgAnswer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.darker_gray)));
            ((RadioButton) rgAnswer.getChildAt(i)).setButtonTintList(ColorStateList.valueOf(getColor(android.R.color.darker_gray)));
            ((RadioButton) rgAnswer.getChildAt(i)).setTextColor(getColor(R.color.color_gray_dark));
            ((RadioButton) rgAnswer.getChildAt(i)).setButtonDrawable(R.drawable.bg_ic_radio_button);
        }
    }

    private void enableRadioGroup(boolean isEnable) {
        for (int i = 0; i < rgAnswer.getChildCount(); i++)
            rgAnswer.getChildAt(i).setEnabled(isEnable);
    }

    private void radioButtonSetText(byte b) {
        txtTextQuestion.setText(questions[b].getQuestionText());
        ((RadioButton) rgAnswer.getChildAt(0)).setText(questions[b].getOption1());
        ((RadioButton) rgAnswer.getChildAt(1)).setText(questions[b].getOption2());
        ((RadioButton) rgAnswer.getChildAt(2)).setText(questions[b].getOption3());
        ((RadioButton) rgAnswer.getChildAt(3)).setText(questions[b].getOption4());
    }

    private void exam(int _time) {
        time = _time * 60;
        circularProgressBar.setProgressMax(time);
        final Handler handler = new Handler();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                handler.post(() -> {
                    if (time > 0) {
                        circularProgressBar.setProgress(--time);
                        txtTime.setText(String.format("%s%s:%s%s", (time / 60) <= 9 ? "0" : "", time / 60, (time % 60) <= 9 ? "0" : "", time % 60));
                        timeTookTest++;
                    } else if (time == 0) {
                        for (int i = 0; i < answers.length; i++) { //calculate the exam result
                            if (answers[i] == -1)
                                nineteen++;
                            else if (answers[i] == questions[i].getTrueAnswer() - 1)
                                correctNumber++;
                            else wrongNumber++;
                        }
                        dialogEndExam();
                        time--;
//                                        data.setLevel(new Levels((byte) 0, bundle.getByte("level"),
//                                nineteen, wrongNumber, correctNumber, timeTookTest, bundle.getInt("termId"),
//                                (Integer) utils.getSharedPreferences("userId", "0")),
//                        objects -> Log.i("status", (String) objects[0]));
                    }
                });
            }
        }, 0, 1000);

        answers = new byte[questions.length];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = (byte) -1;
        }
    }

    private void dialogEndTimeExam() {
        Dialog dialog = new Dialog(QuestionActivity.this);
        dialog.setContentView(R.layout.custom_dialog_finish);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.custom_dialog_finish_btn_yes).setOnClickListener(v -> {
            time = 1;
            dialog.dismiss();
        });
        dialog.findViewById(R.id.custom_dialog_finish_btn_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dialogEndExam() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_finish_test);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((TextView) dialog.findViewById(R.id.custom_dialog_finish_test_txt_number_correct_answer)).setText(correctNumber + "");
        ((TextView) dialog.findViewById(R.id.custom_dialog_finish_test_txt_number_nineteen)).setText(nineteen + "");
        ((TextView) dialog.findViewById(R.id.custom_dialog_finish_test_txt_number_wrongNumber)).setText(wrongNumber + "");
        ((TextView) dialog.findViewById(R.id.custom_dialog_finish_test_txt_timeTookTest)).setText(String.format("%s%s:%s%s", (timeTookTest / 60) <= 9 ? "0" : "", timeTookTest / 60, (timeTookTest % 60) <= 9 ? "0" : "", timeTookTest % 60));
        dialog.findViewById(R.id.custom_dialog_finish_test_bnt1).setOnClickListener(v -> {
            enableRadioGroup(false);
            isEndExam = true;
            btnEndTest.setText("");
            changeColorRadioEndTimeExam();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void setupView() {
        rgAnswer = findViewById(R.id.activity_question_rgAnswer);
        btnNetQuestion = findViewById(R.id.activity_question_btnNextQuestion);
        btnPreviousQuestion = findViewById(R.id.activity_question_btnPreviousQuestion);
        circularProgressBar = findViewById(R.id.activity_question_circularProgressbar);
        txtTime = findViewById(R.id.activity_question_txtTime);
        prgCount = findViewById(R.id.activity_question_prgCount);
        txtCounter = findViewById(R.id.activity_question_txtCounter);
        txtnumberQuestionOfLevel = findViewById(R.id.activity_question_txtNumberQuestionOfLevel);
        txtTextQuestion = findViewById(R.id.activity_question_txtTextQuestion);
        btnEndTest = findViewById(R.id.activity_question_btnEndTest);
        setupToolbar();
        data = new Data(getApplicationContext());
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.activity_question_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (isEndExam || status == 2) {
            if (doubleBackPress) {
                finish();
                return;
            }
            this.doubleBackPress = true;
            new Handler().postDelayed(() -> doubleBackPress = false, 2000);
            Toast.makeText(this, "برای خروج دوبار کلیک کنید", Toast.LENGTH_SHORT).show();
        } else dialogEndTimeExam();
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