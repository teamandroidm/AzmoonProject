package com.example.azmoonproject.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Data.Data;
import com.example.azmoonproject.Model.Levels;
import com.example.azmoonproject.MyAdapter;
import com.example.azmoonproject.R;

import java.util.ArrayList;

public class AzmoonsActivity extends AppCompatActivity {
    Toolbar tlb1;
    ImageView imgBack;
    ImageView img1;
    TextView txt1;
    RecyclerView rcl;
    ArrayList<Levels> questionLevelItemArrayList;
    Levels levelItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azmoons);

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
}