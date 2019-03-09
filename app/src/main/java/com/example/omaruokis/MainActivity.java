package com.example.omaruokis;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ToggleButton;

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
    private static final double PAL_INCREMENTS = 0.3;
    private static final double PAL_MINIMUM = 1.0;
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
            if (selectedDateIsToday()) {
                findViewById(R.id.mainCurrentDateButton).setVisibility(View.INVISIBLE);
                getDetailsFromPrefs();
            } else {
                findViewById(R.id.mainCurrentDateButton).setVisibility(View.VISIBLE);
                getDetailsFromDB();
            }
            setDate();
            bodyCalc();
        }
        toggleMealsView();
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
            double pal = spinner.getSelectedItemPosition() * PAL_INCREMENTS + PAL_MINIMUM;
            String dob = prefs.prefGetUserDob();
            TextView tv = findViewById(R.id.textMainDate);
            String date = tv.getText().toString();
            BodyCalc calc = new BodyCalc(weight, height, sex, pal, dob, date);
            tv = findViewById(R.id.textMainCalcBmi);
            tv.setText(String.format(Locale.forLanguageTag(LOCALE), "%.2f", calc.calcBmi()));
            tv = findViewById(R.id.textMainCalcTee);
            tv.setText(String.format(Locale.forLanguageTag(LOCALE), "%d", calc.calcTee()));
        } else {
            errorMessage(getString(R.string.main_weight_error));
        }
    }

    private void setDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                if (isAfterBirth(newDate)) {
                    DateHolder.getInstance().setDate(newDate.getTime());
                    updateUI();
                } else {
                    errorMessage(getString(R.string.main_date_error));
                }
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

        findViewById(R.id.mainToggleMeals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show/hide meals
                updateUI();
            }
        });

        findViewById(R.id.mainSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save meals to database
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
                ToggleButton toggleButton = findViewById(R.id.mainToggleMeals);
                toggleButton.setChecked(true);
                startActivity(new Intent(MainActivity.this, FoodSearchActivity.class));
            }
        });
    }

    private void setDate() {
        TextView tv = findViewById(R.id.textMainDate);
        String date = DateHolder.getInstance().dateToString();
        tv.setText(date);
    }

    private boolean selectedDateIsToday() {
        String date = DateHolder.getInstance().dateToString();
        return date.equals(DateHolder.getInstance().currentDateToString());
    }

    private void errorMessage(String message) {
        View view = findViewById(R.id.mainContents);
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private boolean isAfterBirth (Calendar calendar) {
        InputChecker checker = new InputChecker();
        UserPrefs prefs = new UserPrefs(this);
        return calendar.after(checker.dateStringToCalendar(prefs.prefGetUserDob()));
    }

    private void toggleMealsView() {
        ToggleButton toggleButton = findViewById(R.id.mainToggleMeals);
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerviewMeals);
        if (toggleButton.isChecked()) {
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void getDetailsFromPrefs() {
        UserPrefs prefs = new UserPrefs(this);
        EditText et = findViewById(R.id.editMainWeight);
        et.setText(Integer.toString(prefs.prefGetUserWeight()));
        Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
        spinner.setSelection(prefs.prefGetUserPal());
    }

    private void getDetailsFromDB() {
        EditText et = findViewById(R.id.editMainWeight);
        et.setText("100");
        Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
        spinner.setSelection(0);
    }
}
