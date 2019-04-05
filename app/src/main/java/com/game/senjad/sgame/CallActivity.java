package com.game.senjad.sgame;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.base.api.BasicApiInterface;
import com.game.senjad.base.components.IranSansButton;
import com.game.senjad.base.components.IranSansEditText;
import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.sgame.api.FeedBackApi;

public class CallActivity extends AppBaseActivity implements BasicApiInterface{
    LinearLayout call;
    IranSansButton button;
    IranSansEditText message_text;
    FeedBackApi feedBackApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        setTitle("تماس با ما");
        setBackToolbarEnable();
        call = findViewById(R.id.call);
        button = findViewById(R.id.button);
        message_text = findViewById(R.id.message_text);
        feedBackApi = new FeedBackApi(this);
        feedBackApi.setApiInterface(this);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callClicked();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked();
            }
        });

    }

    private void buttonClicked() {
        feedBackApi.start(message_text.getText().toString());
    }

    private void callClicked() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("تماس با پشتیبانی")
                .setMessage("021...")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        call();
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.ic_call_black_24dp)
                .show();
    }
    private void call(){

        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:021"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CallActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        }else {
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(String response) {
        displayToast("درخواست با موفقیت ارسال شد.");
        finishActivity();
    }

    @Override
    public void onError(String message) {
        displayToast("ارسال درخواست با مشکل مواجه شده است.");
    }

    @Override
    public void onLogout() {
        exit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
}
