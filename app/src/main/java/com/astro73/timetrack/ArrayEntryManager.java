package com.astro73.timetrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Basic storage that just uses an in-memory array. Nothing else.
 */
public class ArrayEntryManager extends EntryManager {

    private List<TrackEntry> mData;

    public ArrayEntryManager() {
        mData = new ArrayList<>();
    }

    public ArrayEntryManager(Iterable<? extends TrackEntry> data) {
        this();
        for (TrackEntry te : data) {
            mData.add(te);
        }
    }

    @Override
    public Iterator<TrackEntry> getEntries(Ordering order) {
        if (order == Ordering.WHATEVER) {
            return mData.iterator();
        }

        List<TrackEntry> copy = new ArrayList<>(mData);
        Comparator<TrackEntry> cmp;

        switch(order) {
            /*case WHATEVER:
                throw new RuntimeException("Already tested for WHATEVER.");*/
            case START_OLDEST_FIRST:
                cmp = new TrackEntry.CompareStartAscending();
                break;
            case START_YOUNGEST_FIRST:
                cmp = new TrackEntry.CompareStartDescending();
                break;
            case END_OLDEST_FIRST:
                cmp = new TrackEntry.CompareEndAscending();
                break;
            case END_YOUNGEST_FIRST:
                cmp = new TrackEntry.CompareEndDescending();
                break;
            default:
                throw new RuntimeException("Unknown value for order: "+order);
        }

        Collections.sort(copy, cmp);
        return copy.iterator();
    }

    @Override
    public void save(TrackEntry te) {

    }

    @Override
    public TrackEntry getLatestOpen() {
        return null;
    }
}
