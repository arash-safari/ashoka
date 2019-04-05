package com.game.senjad.sgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.game.senjad.base.fragment.BaseFragment;
//import com.game.senjad.sgame.utils.SaveAndRetriveWebView;

import io.pcyan.sweetdialog.SweetDialog;


public class GameFragment extends BaseFragment {
    private GameActivity gameActivity;
    private String link;
    private WebView webView;
    private static final String TAG = "GameFragment";
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = (GameActivity) getActivity();
        gameActivity.displayMessageDialog(getString(R.string.bargozari));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                gameActivity.dismissMessageDialog();
            }
        }, 5000);

        gameActivity.setTitle("بازی");
        gameActivity.setOnRefreshInterface(new GameActivity.OnRefresh() {
            @Override
            public void onRefresh() {
                startWebView();
            }
        });
        link = gameActivity.getLink();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        startWebView();
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        return view;
    }

    private void startWebView() {
        if(view==null){
            if(getActivity()!=null)
            getActivity().finish();
        }
        if(webView==null) {
//            SaveAndRetriveWebView saveAndRetriveWebView = SaveAndRetriveWebView.getInstance(getContext());
//            if(saveAndRetriveWebView.existWebViewObject(link)){
//                webView = saveAndRetriveWebView.getRetriveWebView(link);
//            }
            webView = view.findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
        }
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
//                SaveAndRetriveWebView.getInstance(getContext()).saveWebView(webView,link);
                gameActivity.dismissMessageDialog();
                Log.d(TAG, "onPageFinished: " + url);
            }});
    }

    @Override
    public void onPause() {
        if(webView!=null) {
//            SaveAndRetriveWebView.getInstance(getContext()).saveWebView(webView,link);
            webView.onPause();
            webView.pauseTimers();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(webView!=null) {
//            SaveAndRetriveWebView.getInstance(getContext()).saveWebView(webView,link);
            webView.destroy();
        }
        webView = null;
        super.onStop();
    }
}
