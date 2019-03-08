package com.example.omaruokis;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.omaruokis.food_details.FoodSearchActivity;
import com.project.omaruokis.R;
import com.example.omaruokis.utilities.BodyCalc;
import com.example.omaruokis.utilities.DateHolder;
import com.example.omaruokis.utilities.InputChecker;
import com.example.omaruokis.utilities.UserPrefs;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String LOCALE = "fi-FI";
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setDate();
        setListeners();
        setDatePicker();
        // Check for saved user info. If InfoFilled is not set as true, UserInfoActivity is started.
        UserPrefs prefs = new UserPrefs(this);
        if (!prefs.prefGetInfoFilled()) {
            Intent intent = new Intent(this, UserInfoActivity.class);
            startActivity(intent);
        } else {
            EditText et = findViewById(R.id.editMainWeight);
            et.setText(String.format(Locale.forLanguageTag(LOCALE), "%d",
                    prefs.prefGetUserWeight()));
            Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
            spinner.setSelection(prefs.prefGetUserPal());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
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
        if (id == R.id.action_user_info) {
            startActivity(new Intent(this, UserInfoActivity.class));
            return true;
        }
        if (id == R.id.action_info) {
            startActivity(new Intent(this, InfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        UserPrefs prefs = new UserPrefs(this);
        if (prefs.prefGetInfoFilled()) {
            setDate();
            bodyCalc();
        }

    }

    private void bodyCalc() {
        UserPrefs prefs = new UserPrefs(this);
        InputChecker checker = new InputChecker();
        EditText et = findViewById(R.id.editMainWeight);
        if (checker.checkInt(et.getText().toString(), UserPrefs.MIN_WEIGHT, UserPrefs.MAX_WEIGHT)) {
            int weight = Integer.parseInt(et.getText().toString());
            int height = prefs.prefGetUserHeight();
            String sex = prefs.prefGetUserSex();
            Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
            double pal = 1 + spinner.getSelectedItemPosition() * 0.3;
            String dob = prefs.prefGetUserDob();
            TextView tv = findViewById(R.id.textMainDate);
            String date = tv.getText().toString();
            BodyCalc calc = new BodyCalc(weight, height, sex, pal, dob, date);
            tv = findViewById(R.id.textMainCalcBmi);
            tv.setText(String.format(Locale.forLanguageTag(LOCALE), "%.2f", calc.calcBmi()));
            tv = findViewById(R.id.textMainCalcTee);
            tv.setText(String.format(Locale.forLanguageTag(LOCALE), "%d", calc.calcTee()));
        }
    }

    private void setDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateHolder.getInstance().setDate(newDate.getTime());
                updateUI();
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setListeners() {
        findViewById(R.id.mainCalendarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        findViewById(R.id.mainCurrentDateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateHolder.getInstance().resetDate();
                updateUI();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.activity_level_array_fi));
        Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                updateUI();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FoodSearchActivity.class));
            }
        });
    }

    private void setDate() {
        TextView tv = findViewById(R.id.textMainDate);
        tv.setText(DateHolder.getInstance().dateToString());
    }
}
