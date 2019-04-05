package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class FireBaseTokenApi extends BasicApi {

    public FireBaseTokenApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return getBasicUrl() + "/" + SharedPreferenceUtils.getInstance(getContext()).getToken() + "/firebase-token";
    }

    public void start(String text) {
        setMethod(Request.Method.POST);
        setRequestType(BasicApi.RequestType.JSON_OBJECT);
        addParam("token", text);
        super.start();
    }
}