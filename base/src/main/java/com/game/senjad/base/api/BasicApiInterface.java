package com.game.senjad.base.api;

/**
 * Created by RGB on 6/1/2018.
 */

public interface BasicApiInterface {
    /**
     * response for request to api
     *
     * @param response is String response from server
     */
    void onResponse(String response);

    /**
     * error for requesto to api
     *
     * @param message is Error message to display
     */
    void onError(String message);
    void onLogout();
}
