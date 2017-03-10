package com.leoybkim.justdoit.fragments;


import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.activities.MainActivity;
import com.leoybkim.justdoit.data.TaskContract;

import java.util.Calendar;

import static com.leoybkim.justdoit.data.TaskProvider.LOG_TAG;

/**
 * Created by leo on 09/03/17.
 */

public class EditTaskFragment extends DialogFragment implements LoaderCallbacks<Cursor> {

    private View mView;
    private EditText mEditText;
    private Button mButton;
    private TextView mDueDate;
    private Uri mCurrentTaskUri;
    private RadioButton mRadioButton;

    // Identifier for the task data loader
    private static final int EXISTING_TASK_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Retrieve uri sent from MainActivity
        mCurrentTaskUri = getArguments().getParcelable("uri");

        // Inflate fragment
        mView = inflater.inflate(R.layout.activity_edit_item, container, false);
        mEditText = (EditText) mView.findViewById(R.id.editTask);
        mButton = (Button) mView.findViewById(R.id.update);
        mDueDate = (TextView) mView.findViewById(R.id.due_date);

        if (mCurrentTaskUri != null) {
            getLoaderManager().initLoader(EXISTING_TASK_LOADER, null, this);
        }

        mButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                update(v);
            }
        });

        mDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return mView;
    }

    // MainActivity passes new URI every click
    public static EditTaskFragment newInstance(Uri currentTaskUri) {
        EditTaskFragment frag = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable("uri", currentTaskUri);
        frag.setArguments(args);
        return frag;
    }

    public void update(View v) {
        // Read from input fields
        String taskString = mEditText.getText().toString().trim();
        String dueDateString = mDueDate.getText().toString();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, taskString);
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DUE_DATE, dueDateString);

        int rowsAffected = getActivity().getContentResolver().update(mCurrentTaskUri, values, null, null);

        if (rowsAffected == 0)
        {
            Toast.makeText(getActivity(), getString(R.string.update_task_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), getString(R.string.update_task_successful),
                    Toast.LENGTH_SHORT).show();
        }

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }

    public void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                String date = m + 1 + "/" + d + "/" + y;  // Month is indexed from 0
                mDueDate.setText(date);
            }
        }, year, month, day);
        datePicker.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Database query
        String[] projection = {
                TaskContract.TaskEntry._ID,
                TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskContract.TaskEntry.COLUMN_TASK_DUE_DATE,
                TaskContract.TaskEntry.COLUMN_TASK_PRIORITY };

        // This Loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(), mCurrentTaskUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            int descriptionColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION);
            int dueDateColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DUE_DATE);

            String description = cursor.getString(descriptionColumnIndex);
            String dueDate = cursor.getString(dueDateColumnIndex);

            Log.d(LOG_TAG, String.valueOf(dueDateColumnIndex));


            mEditText.setText(description);
            mEditText.setSelection(mEditText.length());   // move cursor to the end of the text

            if (dueDate != null) {
                mDueDate.setText(dueDate);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
