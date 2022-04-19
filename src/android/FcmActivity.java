package com.centerhealth.plugin.pusher;

import com.centerhealth.plugin.pusher.DataHolder;

import android.app.Activity;
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

}
