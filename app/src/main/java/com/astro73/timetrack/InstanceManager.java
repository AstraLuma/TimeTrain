package com.astro73.timetrack;

import android.content.Context;

import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

/**
 * Central place to manage singletons. May eventually become IOC/whatever.
 */
public class InstanceManager {
    private static InstanceManager mInstance;

    public static InstanceManager getInstance(Context ctx) {
        mInstance = new InstanceManager(ctx);
        return mInstance;
    }

    public static InstanceManager getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("Attempted to use InstanceManager before application creation");
        }
        return mInstance;
    }

    private Context mContext;

    private InstanceManager(Context context) {
        mContext = context;
    }

    /**
     * Really for internal use, but why not.
     * @return Context.getApplicationContext()
     */
    public Context getContext() {
        return mContext;
    }

    private Manager manager;

    public Manager getDbManager() {
        if (manager == null) {
            try {
                manager = new Manager(new AndroidContext(getContext()), Manager.DEFAULT_OPTIONS);
            } catch (IOException e) {
                // FIXME: How do we handle this??
                throw new RuntimeException("Can't create database manager", e);
            }
        }
        return manager;
    }

}
