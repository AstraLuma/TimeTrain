package com.astro73.timetrack;

import android.app.Application;

/**
 * Handles initialization of some application machinery, namely the InstanceManager().
 */
public class TTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InstanceManager.getInstance(getApplicationContext());
    }
}
