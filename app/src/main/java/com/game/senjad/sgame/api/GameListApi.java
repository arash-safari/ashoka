package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class GameListApi extends BasicApi {
    public GameListApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return  getBasicUrl()+"/"+ SharedPreferenceUtils.getInstance(getContext()).getToken()+"/game-list";
    }
    public void start(){
        setMethod(Request.Method.POST);
        setRequestType(RequestType.JSON_OBJECT);
        super.start();
    }
}
