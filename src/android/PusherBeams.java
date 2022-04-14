package com.centerhealth.plugin.pusher;

import android.util.Log;
import android.content.Context;

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
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("setUserId")) {
                String tokenUrl = data.getString(0);
                String userId = data.getString(1);
                String authToken = data.getString(2);

                if (tokenUrl == null || userId == null || authToken == null) {
                    callbackContext.error("Could not continue , missing some information");
                }
                registerUserId(tokenUrl, userId, authToken, callbackContext);
                return true;
            } else if (action.equalsIgnoreCase("start")) {
                String instanceId = data.getString(0);
                if (instanceId == null) {
                    callbackContext.error("Please provide InstanceID");
                }
                startPusher(instanceId);
                callbackContext.success("OK");
                return true;
            } else if (action.equalsIgnoreCase("clear")) {
                PushNotifications.clearAllState();
                callbackContext.success("OK");
                return true;
            } else if (action.equalsIgnoreCase("getNotification")) {
                // TODO: implement
                // PushNotifications.clearAllState();
                // callbackContext.success("OK");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            callbackContext.error(e.toString());
            e.printStackTrace();
        }
    }

    private void registerUserId(String tokenUrl, String userId, String authToken, CallbackContext cb) {
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
                            queryParams);
                });

        PushNotifications.setUserId(userId, tokenProvider, new BeamsCallback<Void, PusherCallbackError>() {
            @Override
            public void onSuccess(Void... values) {
                cb.success("OK");
                Log.v("", "success");
            }

            @Override
            public void onFailure(PusherCallbackError error) {
                cb.error("failed to set user id");
                Log.v("", "fail");
            }
        });
    }

    private void startPusher(String instanceId) {
        Context context = this.cordova.getActivity().getApplicationContext();
        PushNotifications.start(context, instanceId);

        // Debug mode
        PushNotifications.addDeviceInterest("debug-hello");

    }
}
