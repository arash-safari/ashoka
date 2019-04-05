package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class FeedBackApi extends BasicApi {

    public FeedBackApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return getBasicUrl()+"/"+ SharedPreferenceUtils.getInstance(getContext()).getToken()+"/feedback";
    }
    public void start(String text){
        setMethod(Request.Method.POST);
        setRequestType(BasicApi.RequestType.JSON_OBJECT);
        addParam("text",text);
        super.start();
    }
}

