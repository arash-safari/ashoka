package com.game.senjad.sgame.api;

import android.content.Context;

import com.android.volley.Request;
import com.game.senjad.base.api.BasicApi;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

public class RankingApi extends BasicApi {

    public RankingApi(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return getBasicUrl()+"/"+ SharedPreferenceUtils.getInstance(getContext()).getToken()+"/game-score-list";
    }
    public void start(long gameId,int page){
        setMethod(Request.Method.POST);
        setRequestType(RequestType.JSON_OBJECT);
        addParam("game_id",gameId+"");
        addParam("page",page+"");
        super.start();
    }
}
