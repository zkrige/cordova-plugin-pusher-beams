package za.co.apextechnology.pusher.beams;

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

public class Pusher extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("registerUserId")) {
            String userId = data.getString(0);
            String authToken = data.getString(1);
            registerUserId(userId, authToken);
            callbackContext.success("");

            return true;

        } else {

            return false;

        }
    }

    private void registerUserId(String userId, String authToken) {
        BeamsTokenProvider tokenProvider = new BeamsTokenProvider(
                "http://pager.testing.medicom.care/api/v1/user/beams-token",
                new AuthDataGetter() {
                    @Override
                    public AuthData getAuthData() {
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
                }
        );

        PushNotifications.setUserId("userId", tokenProvider, new BeamsCallback<Void, PusherCallbackError>(){
            @Override
            public void onSuccess(Void... values) {
            }

            @Override
            public void onFailure(PusherCallbackError error) {
            }
        });
    }
}
