package com.example.azmoonproject.Data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.Map;


public class Data {
    ArrayList<Factors> factorsList = new ArrayList<>();
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    private Context context;
    private RequestQueue requestQueue = null;
    private boolean isError = false;
    private boolean status = false;
    private Boolean aBoolean = false;
    private ArrayList<Levels> questionLevelItemArrayList = null;
    Utils utils;

    public Data(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        utils = new Utils(context);
    }

    public Data() {
    }

//    public void sendIdField(int userId, byte fieldId, OnResult onResult) {
//        if (userId == 1 && fieldId == 1) {
//            onResult.success(true);
//            return;
//        } else {
//            onResult.success(false);
//
//        }
//    }

//    public void sendCourses(int userId, byte termdId, Date date, int price, OnResult onResult) {
//        date = new Date();
////        if (userId == 1 && fieldId == 3) {
////            onResult.success(true);
////            return;
////        } else {
////            onResult.success(false);
//
////        }
//    }

    public void sendRequestByPostMethodField( OnResult onResult) {

//        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Fields>>() {
//                        }.getType();
//                        fieldsArrayList = gson.fromJson(response.toString(), listType);
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //   txt1.setText("Error : "+error.getMessage());
//                // prg1.setVisibility(View.INVISIBLE);
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//
////        for (Fields fields : fieldItemList()) {
////            fieldsArrayList.add(fields);
////        }
//        onResult.success(fieldsArrayList);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://mehdi899.ir/APi/FieldApi/PostField",
                        response -> {
                            ArrayList<Fields> fieldsArrayList = null;
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                fieldsArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Fields fields = new Fields();
                                    fields.setFeildId((byte) jsonObject.getInt("feildId"));
                                    fields.setFeildName(jsonObject.getString("feildName"));
                                    fields.setImageName(jsonObject.getString("imageName"));
                                    fieldsArrayList.add(fields);
                                    Log.i("***", "e " +response);
                                }

                                onResult.success(fieldsArrayList);
                                Log.i("DEEE", "e " + fieldsArrayList.size());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> Log.i("###", "e " + error.getMessage())) {

                };

                requestQueue.add(stringRequest);
            }
//            thread.start();

        });
        thread.start();


}

//    private ArrayList<Fields> fieldItemList() {
//        ArrayList<Fields> fieldsItemArrayList = new ArrayList<>();
////        String computer = picPath(R.drawable.computer);
////        String tourists = picPath(R.drawable.tourists);
////        String accounting = picPath(R.drawable.accounting);
//
//        fieldsItemArrayList.add(new Fields((byte) 1, "کامپیوتر", " computer", true));
//        fieldsItemArrayList.add(new Fields((byte) 2, "گردشگری", "tourists", false));
//        fieldsItemArrayList.add(new Fields((byte) 3, "حسابداری", "accounting", false));
//        return fieldsItemArrayList;
//    }


    public void sendRequestByPostMethodCourses(OnResult onResult) {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userId", userId);
//            jsonObject.put("fieldId", fieldId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.POST,
//                url,
//                jsonObject,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Terms>>() {
//                        }.getType();
//                        termsArrayList = gson.fromJson(response.toString(), listType);
//                        Log.i("****", "e " + termsArrayList.size());
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//               // Log.i("###", "e " + error.getMessage());
//                //   txt1.setText("Error : "+error.getMessage());
//                // prg1.setVisibility(View.INVISIBLE);
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//        for (Terms terms : termsItemList()) {
//            termsArrayList.add(terms);
//       }
//
//        onResult.success(termsArrayList);
//        Log.i("55",termsArrayList.size()+"");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "http://mehdi899.ir/api/TermsApi/UserTerm",
                        response -> {
                            ArrayList<Terms> termsArrayList = null;
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                termsArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Terms terms1 = new Terms();
                                    terms1.setTermId(jsonObject.getInt("termId"));
                                    terms1.setTermName(jsonObject.getString("termName"));
                                    terms1.setImageName(jsonObject.getString("imageName"));
                                    terms1.setPrice(jsonObject.getInt("price"));
                                    terms1.setTermStatus(jsonObject.getBoolean("termStatuse"));
                                    terms1.setNumberQuestionOfLevel((byte) jsonObject.getInt("numberQuestionOfLevel"));
                                    terms1.setValidateTime(jsonObject.getInt("vlidateTime"));
                                    termsArrayList.add(terms1);
                                }

                                onResult.success(termsArrayList);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> Log.i("###", "e " + error.getMessage())) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("userId", "2");
                        stringMap.put("fieldId", "1");
                        return stringMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }

