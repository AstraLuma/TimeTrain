package com.astro73.timetrack;

import android.database.Cursor;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

/**
 * Base class for various implementations
 */
public abstract class EntryManager {

    enum Ordering {
        WHATEVER,
        START_OLDEST_FIRST,
        START_YOUNGEST_FIRST,
        END_OLDEST_FIRST,
        END_YOUNGEST_FIRST;
    }

    /**
     * Gets an iterable of entries for the last 24 hours.
     * @param order
     * @return
     */
    public abstract Iterable<TrackEntry> getEntries_24hr(Ordering order);

    /**
     * Saves the given entry, whether new or not.
     *
     * @param te
     */
    public abstract void save(TrackEntry te);

    /**
     * Gets the latest open entry. That is, the entry returned will not have an end time and will have
     * the highest start time of all entries without an end.
     *
     * May return null if there are no open entries.
     *
     * @return
     */
    public abstract TrackEntry getLatestOpen();

    /**
     * Closes the latest open entry and creates a new one.
     *
     * The new entry is saved.
     *
     * @param message
     */
    public TrackEntry closeAndCreate(String message, Date when) {
        // Note: This is a naive implementation. Others may want to do it differently.
        TrackEntry cur_ent = getLatestOpen();
        if (cur_ent != null) {
            cur_ent.end = when;
            save(cur_ent);
        }
        TrackEntry new_ent = new TrackEntry(message, when);
        save(new_ent);
        return new_ent;
    }
}
