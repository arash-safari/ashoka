package com.game.senjad.sgame;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;

import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class AppBaseActivity extends BaseActivity {
    private static final String TAG = "AppBaseActivity";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.game.senjad.sgame.R.menu.menu_refresh, menu);
        return true;
    }
    public void exit(){
        SharedPreferenceUtils.getInstance(this).setToken("");
        Intent intent = new Intent(getApplicationContext(),SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
