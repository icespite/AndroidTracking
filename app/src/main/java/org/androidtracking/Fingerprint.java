package org.androidtracking;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.androidtracking.API1.BasicDeviceInfo;
import org.androidtracking.API2.AudioDeviceInfo;
import org.androidtracking.API3.SensorDeviceInfo;
import org.androidtracking.API4.WebAudioDeviceInfo;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Fingerprint {
    private String fingerprint;

    private final Context context;
    private final Activity activity;

    private JSONObject fingerprintInfo;

    public Fingerprint(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private JSONObject generateFingerprintInfo() throws JSONException {
        DeviceInfo info1 = new BasicDeviceInfo();
        DeviceInfo info2 = new AudioDeviceInfo();
        DeviceInfo info3 = new SensorDeviceInfo();
        DeviceInfo info4 = new WebAudioDeviceInfo();

        ((BasicDeviceInfo) info1).setContextActivity(context, activity);
        ((SensorDeviceInfo) info3).setContext(context);

        JSONObject info4fp = new JSONObject();
        info4fp = merge(info4fp, info1.getInfo());
        info4fp = merge(info4fp, info2.getInfo());
        JSONObject info3Res = info3.getInfo();
        Log.d("result", "info3Res: " + info3Res.toString());
        info4fp = merge(info4fp, info3Res);
        JSONObject info4Res = info4.getInfo();
        Log.d("result", "info4Res: " + info4Res.toString());
        info4fp = merge(info4fp, info4Res);

        return info4fp;
    }

    private JSONObject merge(JSONObject o1, JSONObject o2) throws JSONException {
        if (o2 == null)
            return o1;
        Iterator<String> keys = o2.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            o1.put(key, o2.get(key));
        }
        return o1;
    }

    public void generateFingerprint() throws JSONException {
        fingerprintInfo = generateFingerprintInfo();
        //TODO:生成指纹。注意指纹为成员变量
    }

    public JSONObject getFingerprintInfo() {
        return fingerprintInfo;
    }

    public String getFingerprint() {
        return fingerprint;
    }
}
