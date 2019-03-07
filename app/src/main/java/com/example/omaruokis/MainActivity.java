package com.example.omaruokis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.project.omaruokis.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check for saved user info. If InfoFilled is not set as true, UserInfoActivity is started.
        UserPrefs userPrefs = new UserPrefs(this);
        if (!userPrefs.prefGetInfoFilled()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        } else {
            EditText et = findViewById(R.id.editMainWeight);
            et.setText(Integer.toString(userPrefs.prefGetUserWeight()));
            Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
            spinner.setSelection(userPrefs.prefGetUserPAL());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_user_info) {
            startActivity(new Intent(this, UserInfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}