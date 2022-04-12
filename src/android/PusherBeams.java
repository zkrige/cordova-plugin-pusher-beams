package com.centerhealth.plugin.pusher;

import android.util.Log;

import com.pusher.pushnotifications.BeamsCallback;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.pushnotifications.PusherCallbackError;
import com.pusher.pushnotifications.auth.AuthData;
import com.pusher.pushnotifications.auth.AuthDataGetter;
import com.pusher.pushnotifications.auth.BeamsTokenProvider;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class PusherBeams extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Context context = this.cordova.getActivity().getApplicationContext();
        Log.i("XXX", "initialize: ${context} ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("XXX", "onStart: ");
    }
    
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("setUserId")) {
            String tokenUrl = data.getString(0);
            String userId = data.getString(1);
            if (userId == null) {
                return true;
            }
            String authToken = data.getString(2);
            if (authToken.equals("null")) {
                return true;
            }
            registerUserId(tokenUrl, userId, authToken);
            callbackContext.success("");
            return true;
        } else if (action.equalsIgnoreCase("clear")){
            PushNotifications.clearAllState();
            return true;
        } else {
            return false;
        }
    }

    private void registerUserId(String tokenUrl, String userId, String authToken) {
        BeamsTokenProvider tokenProvider = new BeamsTokenProvider(tokenUrl,
                () -> {
                    // Headers and URL query params your auth endpoint needs to
                    // request a Beams Token for a given user
                    HashMap<String, String> headers = new HashMap<>();
                    // for example:
                    headers.put("Authorization", "Bearer " + authToken);
                    HashMap<String, String> queryParams = new HashMap<>();
                    return new AuthData(
                            headers,
                            queryParams
                    );
                }
        );

        PushNotifications.setUserId(userId, tokenProvider, new BeamsCallback<Void, PusherCallbackError>(){
            @Override
            public void onSuccess(Void... values) {
                Log.v("", "success");
            }

            @Override
            public void onFailure(PusherCallbackError error) {
                Log.v("","fail");
            }
        });
    }
}
