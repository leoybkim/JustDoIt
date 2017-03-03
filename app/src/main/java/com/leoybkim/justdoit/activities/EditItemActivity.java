package com.leoybkim.justdoit.activities;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.data.TaskContract;

public class EditItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    EditText editText;
    Uri mCurrentTaskUri;
    private final static String LOG_TAG = EditItemActivity.class.getSimpleName();

    /** Identifier for the task data loader */
    private static final int EXISTING_TASK_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Find all relevant views that we will need to read user input from
        editText = (EditText) findViewById(R.id.editText2);

        Intent intent = getIntent();
        mCurrentTaskUri = intent.getData();
        Log.d(LOG_TAG, mCurrentTaskUri.toString());
        if (mCurrentTaskUri != null) {
            getLoaderManager().initLoader(EXISTING_TASK_LOADER, null, this);
        }
    }

    public void update(View v) {
        editText = (EditText) findViewById(R.id.editText2);
        // Read from input fields
        String taskString = editText.getText().toString().trim();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, taskString);

        int rowsAffected = getContentResolver().update(mCurrentTaskUri, values, null, null);

        if (rowsAffected == 0)
        {
            Toast.makeText(this, getString(R.string.update_task_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.update_task_successful),
                    Toast.LENGTH_SHORT).show();
        }

        Intent i = new Intent(EditItemActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Database query
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION };

        // This Loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                mCurrentTaskUri, // The content
                projection,                       // The column to return for each row
                null,                             // Selection criteria
                null,                             // Selection criteria
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            int descriptionColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION);
            String description = cursor.getString(descriptionColumnIndex);
            editText.setText(description);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
