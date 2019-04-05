package com.game.senjad.base.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.game.senjad.base.api.BasicApi.RequestType.JSON_ARRAY;
import static com.game.senjad.base.api.BasicApi.RequestType.JSON_OBJECT;
import static com.game.senjad.base.api.BasicApi.RequestType.STRING;

/**
 * Created by RGB on 6/1/2018.
 */

public abstract class BasicApi implements Response.ErrorListener {

    public static final String AUTHORISATION = "X-API-TOKEN";
    public static final String VERSION = "version";

    protected String basicUrl = "http://app.senjad.com/aapi";

    protected String notAuthenticatedUrl = "https://mobin.co.ir/appapi/";
    private BasicApiInterface mApiInterface;
    public enum RequestType {
        JSON_OBJECT, JSON_ARRAY, STRING
    }

    private RequestType requestType = STRING;

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * TAG for log
     */
    private final String TAG = getClass().getSimpleName();
    /**
     * context for create RequestQueue
     */
    protected final Context context;
    private JSONObject jsonParams = new JSONObject();
    private int MY_SOCKET_TIMEOUT_MS = 10000;
    private RetryPolicy policy = new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    /**
     * volley RequestQueue
     */
    private RequestQueue mRequestQueue;

    /**
     * request method
     */
    private int method = Request.Method.GET;
    /**
     * headers of the request
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * post or patch parameters
     */
    private Map<String, String> params = new HashMap<>();


    public abstract String getUrl();

    public Context getContext() {
        return context;
    }

    public BasicApi(Context context) {
        this.context = context;
        int version = 1;
        try {
            version = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName()
                            ,PackageManager.GET_META_DATA).metaData.getInt("version");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        addParam(VERSION,version+"");
    }

    public String getBasicUrl() {
        return basicUrl;
    }
    public void setMethod(int method) {
        this.method = method;
    }

    public void addParam(String key, String value) {
        Log.d(TAG, "addParam: " + key + value);
        params.put(key, value);
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    protected void addHeader(String key, String value) {
        headers.put(key, value);
    }
    public static String getAppVersion(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }
    /**
     * when request fails this method is called
     *
     * @param error is error of request that should be determined
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: ");
        error.printStackTrace();
        reset();
        if (error != null && error.networkResponse != null) {
            Log.d(TAG, "error Response: " + new String(error.networkResponse.data));
            try {
                JSONObject jsonObject = new JSONObject(new String(error.networkResponse.data));
                int err = jsonObject.getInt("err");
                mApiInterface.onError("");
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (error instanceof NoConnectionError) {
            Log.d(TAG, "onErrorResponse: no connection");
            mApiInterface.onError("context.getString(R.string.error_noConnection)");
            return;
        }
        if (error instanceof TimeoutError) {
            Log.d(TAG, "onErrorResponse: Timeout Error");
            mApiInterface.onError("context.getString(R.string.error_timeout)");
            return;
        }
        if (error instanceof ServerError) {
            Log.d(TAG, "onErrorResponse: server error");
            if (error.networkResponse != null)
                Log.d(TAG, new String(error.networkResponse.data));
            mApiInterface.onError("context.getString(R.string.error_server)");
            return;
        }
        if (error instanceof NetworkError) {
            Log.d(TAG, "onErrorResponse: network error");
            mApiInterface.onError("context.getString(R.string.error_network)");
            return;
        }
        if (error instanceof AuthFailureError) {
            mApiInterface.onLogout();
            return;
        }
        if (error == null || error.networkResponse == null) {
            Log.d(TAG, "onErrorResponse: unknown error");
            mApiInterface.onError("context.getString(R.string.error_unknown)");
            return;
        }
        //// TODO: 6/30/2017 error should be handled
        if (error.networkResponse.statusCode == 401) {
            mApiInterface.onLogout();
        }

        mApiInterface.onError("");
    }

    protected RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }
    private Response.Listener<String> stringListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            mApiInterface.onResponse(response);
            reset();
        }
    };

    public void setApiInterface(BasicApiInterface mApiInterface) {
        this.mApiInterface = mApiInterface;
    }

    public JSONObject getJsonParams() {

        for (String key : params.keySet()) {
            try {
                jsonParams.put(key, params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonParams;
    }

    private Response.Listener<JSONObject> jsonListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.d(TAG, "jsonObject onResponse: " + response);
            try {
                if(!response.getBoolean("status") && response.getJSONArray("validations").length()>0 &&
                        response.getJSONArray("validations")
                        .getJSONObject(0).getString("code").equals("user_not_found_token")){
                    mApiInterface.onLogout();
                }else {
                    mApiInterface.onResponse(response.toString());
                    reset();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
    private void sendStringRequest() {
        Log.d("PostParams", jsonParams.toString());

        StringRequest request = new StringRequest(method, getUrl(), stringListener, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        request.setRetryPolicy(policy);
        getRequestQueue().add(request);
    }

    private void sendJSONObjectRequest() {
        Log.d("jsonPostParams", getJsonParams().toString());
        JsonObjectRequest request = new JsonObjectRequest(method, getUrl(), getJsonParams(), jsonListener, this) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };
        request.setRetryPolicy(policy);
        getRequestQueue().add(request);
    }

    public void start() {
        Log.d(TAG, "url: " + getUrl());
        switch (requestType) {
            case STRING:
                sendStringRequest();
                break;
            case JSON_ARRAY:
                break;
            case JSON_OBJECT:
                sendJSONObjectRequest();
                break;
        }
    }

    protected void reset() {
        method = Request.Method.GET;
        headers.clear();
        params.clear();
    }
}

