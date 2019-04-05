package com.game.senjad.sgame.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferenceUtils ourInstance;

    public static SharedPreferenceUtils getInstance(Context context) {
        if(ourInstance==null){
            ourInstance = new SharedPreferenceUtils(context);
        }
        return ourInstance;
    }

    private SharedPreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("senjadPrefrences",Context.MODE_PRIVATE);
    }
    public long getMyId(){
        return mSharedPreferences.getLong("M_ID",0);
    }
    public void setMyId(long id){
        mSharedPreferences.edit().putLong("M_ID",id).apply();
    }
    public String getToken(){
        return mSharedPreferences.getString("Token","");
    }
    public void setToken(String token){
        mSharedPreferences.edit().putString("Token",token).apply();
    }
    public void setMyFtoken(String token){
        mSharedPreferences.edit().putString("F_TOKEN",token).apply();
    }
    public void setMyFtokenChange(boolean b){
        mSharedPreferences.edit().putBoolean("F_TOKEN_B",b).apply();
    }
    public boolean getMyFtokenChange(){
        return mSharedPreferences.getBoolean("F_TOKEN_B",false);
    }
    public String getMyFtoken(){
        return mSharedPreferences.getString("F_TOKEN","");
    }

}
