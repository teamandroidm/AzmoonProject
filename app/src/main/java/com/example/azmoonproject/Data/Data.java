package com.example.azmoonproject.Data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.azmoonproject.Model.Factors;
import com.example.azmoonproject.Model.Fields;
import com.example.azmoonproject.Model.Terms;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Data {
    private Context context;
    private RequestQueue requestQueue = null;
    private boolean isError = false;
    private boolean status = false;
    ArrayList<Factors> factorsList = new ArrayList<>();
    ArrayList<Fields> fieldsArrayList = new ArrayList<>();
    ArrayList<Terms> termsArrayList = new ArrayList<>();
    private static final String _URL1="http://192.168.1.102:8080/MyWebService/showalluser.php";
    public Data(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
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
    public void sendCourses(int userId, byte termdId, Date date, int price , OnResult onResult) {
        date = new Date();
//        if (userId == 1 && fieldId == 3) {
//            onResult.success(true);
//            return;
//        } else {
//            onResult.success(false);

//        }
    }
    public void SendRequestByPostMethodField(String url){
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

    public void SendRequestByPostMethodCourses(String url){
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

    public void sendByPostMethod(String url, JSONObject jsonObject){
//        JsonObjectRequest request=new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //      txtShowUsers1.setText("Ø§Ø·Ù„Ø§Ø¹Ø§Øª Ú©Ø§Ø±Ø¨Ø± Ø¨Ù‡ Ø±ÙˆØ´ post Ø¯Ø±Ø¬ Ø´Ø¯");
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



    public void Login(String userName, OnResult onResult) {

//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userName", userName);
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
//                                    G.user.setUserId(response.getInt("userId"));
//                                    G.user.setName(response.getString("name"));
//                                    G.user.setFamily(response.getString("family"));
//                                    G.user.setMobileNumber(response.getString("mobileNumber"));
//                                    G.user.setPassword(response.getString("password"));
//                                    G.user.setActive(response.getBoolean("isActive"));
//                                    G.user.setFieldId((byte) response.getInt("fieldId"));
//                                    status = true;
//                                } else {
//                                    G.user.setUserId(0);
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                isError = true;
//                            }
//                        });
//                        requestQueue.add(request);
//            }
//        });
//        thread.start();
//        onResult.success((boolean) isError, (boolean) status);


        //data fake
        G.user.setUserId(1);
        G.user.setName("admin");
        G.user.setFamily("admin");
        G.user.setMobileNumber("09181234567");
        G.user.setPassword("1234");
        G.user.setActive(true);
        G.user.setFieldId((byte) 1);
        onResult.success((boolean) false, (boolean) true);

    }

    public void getFactors(String url, OnResult onResult) {
        // user id from class G
        int userId = 2;
        //

//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson = new Gson();
//                        Type listType = new TypeToken<ArrayList<Factors>>() {
//                        }.getType();
//                        factorsList = gson.fromJson(response, listType);
//                    }
//                }
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(request);

        for (Factors factor : fakeDataFactor()) {
            if (factor.getUserId() == userId && factor.isFinally()) {
                factorsList.add(factor);
            }
        }
        onResult.success(factorsList);

    }

    public void getTermName(int termId, OnResult onResult) {
        ArrayList<Terms> termsList = fakeDataTerm();
        for (Terms terms : termsList
        ) {
            if (terms.getTermId() == termId)
                onResult.success(terms.getTermName());
        }

    }


    public void NewPassword(String url, String newPass, OnResult onResult) {
        // user id from class G
        int userId = 2;


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
        factors.setTermId((byte) 1);
        factors.setUserId(2);
        factors.setValidateTime(14);
        factorsArrayList.add(factors);

        /////////
        Factors factors1 = new Factors();
        factors1.setFactorId(2);
        factors1.setFinally(true);
        factors1.setFinallyDate(new Date(new Date().getTime() + (84600000 * 10)));
        factors1.setPrice(20000);
        factors1.setTermId((byte) 5);
        factors1.setUserId(2);
        factors1.setValidateTime(14);
        factorsArrayList.add(factors1);
        /////////////
        Factors factors2 = new Factors();
        factors2.setFactorId(3);
        factors2.setFinally(true);
        factors2.setFinallyDate(new Date(new Date().getTime() - 2000));
        factors2.setPrice(30000);
        factors2.setTermId((byte) 1);
        factors2.setUserId(3);
        factors2.setValidateTime(14);
        factorsArrayList.add(factors2);
        ////////////
        Factors factors3 = new Factors();
        factors3.setFactorId(4);
        factors3.setFinally(false);
        factors3.setFinallyDate(new Date(new Date().getTime() - 1000));
        factors3.setPrice(30000);
        factors3.setTermId((byte) 1);
        factors3.setUserId(2);
        factors3.setValidateTime(14);
        factorsArrayList.add(factors3);

        return factorsArrayList;

    }

    public ArrayList<Terms> fakeDataTerm() {
        ArrayList<Terms> termsArrayList = new ArrayList<>();
        Terms terms = new Terms();
        terms.setTermId(1);
        terms.setPrice(30000);
        terms.setTermName("جاوا");

        termsArrayList.add(terms);
        /////////
        Terms terms1 = new Terms();
        terms1.setTermId(5);
        terms1.setPrice(20000);
        terms1.setTermName("icdl");

        termsArrayList.add(terms1);

        return termsArrayList;

    }
}
