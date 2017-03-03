package com.leoybkim.justdoit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.models.Task;

import java.util.ArrayList;

/**
 * Created by leo on 03/03/17.
 */

public class TaskAdapter extends ArrayAdapter<Task> {
    private final String LOG_TAG = TaskAdapter.class.getSimpleName();

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        Task currentTask = getItem(position);
        TextView description = (TextView) listItemView.findViewById(R.id.description);
        description.setText(currentTask.description);
        Log.d(LOG_TAG, currentTask.description);
        return listItemView;
    }
}