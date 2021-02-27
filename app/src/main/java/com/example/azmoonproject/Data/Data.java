package com.example.azmoonproject.Data;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private final String BASE_URL = "http://mehdi899.ir/api/";
    ArrayList<Factors> factorsList = new ArrayList<>();
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    Utils utils;
    private Context context;
    private RequestQueue requestQueue = null;
    private boolean isError = false;
    private boolean status = false;
    private Boolean aBoolean = false;
    private ArrayList<Levels> questionLevelItemArrayList = null;

    public Data(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        utils = new Utils(context);
    }

    public Data() {
    }


    public void sendRequestByPostMethodField(OnResult onResult) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        BASE_URL+"FieldApi/PostField",
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
                                    Log.i("***", "e " + response);
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



    public void sendRequestByPostMethodCourses(OnResult onResult) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        BASE_URL+"TermsApi/UserTerm",
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


    public void sendRequestByPostMethodFactor( int userId, int termId, OnResult onResult) {
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
                        BASE_URL+"FactorApi/AddFactorId",
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int x = response.getInt("factorId");
                                    onResult.success(x);
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

    }

    public void sendRequestByPostMethodPayment( int price, int factorId, String tittlePay, String transactionCode, int resultCod, OnResult onResult) {
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
                BASE_URL+"PaymentApi/AddPayment",
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

    public void sendRequestByPostMethodPaymentresult( boolean ResultPayment, int paymentId, int factorId, OnResult onResult) {
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
                BASE_URL+"PaymentApi/PaymentResult",
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

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://mehdi899.ir/api/UserApi/Login",
                        jsonObject,
                        response -> {
                            try {
                                if (response.getInt("userId") > 0) {  // زمانی که کاربری در سیستم با نام وارد شده کاربر وجود داشته باشد
                                    utils.setSharedPreferences("userId", response.getInt("userId"));
                                    utils.setSharedPreferences("name", response.getString("name"));
                                    utils.setSharedPreferences("family", response.getString("family"));
                                    utils.setSharedPreferences("fieldId", (byte) response.getInt("fieldId"));
                                    status = true;
                                }
                                onResult.success(isError, status);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {isError = true;
                           onResult.success(isError, status);
                            });

                requestQueue.add(request);
            }
        });
        thread.start();

    }

    public void getFactors(int userId, OnResult onResult) {


        JSONObject factor = new JSONObject();
        try {

            factor.put("UserId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.POST,
                BASE_URL + "FactorApi/ShowFactor",
                factor,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Factors>>() {
                        }.getType();
                        factorsList = gson.fromJson(response.toString(), type);
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


    public void NewPassword(int userid, String Pass, String newPass, OnResult onResult) {

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
                BASE_URL + "UserApi/ChangePassword",
                user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            aBoolean = response.getBoolean("result");
                            Log.i("result", aBoolean + "");
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
        String name = (String) utils.getSharedPreferences("name", "هانیه");
        String family = (String) utils.getSharedPreferences("family", "پروین");
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
        new Thread(() -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, BASE_URL + "QuestionsApi/Azmoon", response -> {
                Questions[] questions;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    questions = new Questions[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Questions question = new Questions();
                        question.setQuestionText(jsonObject.getString("questionText"));
                        question.setOption1(jsonObject.getString("option1"));
                        question.setOption2(jsonObject.getString("option2"));
                        question.setOption3(jsonObject.getString("option3"));
                        question.setOption4(jsonObject.getString("option4"));
                        question.setTrueAnswer((byte) jsonObject.getInt("trueAnswer"));
                        questions[i] = question;
                    }
                    Log.i("###", "r: " + questions[0].getQuestionText());
                    onResult.success((Object) questions);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onResult.success((Object) null);
                }
            }, error -> {
                Log.i("###", "e: " + error.getMessage());
                onResult.success((Object) null);
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("termId", "14");
                    stringMap.put("levelCount", "1");
                    return stringMap;
                }
            };
            requestQueue.add(stringRequest);
        }).start();
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
