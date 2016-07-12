package com.astro73.timetrack;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

/**
 * TimeTrack application
 */
// TODO: Do this as a singleton per https://developer.android.com/reference/android/app/Application.html ?
public class TTApplication extends android.app.Application {
    private Manager manager;
    private static Context mContext;

    private static String TAG = "TTApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        try {
            manager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create Manager instance", e);
            return;
        }
    }
}
