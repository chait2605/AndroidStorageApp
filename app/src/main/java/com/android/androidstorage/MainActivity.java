package com.android.androidstorage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            InputStream in = openFileInput(PreferenceView.STORE_PREFERENCES);
            if(in != null) {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while((str=reader.readLine())!=null) {
                    buf.append(str +"\n");
                }
                in.close();
                info.setText(buf.toString());
                info.setVisibility(View.VISIBLE);
            } else {
                info.setVisibility(View.INVISIBLE);
            }
        } catch(Exception e) {
            e.printStackTrace();
            info.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Function to go to preference page
     * @param v
     */
    public void gotoPreference(View v) {
        Intent intent = new Intent(this, PreferenceView.class);
        startActivity(intent);
    }

    /**
     * Function to to to SQLite saving page
     * @param v
     */
    public void gotoSQLite(View v) {
        Intent intent = new Intent(this, SQLiteView.class);
        startActivity(intent);
    }

    /**
     * Function to close app
     * @param v
     */
    public void closeApp(View v) {
        MainActivity.this.finish();
        System.exit(0);
    }
}