//    private ArrayList<Terms> termsItemList() {
//        ArrayList<Terms> termsArrayList = new ArrayList<>();
//        //        String java = picPath(R.drawable.java);
////        String edit = picPath(R.drawable.edit);
////        String excel = picPath(R.drawable.excel);
////        String access = picPath(R.drawable.access);
////        String powerpoint = picPath(R.drawable.powerpoint);
////        String word = picPath(R.drawable.word);
////        String tourists = picPath(R.drawable.tourists);
////        String accounting = picPath(R.drawable.accounting);
////        String hashtag = picPath(R.drawable.hashtag);
//        termsArrayList.add(new Terms(1, "جاوا", "java", 20000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(2, "پاورپوینت", "powerpoint", 30000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(3, "اکسل", "excel", 40000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(4, "اکسس", "access", 12000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(5, "سی شارپ", "hashtag", 18000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(6, "ورد", "word", 25000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(7, "سی پلاس پلاس", "edit", 32000, true, 50, (byte) 50, (byte) 1));
//        termsArrayList.add(new Terms(1, "هلو", "accounting", 20000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "محک", "accounting", 30000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(3, "قیاس", "accounting", 22000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(4, "شایگان", "accounting", 18000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "تدبیر", "accounting", 15000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(3, "پیوست", "accounting", 40000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(4, "آرین سیستم", "accounting", 82000, true, 50, (byte) 50, (byte) 3));
//        termsArrayList.add(new Terms(2, "مدیریت جهانگردی", "tourists", 30000, true, 50, (byte) 50, (byte) 2));
//        termsArrayList.add(new Terms(3, "جغرافبا", "tourists", 22000, true, 50, (byte) 50, (byte) 2));
//        termsArrayList.add(new Terms(4, "مسافرتی", "tourists", 18000, true, 50, (byte) 50, (byte) 2));
//        return termsArrayList;
//    }

    public void sendRequestByPostMethodFactor(String url, int userId, int termId, OnResult onResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", userId);
            jsonObject.put("TermId", termId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int x = response.getInt("factorId");
                                    // Log.i("xxxxxxxx", x + "");
                                    //  Log.i("NNN",x+"");
                                    onResult.success(x);
                                    // Log.i("ssss",x + "");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //   txt1.setText("Error : "+error.getMessage());
                        // prg1.setVisibility(View.INVISIBLE);
                    }
                });
                requestQueue.add(jsonObjectRequest);

            }
        });
        thread.start();

//        StringRequest stringRequest = new StringRequest(Request.Method.POST,
//                "http://mehdi899.ir/api/TermsApi/UserTerm",
//                response -> {
//                    try {
//                        StringRequest stringRequest1 = new StringRequest(response);
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            Factors factors = new Factors();
//                            factors.setFactorId(jsonObject.getInt("FactorId"));
//
//                            termsArrayList.add(terms1);
//                        onResult.success(termsArrayList);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> Log.i("###", "e " + error.getMessage())) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> stringMap = new HashMap<>();
//                stringMap.put("UserId", "2");
//                stringMap.put("TermId", "1");
//                return stringMap;
//            }
//        };
//        requestQueue.add(stringRequest);
    }

    public void sendRequestByPostMethodPayment(String url, int price, int factorId, String tittlePay, String transactionCode,int resultCod, OnResult onResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tittlePay", tittlePay);
            jsonObject.put("price", price);
            jsonObject.put("factorId", factorId);
            jsonObject.put("transactionCode", transactionCode);
            jsonObject.put("resultCod", resultCod);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            int x = response.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //   txt1.setText("Error : "+error.getMessage());
                // prg1.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void sendRequestByPostMethodPaymentresult(String url, boolean ResultPayment,int paymentId, int factorId, OnResult onResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("resultPayment", ResultPayment);
            jsonObject.put("factorId", factorId);
            jsonObject.put("paymentId", paymentId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Boolean x = response.getBoolean("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //   txt1.setText("Error : "+error.getMessage());
                // prg1.setVisibility(View.INVISIBLE);
            }
        });
        requestQueue.add(jsonObjectRequest);
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

    public void Login(String userName, String password, OnResult onResult) {

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


        JSONObject factor = new JSONObject();
        try {

            factor.put("UserId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request=new JsonArrayRequest(
                Request.Method.POST,
                url,
                factor,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Factors>>(){}.getType();
                        factorsList=gson.fromJson(response.toString(),type);
                        onResult.success(factorsList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        requestQueue.add(request);



    }




    public void NewPassword(String url,int userid,String Pass, String newPass, OnResult onResult) {

        JSONObject user = new JSONObject();
        try {
            user.put("NewPassword", newPass);
            user.put("Password", Pass);
            user.put("UserId", userid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            aBoolean=response.getBoolean("result");
                            Log.i("result",aBoolean+"");
                            onResult.success(aBoolean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(request);


    }

    public void getNameFamily(OnResult onResult) {
        // user id from class G
        String name = "هانیه";
        String family = "پروین";
        //
        onResult.success(name + " " + family);
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
