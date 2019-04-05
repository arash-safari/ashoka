package com.game.senjad.base.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.game.senjad.base.R;
import com.game.senjad.base.receiver.ConnectionInterface;
import com.game.senjad.base.utils.typeface.TypeFaceUtil;

import java.util.Stack;

import io.pcyan.sweetdialog.SweetDialog;


/**
 * Created by RGB on 6/1/2018.
 */

public class BaseActivity extends AppCompatActivity implements BaseUIInterface, ConnectionInterface {
    String TAG = getClass().getSimpleName();
    Stack<AlertDialog> dialogs = new Stack<>();
    static SweetDialog pDialog;
    @Override
    public void onNoConnection() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void displayToast(@NonNull String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayToast(int message) {

    }

    @Override
    public void displaySnackBar(@NonNull String message, String btnText) {

    }

    @Override
    public void displaySnackBar(int message, int btnText) {

    }

    @Override
    public void displaySnackBar(@NonNull String message) {

    }
    public void setBackToolbarEnable(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void displaySnackBar(int message) {

    }

    @Override
    public void displayMessageDialog( String message) {
        pDialog = SweetDialog.build();
        pDialog
        .isLoading(true,message)
        .showDialog(getSupportFragmentManager(),"progress_dialog");

    }

    public void dismissMessageDialog(){
        try {
            pDialog.dismiss();
        }catch (Exception e){
           e.printStackTrace();
        }
    }
    @Override
    public void displayMessageDialog(int title, int message) {

    }

    @Override
    public void displayYesNoDialog(String title, String description, String TAG) {

    }

    @Override
    public void displayYesNoDialog(int title, int description, String TAG) {

    }

    @Override
    public void displayYesNoDialog(String title, String description) {

    }

    @Override
    public void displayYesNoDialog(int title, int description) {

    }

    @Override
    public void finishActivity() {
        finish();
    }
    @Override
    public void startActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    @Override
    public void startActivity(Class c, Bundle bundle) {
        Intent intent = new Intent(this, c);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @SuppressLint("HardwareIds")
    @Override
    public String getDeviceId() {
        return Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void onSnackBarButtonClick() {

    }

    @Override
    public void onCancelClick(String tag) {

    }

    @Override
    public void onNoClick(String tag) {

    }

    @Override
    public void onYesClick(String tag) {

    }

    @Override
    public void onProgressCancel() {

    }


    @Override
    public void setStatusBarColor(int color) {
        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, color));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.background));
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.primary));
        }
    }
    @Override
    public void setTitle(int titleId) {
        String str = getString(titleId);
        setTitle(str);
    }

    @Override
    public void setTitle(CharSequence title) {
        SpannableString str = TypeFaceUtil.setTypeFace(this, title.toString());
        super.setTitle(str);
    }
}
