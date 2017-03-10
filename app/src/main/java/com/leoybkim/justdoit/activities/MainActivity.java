package com.leoybkim.justdoit.activities;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.adapters.TaskAdapter;
import com.leoybkim.justdoit.adapters.TaskCursorAdapter;
import com.leoybkim.justdoit.data.TaskContract;
import com.leoybkim.justdoit.fragments.EditTaskFragment;

;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listView;
    EditText editText;
    TaskAdapter mAdapter;
    TaskCursorAdapter mCursorAdapter;
    private Uri mCurrentTaskUri;
    private static final int REQUEST_CODE = 200;
    private static final int TASK_LOADER = 0;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        mCursorAdapter = new TaskCursorAdapter(this, null);
        listView.setAdapter(mCursorAdapter);

        // Kick off the Loader
        getSupportLoaderManager().initLoader(REQUEST_CODE, null, this);

        setUpListViewListener();
    }

    public void setUpListViewListener() {
        // Edit on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                // EditItemActivity --> Fragments
//                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
//                Uri currentTaskUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
//                i.setData(currentTaskUri);
//                startActivity(i);

                // gets the current Task
                Uri currentTaskUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);

                // initialize fragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager();
                EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(currentTaskUri);
                editTaskFragment.show(fragmentManager, "test fragment");

            }
        });

        // Delete on long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                showDeleteConfirmationDialog(id);
                return true;
            }
        });
    }

    public void onAddItem(View v) {
        editText = (EditText) findViewById(R.id.editText);
        // Read from input fields
        String taskString = editText.getText().toString().trim();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, taskString);

        if (mCurrentTaskUri == null) {
            // Insert a new task into the provider, returning the content URI for the new task.
            Uri newUri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.insert_task_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_task_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        editText.setText("");
    }

    private void showDeleteConfirmationDialog(long id) {
        final long taskId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteTask(taskId);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteTask(long id) {
        Uri currentTaskUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
        if(currentTaskUri != null) {
            int rowsDeleted = getContentResolver().delete(currentTaskUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.delete_task_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.delete_task_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Database query
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION };

        // This Loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,
                TaskContract.TaskEntry.CONTENT_URI, // The content
                projection,                       // The column to return for each row
                null,                             // Selection criteria
                null,                             // Selection criteria
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Update with new cursor
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback is called when data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}
