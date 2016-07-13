package com.astro73.timetrack;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Handles popping up a basic input.
 */
public abstract class InputDialog {
    protected final AlertDialog.Builder builder;
    protected final EditText input;

    public InputDialog(Context ctx) {
        builder = new AlertDialog.Builder(ctx);

// Set up the input
        input = new EditText(ctx);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                on_Ok(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                on_Cancel();
            }
        });
    }

    /**
     * Optional event handler for if the dialog is cancelled. Called after the dialog itself is cancelled.
     *
     * By default, does nothing.
     */
    protected void on_Cancel() {
    }

    /**
     * Event handler for when the user inputs text.
     *
     * @param s The text the user inputted.
     */
    protected abstract void on_Ok(String s);

    /**
     * Sets the title of the dialog.
     *
     * @param title
     */
    public void setTitle(String title) {
        builder.setTitle(title);
    }

    public void setMessage(String msg) {
        builder.setMessage(msg);
    }

    /**
     * Sets the inputType of the EditText.
     *
     * @param type See https://developer.android.com/reference/android/view/inputmethod/EditorInfo.html#inputType
     */
    public void setInputType(int type) {
        input.setInputType(type);
    }

    /**
     * Shows the dialog.
     */
    public void show() {
        builder.show();
    }
}
