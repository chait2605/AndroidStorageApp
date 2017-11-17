package com.android.androidstorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteView extends AppCompatActivity {

    private EditText desc;
    private int counter = 0;
    private SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");
    private static final String SQL_COUNTER = "SQL_COUNTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_view);
        desc = (EditText) findViewById(R.id.desc_text);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPrefs.getInt(SQL_COUNTER, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPrefs.getInt(SQL_COUNTER, 0);
    }

    /**
     * Helper function to save SQLite
     * @param v
     */
    public void saveSQLite(View v) {
        String blog_message = desc.getText().toString();
        if (blog_message != null) {
            StorageController dataController = new StorageController(getBaseContext());
            dataController.open();
            long retValue = dataController.insert(blog_message);
            dataController.close();
            if (retValue != -1) {
                Toast.makeText(getApplicationContext(), "Message saved with SQLIte successfully", Toast.LENGTH_LONG).show();
                try {
                    counter += 1;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                    Editor editor = sharedPreferences.edit();
                    editor.putInt(SQL_COUNTER, counter);
                    editor.commit();
                    OutputStreamWriter out = new OutputStreamWriter(openFileOutput(PreferenceView.STORE_PREFERENCES, MODE_APPEND));
                    out.write("\nSQLite " + counter + ", " + s.format(new Date()));
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid info.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void cancelActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
