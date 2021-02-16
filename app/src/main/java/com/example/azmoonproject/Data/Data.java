package com.example.azmoonproject.Data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.azmoonproject.Engine.Utils;
import com.example.azmoonproject.Model.Factors;
import com.example.azmoonproject.Model.Fields;
import com.example.azmoonproject.Model.Levels;
import com.example.azmoonproject.Model.Questions;
import com.example.azmoonproject.Model.Terms;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


public class Data {
    private static final String _URL1 = "http://192.168.1.102:8080/MyWebService/showalluser.php";
    ArrayList<Factors> factorsList = new ArrayList<>();
    ArrayList<Fields> fieldsArrayList = new ArrayList<>();
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    private Context context;
    private RequestQueue requestQueue = null;
    private boolean isError = false;
    private boolean status = false;
    private ArrayList<Levels> questionLevelItemArrayList = null;
    Utils utils;

    public Data(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        utils=new Utils(context);
    }

    public Data() {
    }

    public void sendIdField(int userId, byte fieldId, OnResult onResult) {
        if (userId == 1 && fieldId == 3) {
            onResult.success(true);
            return;
        } else {
            onResult.success(false);

        }
    }

    public void sendCourses(int userId, byte termdId, Date date, int price, OnResult onResult) {
        date = new Date();
//        if (userId == 1 && fieldId == 3) {
//            onResult.success(true);
//            return;
//        } else {
//            onResult.success(false);

//        }
    }

    public void SendRequestByPostMethodField(String url) {
//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Fields>>() {
//                        }.getType();
//                        fieldsArrayList = gson.fromJson(response, listType);
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                //   txt1.setText("Error : "+error.getMessage());
//                // prg1.setVisibility(View.INVISIBLE);
//            }
//        });
//        requestQueue.add(request);
    }

    public void SendRequestByPostMethodCourses(String url) {
//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Terms>>() {
//                        }.getType();
//                        termsArrayList = gson.fromJson(response, listType);
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                //   txt1.setText("Error : "+error.getMessage());
//                // prg1.setVisibility(View.INVISIBLE);
//            }
//        });
//        requestQueue.add(request);
    }

    public void sendByPostMethod(String url, JSONObject jsonObject) {
//        JsonObjectRequest request=new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                              txtShowUsers1.setText("Ø§Ø·Ù„Ø§Ø¹Ø§Øª Ú©Ø§Ø±Ø¨Ø± Ø¨Ù‡ Ø±ÙˆØ´ post Ø¯Ø±Ø¬ Ø´Ø¯");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //   txtShowUsers1.setText("Error : "+error.getMessage());
//                    }
//                });
//        requestQueue.add(request);
    }

    public void Login(String userName,String password ,OnResult onResult) {

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userName", userName);
//            jsonObject.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                JsonObjectRequest request = new JsonObjectRequest(
//                        Request.Method.POST,
//                        "آدرس Api",
//                        jsonObject,
//                        response -> {
//                            try {
//                                if (response.getInt("userId") > 0) {  // زمانی که کاربری در سیستم با نام وارد شده کاربر وجود داشته باشد
//                                  utils.setSharedPreferences("userId",response.getInt("userId"));
//                                  utils.setSharedPreferences("name",response.getString("name"));
//                                  utils.setSharedPreferences("family",response.getString("family"));
//                                  utils.setSharedPreferences("fieldId",(byte)response.getInt("fieldId"));
//                                    status = true;
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        },
//                        error -> isError = true);
//                        requestQueue.add(request);
//            }
//        });
//        thread.start();
//        onResult.success( isError, status);


        //data fake

