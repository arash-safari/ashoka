//package com.game.senjad.sgame.utils;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.webkit.WebView;
//
//import com.google.gson.Gson;
//
//public class SaveAndRetriveWebView {
//    Context context;
//    @SuppressLint("StaticFieldLeak")
//    private static SaveAndRetriveWebView ourInstance;
//
//    public static SaveAndRetriveWebView getInstance(Context context) {
//        if(ourInstance ==null) {
//            ourInstance = new SaveAndRetriveWebView(context);
//        }
//        return ourInstance;
//    }
//
//    private SaveAndRetriveWebView(Context context) {
//        this.context = context;
//    }
//    public void saveWebView(Object MyObject,String name){
//        SharedPreferences.Editor prefsEditor = getSharePreference().edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(MyObject);
//        prefsEditor.putString(name, json);
//        prefsEditor.apply();
//    }
//    private SharedPreferences getSharePreference(){
//        return context.getSharedPreferences("senjadPrefrences", Context.MODE_PRIVATE);
//    }
//    public boolean existWebViewObject(String name){
//        Gson gson = new Gson();
//        String json = getSharePreference().getString(name, "");
//        return !json.equals("");
//    }
//    public WebView getRetriveWebView(String name){
//        Gson gson = new Gson();
//        String json = getSharePreference().getString(name, "");
//        return gson.fromJson(json, WebView.class);
//    }
//}
