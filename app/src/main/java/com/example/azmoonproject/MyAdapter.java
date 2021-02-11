package com.example.azmoonproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azmoonproject.Activity.QuestionActivity;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Levels;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    int resource;
    ArrayList<Levels> questionLevelItemArrayList;
    Activity activity;

    public MyAdapter(Context context, int resource, ArrayList<Levels> questionLevelItemArrayList, Activity activity) {
        this.context = context;
        this.resource = resource;
        this.questionLevelItemArrayList = questionLevelItemArrayList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Levels levelItem = questionLevelItemArrayList.get(position);
        holder.FillItem(levelItem, position);
    }

    @Override
    public int getItemCount() {
        return questionLevelItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;
        public ImageView img;
        public LinearLayout lil;
        public int status;
        public Dialog dialog;
        public Button btnCancle;
        public Button btnYes;
        public Utils utils;

        public ViewHolder(View itemView) {
            super(itemView);
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_start_quiz);
            Levels levelItem = new Levels();
            txt = (TextView) itemView.findViewById(R.id.item_question_level_txt1);
            img = (ImageView) itemView.findViewById(R.id.item_question_level_img1);
            lil = (LinearLayout) itemView.findViewById(R.id.lil_level_item);
            btnCancle = (Button) dialog.findViewById(R.id.dialog_btnCancel);
            btnYes = (Button) dialog.findViewById(R.id.dialog_btnYes);
            utils = new Utils(context, activity);

        }

        public void FillItem(Levels levelItem, int position) {
//            txt.setText(FarsiNumber.Convert(levelItem.getTitle().toString()));
//            txt.setTextColor(levelItem.getColorRes());
//            img.setImageResource(levelItem.getImageId());

            byte status = levelItem.getLevelCount();
            byte level = levelItem.getLevelId();
            if (status == (byte) 1) {
                txt.setTextColor(Color.argb(255, 0, 0, 0));
                img.setImageResource(R.drawable.unlockicon);

            } else if (status == (byte) 2) {
                txt.setTextColor(Color.argb(130, 0, 0, 0));

                img.setImageResource(R.drawable.lockicon);

            } else if (status == (byte) 3) {

                txt.setTextColor(Color.argb(255, 255, 118, 0));
                img.setImageResource(R.drawable.checkmark_52px);

            }
            // TODO: 2/11/2021 Level?? 
            switch (level) {
                case 1:
                    txt.setText(utils.farsiNumberConvert("آزمون 1"));
                    break;
                case 2:
                    txt.setText(utils.farsiNumberConvert("آزمون 2"));
                    break;
                case 3:
                    txt.setText(utils.farsiNumberConvert("آزمون 3"));
                    break;
                case 4:
                    txt.setText(utils.farsiNumberConvert("آزمون 4"));
                    break;
                case 5:
                    txt.setText(utils.farsiNumberConvert("آزمون 5"));
                    break;
                case 6:
                    txt.setText(utils.farsiNumberConvert("آزمون 6"));
                    break;
                case 7:
                    txt.setText(utils.farsiNumberConvert("آزمون 7"));
                    break;
                case 8:
                    txt.setText(utils.farsiNumberConvert("آزمون 8"));
                    break;
                case 9:
                    txt.setText(utils.farsiNumberConvert("آزمون 9"));
                    break;
                case 10:
                    txt.setText(utils.farsiNumberConvert("آزمون 10"));
                    break;
            }

            lil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status == (byte) 1) {
                        showCustomDialog();

                    } else if (status == (byte) 2) {
                        Toast.makeText(context, "مرحله قفل است !!!", Toast.LENGTH_SHORT).show();
                    } else if (status == (byte) 3) {
                        Intent intent = new Intent(activity, QuestionActivity.class);
                        intent.putExtra("status", (byte) 2);
                        intent.putExtra("level", position);
                        intent.putExtra("termId", 0); // TODO: 2/11/2021 get termsId testTime from CoursesActivity
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(activity, QuestionActivity.class);
                    intent.putExtra("status", (byte) 1);
                    intent.putExtra("level", position);
                    intent.putExtra("termId", 0); // TODO: 2/11/2021 get termsId testTime from CoursesActivity
                    intent.putExtra("userId", 0); // TODO: 2/11/2021 get userId testTime from CoursesActivity
                    intent.putExtra("testTime", 20); // TODO: 2/11/2021 get testTime from CoursesActivity
                    activity.startActivity(intent);
                }
            });
        }

        private void showCustomDialog() {
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
