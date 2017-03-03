package com.leoybkim.justdoit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.leoybkim.justdoit.R;

public class EditItemActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Read data from intent extras
        String text = getIntent().getExtras().getString("text");
        editText = (EditText) findViewById(R.id.editText2);
        editText.setText(text);
    }

    public void onSaveItem(View v) {
        String newText = editText.getText().toString();
        Intent i = new Intent();
        i.putExtra("newText", newText);
        setResult(RESULT_OK, i);
        finish();
    }
}
