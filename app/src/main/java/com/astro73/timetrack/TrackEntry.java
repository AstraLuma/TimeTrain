package com.astro73.timetrack;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Simple container class for timesheet entries
 */
public class TrackEntry {
    public String description; // Not nullable, shouldn't be empty
    public Date start; // Not nullable
    public Date end; // Nullable (hasn't ended yet), must be later than start

    public TrackEntry(String description) {
        assert(description != null);
        this.description = description;
        this.start = new Date();
    }

    public TrackEntry(String description, Date start) {
        assert(description != null);
        assert(start != null);
        this.description = description;
        this.start = start;
    }

    public TrackEntry(String description, Date start, Date end) {
        assert(description != null);
        assert(start != null);
        assert(end == null || start.before(end));
        this.description = description;
        this.start = start;
        this.end = end;
    }

    public String toString() {
        if (end == null) {
            return description + " (" + start.toString() + ")";
        } else {
            return description + " (" + start.toString() + " - " + end.toString() + ")";
        }
    }

    public static TrackEntry[] getDummyArray() {
        DateFormat df = DateFormat.getInstance();
        Date d = new Date();
        System.out.println(df.format(d));
        try {
            return new TrackEntry[]{
                    new TrackEntry("Running", df.parse("7/11/16  8:00 AM"), df.parse("7/11/16  9:00 AM")),
                    new TrackEntry("Eating",  df.parse("7/11/16  9:00 AM"), df.parse("7/11/16 10:00 AM")),
                    new TrackEntry("Driving", df.parse("7/11/16 10:00 AM"), df.parse("7/11/16 11:00 AM")),
                    new TrackEntry("Working", df.parse("7/11/16 11:00 AM")),
            };
        } catch (ParseException e) {
            e.printStackTrace();
            assert(e == null);
            return null;
        }
    }
}
