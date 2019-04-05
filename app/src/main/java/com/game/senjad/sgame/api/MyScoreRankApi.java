package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class MyScoreRankApi extends BasicApi {
    public MyScoreRankApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return getBasicUrl()+"/"+ SharedPreferenceUtils.getInstance(getContext()).getToken()+"/my-score-rank";
    }
    public void start(){
        setMethod(Request.Method.POST);
        setRequestType(RequestType.JSON_OBJECT);
        super.start();
    }
}