        utils.setSharedPreferences("userId", 2);
        utils.setSharedPreferences("name", "admin");
        utils.setSharedPreferences("family", "Admin");
        utils.setSharedPreferences("fieldId", 2);
        onResult.success(false, true);

    }

    public void getFactors(String url,int userId, OnResult onResult) {

//
//        JSONObject factor = new JSONObject();
//        try {
//
//            factor.put("userid", userId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonArrayRequest request=new JsonArrayRequest(
//                Request.Method.POST,
//                url,
//                factor,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Gson gson=new Gson();
//                        Type listType = new TypeToken<ArrayList<Factors>>(){}.getType();
//                        factorsList=gson.fromJson(response.toString(),listType);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );
//
//        requestQueue.add(request);

        for (Factors factor : fakeDataFactor()) {
            if (factor.getUserId() == userId ) {
                factorsList.add(factor);
            }
        }
        onResult.success(factorsList);

    }




    public void NewPassword(String url,int userid,String Pass, String newPass, OnResult onResult) {



//        JSONObject user = new JSONObject();
//        try {
//            user.put("password", newPass);
//            user.put("userid", userId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                user,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//        requestQueue.add(request);

        onResult.success(true);
    }

    public void getNameFamily(OnResult onResult) {
        // user id from class G
        String name = "هانیه";
        String family = "پروین";
        //
        onResult.success(name + " " + family);
    }

    public ArrayList<Factors> fakeDataFactor() {
        ArrayList<Factors> factorsArrayList = new ArrayList<>();
        Factors factors = new Factors();
        factors.setFactorId(1);
        factors.setFinally(true);
        factors.setFinallyDate(new Date());
        factors.setPrice(30000);
        factors.setTermName("icdl");
        factors.setUserId(2);
        factors.setValidateTime(14);
        factorsArrayList.add(factors);

        /////////
        Factors factors2 = new Factors();
        factors2.setFactorId(3);
        factors2.setFinally(true);
        factors2.setFinallyDate(new Date(new Date().getTime() - 84600000*2));
        factors2.setPrice(30000);
        factors2.setTermName("پایتون");
        factors2.setUserId(2);
        factors2.setValidateTime(14);
        factorsArrayList.add(factors2);
        ////////////
        Factors factors3 = new Factors();
        factors3.setFactorId(4);
        factors3.setFinally(true);
        factors3.setFinallyDate(new Date(new Date().getTime() - 84600000*5));
        factors3.setPrice(30000);
        factors3.setTermName("جاوا");
        factors3.setUserId(2);
        factors3.setValidateTime(14);
        factorsArrayList.add(factors3);

        return factorsArrayList;

    }

    public ArrayList<Levels> getLevel() {
        if (questionLevelItemArrayList == null) {
            questionLevelItemArrayList = new ArrayList<>();
            questionLevelItemArrayList.add(new Levels((byte) 1, (byte) 3, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 2, (byte) 3, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 3, (byte) 3, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 4, (byte) 3, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 5, (byte) 1, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 6, (byte) 2, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 7, (byte) 2, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 8, (byte) 2, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 9, (byte) 2, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
            questionLevelItemArrayList.add(new Levels((byte) 10, (byte) 2, (byte) 4, (byte) 6, (byte) 40, 50, 2, 100));
        }
        return questionLevelItemArrayList;
    }
    // QuestionActivity
    public void getQuestions(int termId, int level, OnResult onResult) {

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("termId", termId);
//            jsonObject.put("level", level);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                "آدرس Api",
//                jsonObject,
//                response -> {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<ArrayList<Questions>>() {
//                    }.getType();
//                    ArrayList<Questions> questions = gson.fromJson(response.toString(), type);
//                    onResult.success((Object) questions.toArray());
//                }, error -> {
//        });
//        requestQueue.add(request);

        //Data fake
        Questions[] questions = new Questions[50];
        questions[0] = new Questions(0, "سوال یک", "الف", "ب", "پ", "چ", (byte) 3, true, 0);
        questions[1] = new Questions(1, "سوال دو", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 1, true, 0);
        questions[2] = new Questions(2, "سوال سه", "شسیب", "شبیسب", "زرذز", "ابلای", (byte) 4, true, 0);
        questions[3] = new Questions(3, "سوال چهار", "A", "B", "C", "D", (byte) 2, true, 0);
        questions[4] = new Questions(4, "سوال پنجم", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 4, true, 0);
        questions[5] = new Questions(5, "سوال ششم", "بمنشسیبکمن", "بهشتسیمبنتشمینب", "ئرذئمذنررئمذن", "شسیب", (byte) 2, true, 0);
        questions[6] = new Questions(6, "سوال هفتم", "AAAA", "BBBBBBB", "CCCCCCCCCC", "DDDDDDDDDDDD", (byte) 1, true, 0);
        questions[7] = new Questions(7, "سوال هشتم", "گزینه اول ", "بشیتمبن شمسینبمشسیب سشیب", "هتهتحهتحهتهح", "مبنلمنسیمبنلسیمبنلتیمبنتمن", (byte) 1, true, 0);
        questions[8] = new Questions(8, "سوال نهم", "جواب1", "جواب2", "جواب3", "جواب4", (byte) 2, false, 0);
        questions[9] = new Questions(9, "دهم", "یک", "دو", "سه", "چهار", (byte) 3, false, 0);
        for (int i = 10; i < 50; i++)
            questions[i] = new Questions(i, "title", "1", "2", "3", "4", (byte) 2, true, 0);

        onResult.success((Object) questions);
    }

    public void setLevel(Levels level, OnResult onResult) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("correctNumber", level.getCorrectNumber());
//            jsonObject.put("nineteen", level.getNineteen());
//            jsonObject.put("wrongNumber", level.getWrongNumber());
//            jsonObject.put("levelCount", level.getLevelCount());
//            jsonObject.put("timeTookTest", level.getTimeTookTest());
//            jsonObject.put("termId", level.getTermId());
//            jsonObject.put("userId", level.getUserId());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                "آدرس Api",
//                jsonObject,
//                response -> {
//                }, error -> {
//        });
//        requestQueue.add(request);
    }

}
