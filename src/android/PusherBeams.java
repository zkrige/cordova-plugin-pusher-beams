package com.centerhealth.plugin.pusher;

import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class PusherBeams extends CordovaPlugin {
    private static final String TAG = "PUSHER";
    JSONObject json = null;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("setUserId")) {
                String tokenUrl = data.getString(0);
                String userId = data.getString(1);
                String authToken = data.getString(2);

                if (tokenUrl == null || userId == null || authToken == null) {
                    callbackContext.error("Please provide all the information required");
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
                callbackContext.success(json);
                json = null;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            callbackContext.error(e.toString());
            e.printStackTrace();
        } finally {
            return true;
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

        Log.d(TAG, "STARTING PUSHER");

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent - starting");
        Bundle extras = intent.getExtras();

        if (extras != null) {
            json = new JSONObject();
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                try {
                    json.put(key, JSONObject.wrap(extras.get(key)));
                } catch (JSONException e) {
                    // Handle exception here
                }
            }
        }

    }

}
