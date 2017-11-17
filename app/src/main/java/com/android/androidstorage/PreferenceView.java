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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PreferenceView extends AppCompatActivity {

    private EditText bookName;
    private EditText authorName;
    private EditText desc;
    private int counter = 0;
    private SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy-hh:mm a");
    public final static String STORE_PREFERENCES= "datastorage_assign_pref.txt"; //File to save preferences
    public final static String BOOK_PREFERCENS = "datastorage_assign_books.text"; //File to save book title
    private final static String COUNTER = "COUNTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref_view);
        bookName = (EditText) findViewById(R.id.book_name_text);
        authorName = (EditText) findViewById(R.id.book_name_text);
        desc = (EditText) findViewById(R.id.desc_text);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        counter = sharedPrefs.getInt(COUNTER, 0);
    }

    /**
     * Helper function to save preference
     * @param v
     */
    public void savePreference(View v) {
        String name = bookName.getText().toString();
        String author = authorName.getText().toString();
        String description = desc.getText().toString();
        if ((name != null) && (author != null) && (description != null)) {
            try {
                counter += 1;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                Editor editor = sharedPreferences.edit();
                editor.putInt(COUNTER, counter);
                boolean commitStatus = editor.commit();

                if(commitStatus == true) {
                    Toast.makeText(getApplicationContext(), "Preference saved successfully", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(getApplicationContext(), "Preference could not be saved. Try Again!", Toast.LENGTH_LONG).show();
                }
                //Share preference
                OutputStreamWriter out=new OutputStreamWriter(openFileOutput(STORE_PREFERENCES, MODE_APPEND));
                String message = "\nSaved Preference " + counter + ", " + s.format(new Date());
                out.write(message);
                out.close();
                //Book preferences
                OutputStreamWriter out1 =new OutputStreamWriter(openFileOutput(BOOK_PREFERCENS, MODE_APPEND));
                String book = "\n" + name + ", " + author + ". Desc: " + description;
                out1.write(book);
                out1.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else{
            Toast.makeText(getApplicationContext(), "Please enter a valid info.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Function to cancel activity
     * @param v
     */
    public void cancelActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
