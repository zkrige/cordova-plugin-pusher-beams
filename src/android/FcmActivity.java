
package com.centerhealth.plugin.pusher;

import com.centerhealth.plugin.pusher.DataHolder;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Set;

public class FcmActivity extends Activity {
    final String TAG = "NEWWWW";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting activity");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent - starting");
        Bundle extras = intent.getExtras();

        if (extras != null) {
            JSONObject json = new JSONObject();
            Set<String> keys = extras.keySet();
            for (String key : keys) {
                try {
                    json.put(key, JSONObject.wrap(extras.get(key)));
                } catch (JSONException e) {
                    // Handle exception here
                }
            }
            try {
                JSONObject pusher = json.getJSONObject("pusher");
                if (pusher != null) {
                    DataHolder.getInstance().setData(pusher);
                }
            } catch (JSONException e) {
                // Handle exception here
            }
        }

    }
}
