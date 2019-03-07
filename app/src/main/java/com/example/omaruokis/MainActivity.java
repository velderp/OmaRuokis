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
import android.widget.TextView;

import com.project.omaruokis.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check for saved user info. If InfoFilled is not set as true, UserInfoActivity is started.
        UserPrefs prefs = new UserPrefs(this);
        if (!prefs.prefGetInfoFilled()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        } else {
            EditText et = findViewById(R.id.editMainWeight);
            et.setText(Integer.toString(prefs.prefGetUserWeight()));
            Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
            spinner.setSelection(prefs.prefGetUserPal());
        }

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
        UserPrefs prefs = new UserPrefs(this);
        if (prefs.prefGetInfoFilled()) {
            updateUI();
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

    private void updateUI() {
        DateFormat df = new SimpleDateFormat(InputChecker.DATE_FORMAT);
        Date date = Calendar.getInstance().getTime();
        TextView tv = findViewById(R.id.textMainDate);
        tv.setText(df.format(date));
        bodyCalc();
    }

    private void bodyCalc() {
        UserPrefs prefs = new UserPrefs(this);
        InputChecker checker = new InputChecker();
        EditText et = findViewById(R.id.editMainWeight);
        if (checker.checkInt(et.getText().toString(), UserPrefs.MIN_WEIGHT, UserPrefs.MAX_WEIGHT)) {
            int weight = Integer.parseInt(et.getText().toString());
            int height = prefs.prefGetUserHeight();
            String sex = prefs.prefGetUserSex();
            double pal = 1 + prefs.prefGetUserPal() * 0.3;
            String dob = prefs.prefGetUserDob();
            TextView tv = findViewById(R.id.textMainDate);
            String date = tv.getText().toString();
            BodyCalc calc = new BodyCalc(weight, height, sex, pal, dob, date);
            tv = findViewById(R.id.textMainCalcBmi);
            tv.setText(String.format(Locale.forLanguageTag("fi-FI"), "%.2f", calc.calcBmi()));
            tv = findViewById(R.id.textMainCalcTee);
            tv.setText(String.format(Locale.forLanguageTag("fi-FI"), "%d", calc.calcTee()));
        }


    }
}
