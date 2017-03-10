package com.leoybkim.justdoit.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.data.TaskContract;

/**
 * Created by leo on 03/03/17.
 */

public class TaskCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link TaskCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public TaskCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    }

    /**
     * This method binds the task data (in the current row pointed to by cursor) to the given
     * list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find view that needs to be modified
        TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

        // Find columns of task attributes of interest
        int descriptionColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION);
        int dueDateColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_DUE_DATE);
        int priorityColumnIndex = cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_TASK_PRIORITY);

        // Read the task attribute from the cursor of the current task
        String taskDescription = cursor.getString(descriptionColumnIndex);
//        String taskDueDate = cursor.getString(dueDateColumnIndex);
        // TODO: update textview radio button fro priority and duedate

        // Set TextView
        descriptionTextView.setText(taskDescription);
    }

}

