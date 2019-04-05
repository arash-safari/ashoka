package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import org.json.JSONObject;

public class GetMeApi extends BasicApi {
    public GetMeApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {

        return getBasicUrl()+"/"+ SharedPreferenceUtils.getInstance(getContext()).getToken()+"/get-me";
    }
    public void start(){
        setMethod(Request.Method.POST);
        setRequestType(RequestType.JSON_OBJECT);
        super.start();
    }
}
