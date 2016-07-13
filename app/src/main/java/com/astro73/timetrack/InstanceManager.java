package com.astro73.timetrack;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private static String DATABASE = "timesheet";

    public Database getDatabase() {
        // This is only for development to populate demo data
        if (!getDbManager().getAllDatabaseNames().contains(DATABASE)) {
            try {
                Database db = getDbManager().getDatabase(DATABASE);
                for (TrackEntry te : TrackEntry.getDummyArray()) {
                    Map<String, Object> properties = new HashMap<String, Object>();
                    properties.put("_id", te.start.getTime());
                    properties.put("description", te.description);
                    properties.put("start", te.start.getTime());
                    properties.put("end", te.start.getTime());
                    Document doc = db.createDocument();
                    doc.putProperties(properties);
                }
            } catch (CouchbaseLiteException e) {
                throw new RuntimeException("Couldn't create default data", e);
            }
        }
        try {
            return getDbManager().getDatabase(DATABASE);
        } catch (CouchbaseLiteException e) {
            // FIXME: How do we handle this?
            throw new RuntimeException("Can't open database", e);
        }
    }
}
