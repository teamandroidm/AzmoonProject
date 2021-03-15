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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private int resource;
    private Activity activity;
    private int questionCount;
    private int levelCount;

    public MyAdapter(Context context, int resource, int levelCount, int questionCount, Activity activity) {
        this.context = context;
        this.resource = resource;
        this.activity = activity;
        this.questionCount = questionCount;
        this.levelCount = levelCount;
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
        holder.FillItem(position);
    }

    @Override
    public int getItemCount() {
        return questionCount;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;
        private ImageView img;
        private LinearLayout lil;
        private Dialog dialog;
        private Button btnCancle;
        private Button btnYes;
        private Utils utils;

        public ViewHolder(View itemView) {
            super(itemView);
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_start_quiz);
            txt = (TextView) itemView.findViewById(R.id.item_question_level_txt1);
            img = (ImageView) itemView.findViewById(R.id.item_question_level_img1);
            lil = (LinearLayout) itemView.findViewById(R.id.lil_level_item);
            btnCancle = (Button) dialog.findViewById(R.id.dialog_btnCancel);
            btnYes = (Button) dialog.findViewById(R.id.dialog_btnYes);
            utils = new Utils(context, activity);
        }

        public void FillItem(int position) {
            if (position == levelCount) {
                txt.setTextColor(Color.argb(255, 0, 0, 0));
                img.setImageResource(R.drawable.unlockicon);
            } else if (position > levelCount) {
                txt.setTextColor(Color.argb(130, 0, 0, 0));
                img.setImageResource(R.drawable.lockicon);
            } else if (position < levelCount) {
                txt.setTextColor(Color.argb(255, 255, 118, 0));
                img.setImageResource(R.drawable.checkmark_52px);
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
            txt.setText("آزمون" + utils.farsiNumberConvert(String.valueOf(position+1)));

            lil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == levelCount) {
                        showCustomDialog();
                    } else if (position > levelCount) {
                        Toast.makeText(context, "مرحله قفل است !!!", Toast.LENGTH_SHORT).show();
                    } else if (position < levelCount) {
                        Intent intent = new Intent(activity, QuestionActivity.class);
                        intent.putExtra("status", (byte) 2);
                        intent.putExtra("level", position+1);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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
                    intent.putExtra("level", position+1);
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