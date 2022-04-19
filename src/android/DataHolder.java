package com.centerhealth.plugin.pusher;

import org.json.JSONObject;

import java.lang.reflect.Array;

public class DataHolder {
    private JSONObject data;
    public JSONObject getData() {return data;}
    public void setData(JSONObject data) {this.data = data;}

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}
