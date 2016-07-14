package com.astro73.timetrack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class RecentEntriesActivity extends AppCompatActivity {

    private EntryManager mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_entries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                InputDialog dlg = (new InputDialog(RecentEntriesActivity.this) {
                    @Override
                    protected void on_Ok(String s) {
                        mData.closeAndCreate(s, new Date());
                        refresh();
                    }
                });
                dlg.setTitle("New Entry Name"); // TODO: Better language here.
                // XXX: Do we also want TYPE_TEXT_VARIATION_SHORT_MESSAGE?
                dlg.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                dlg.show();
            }
        });

        mData = new ArrayEntryManager(Arrays.asList(TrackEntry.getDummyArray()));

        refresh();
    }

    private void refresh() {
        ListAdapter adapter = new ArrayAdapter<TrackEntry>(this,
                android.R.layout.simple_list_item_1,
                mkArray(mData.getEntries_24hr(EntryManager.Ordering.START_YOUNGEST_FIRST)));
        ListView listView = (ListView) findViewById(R.id.listview_entries);
        listView.setAdapter(adapter);
    }

    private <T> T[] mkArray(Iterable<T> iter) {
        ArrayList<T> rv = new ArrayList<>();
        for (T i : iter) {
            rv.add(i);
        }
        return (T[])rv.toArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_recent_entries, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void debugToSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
}
