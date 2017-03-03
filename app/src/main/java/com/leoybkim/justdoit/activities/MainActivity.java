package com.leoybkim.justdoit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.leoybkim.justdoit.R;
import com.leoybkim.justdoit.adapters.TaskAdapter;
import com.leoybkim.justdoit.models.Task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> fileLines;
//    ArrayAdapter<String> itemsAdapter;
    ArrayList<Task> tasks;
    TaskAdapter mAdapter;
    ListView listView;
    private final int REQUEST_CODE = 200;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        readItems();
        Log.d(LOG_TAG, tasks.toString());
        mAdapter = new TaskAdapter(this, tasks);
        listView.setAdapter(mAdapter);

        setUpListViewListener();
    }

    public void onAddItem(View v) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String taskDescription = editText.getText().toString();
        mAdapter.add(new Task(taskDescription));
        editText.setText("");
        try {
            writeItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String newText = data.getExtras().getString("newText");
            int index = data.getExtras().getInt("index");
            tasks.get(index).description = newText;
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setUpListViewListener() {
        // Edit on click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("text", tasks.get(pos).description);
                i.putExtra("index", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        // Delete on long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                tasks.remove(pos);
                mAdapter.notifyDataSetChanged();
                try {
                    writeItems();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile =  new File(filesDir, "todo.txt");

        try {
            tasks = new ArrayList<>();
            fileLines = new ArrayList<String>(FileUtils.readLines(todoFile));
            for (String lines: fileLines) {
                tasks.add(new Task(lines));
            }
        } catch (IOException e) {
            tasks = new ArrayList<Task>();
        }
    }

    private void writeItems() throws IOException {
        File filesDir = getFilesDir();
        File todoFile =  new File(filesDir, "todo.txt");
        FileOutputStream fos = new FileOutputStream(todoFile);

        OutputStreamWriter osw = new OutputStreamWriter(fos);

        for (Task task: tasks) {
            osw.write(task.description+"\n");
        }
        osw.close();
    }
}
