package com.game.senjad.sgame;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import org.greenrobot.essentials.StringUtils;

import java.util.StringTokenizer;

public class RegisterActivity extends AppBaseActivity {
    private static final String TAG = "RegisterActivity";
    WebView webView ;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("ورود");
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        startWebView();
    }

    private void startWebView() {
        webView.loadUrl("http://app.senjad.com/user/auth/open-id");
        displayMessageDialog("در حال بارگیری");
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                dismissMessageDialog();
                Log.d(TAG, "onPageFinished: " + url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (url.equals("senjad://app.senjad.com")) {
                    String cookies = CookieManager.getInstance().getCookie(webView.getUrl());
                    StringTokenizer tokenizer = new StringTokenizer(cookies,";");
                    while (tokenizer.hasMoreTokens()){
                        String splitString = tokenizer.nextToken().replaceAll("\\s","");
                        if(splitString.substring(0,3).equals("ut=")){
                            token = splitString.substring(3);
                            SharedPreferenceUtils.getInstance(RegisterActivity.this).setToken(token);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                CookieManager.getInstance().removeAllCookies(new ValueCallback<Boolean>() {
                                    @Override
                                    public void onReceiveValue(Boolean aBoolean) {

                                    }
                                });
                            }else {
                                CookieManager.getInstance().removeAllCookie();
                            }
                        }
                    }
                    startActivity(MainActivity.class);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){
            startWebView();
        }
        return super.onOptionsItemSelected(item);
    }
}
