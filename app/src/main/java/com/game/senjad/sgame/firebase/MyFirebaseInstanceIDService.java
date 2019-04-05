package com.game.senjad.sgame.firebase;

import android.util.Log;

import com.game.senjad.sgame.utils.SharedPreferenceUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG = "MyFirebaseInstanceIDSer";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        SharedPreferenceUtils.getInstance(getApplicationContext()).setMyFtoken(refreshedToken);
        SharedPreferenceUtils.getInstance(getApplicationContext()).setMyFtokenChange(true);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
}
