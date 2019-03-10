package com.example.omaruokis;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.RoomDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import com.example.omaruokis.food_details.FoodEaten;
import com.example.omaruokis.food_details.FoodSearchActivity;
import com.example.omaruokis.food_details.FoodViewModel;
import com.example.omaruokis.food_details.UsersDay;
import com.project.omaruokis.R;
import com.example.omaruokis.utilities.BodyCalc;
import com.example.omaruokis.utilities.DateHolder;
import com.example.omaruokis.utilities.InputChecker;
import com.example.omaruokis.utilities.UserPrefs;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String LOCALE = "fi-FI";
    private static final double PAL_INCREMENTS = 0.3;
    private static final double PAL_MINIMUM = 1.0;
    private DatePickerDialog datePickerDialog;
    private FoodViewModel foodViewModel;
    private MealsListAdapter mealsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ToggleButton toggleButton = findViewById(R.id.mainToggleMeals);
        toggleButton.setChecked(true);
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

        //RecyclerView for daily meal quantity and deleting
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerviewMeals);
        mealsListAdapter = new MealsListAdapter(this);
        recyclerView.setAdapter(mealsListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //ViewModel can be used for separating non ui logic. Used here as intermediary for accessing database commands.
        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        updateAdapter();
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
        setDate();
        if (prefs.prefGetInfoFilled()) {
            if (selectedDateIsToday()) {
                findViewById(R.id.mainCurrentDateButton).setVisibility(View.INVISIBLE);
            } else {
                findViewById(R.id.mainCurrentDateButton).setVisibility(View.VISIBLE);
            }
            getDetailsFromDB();
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

                    updateAdapter();

                    bodyCalc();
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
                updateAdapter();
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
                String date = DateHolder.getInstance().dateToString();
                Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
                int activityLevel = spinner.getSelectedItemPosition();
                EditText et = findViewById(R.id.editMainWeight);
                int weight = Integer.parseInt(et.getText().toString());
                UsersDay usersDay = new UsersDay(date, activityLevel, weight);
                foodViewModel.insertUsersDay(usersDay);
                if (selectedDateIsToday()) {
                    saveWeightToPrefs();
                }
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
                bodyCalc();
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

    private void saveWeightToPrefs() {
        UserPrefs pref = new UserPrefs(this);
        EditText et = findViewById(R.id.editMainWeight);
        String weight = et.getText().toString();
        pref.prefSetUserWeight(weight);
    }

    private void getDetailsFromDB() {
        setDate();
        String date = DateHolder.getInstance().dateToString();
        LiveData<UsersDay> dayLiveData = foodViewModel.findUsersDayByDate(date);
        dayLiveData.observe(this, new Observer<UsersDay>() {
            @Override
            public void onChanged(@Nullable UsersDay usersDay) {
                if (usersDay == null) {
                    createUsersDay();
                } else {
                    EditText et = findViewById(R.id.editMainWeight);
                    et.setText(Integer.toString(usersDay.getWeight()));
                    Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
                    spinner.setSelection(usersDay.getActivityLevel());
                }
            }
        });
        updateNutrients();
    }

    private void updateAdapter(){
        String date = DateHolder.getInstance().dateToString();
        LiveData<List<FoodEaten>> listLiveData = foodViewModel.findFoodEatenByDate(date);
        listLiveData.observe(this, new Observer<List<FoodEaten>>() {
            @Override
            public void onChanged(@Nullable List<FoodEaten> foodEatens) {
                mealsListAdapter.setMeals(foodEatens);
            }
        });
    }

    private void createUsersDay() {
        UserPrefs pref = new UserPrefs(this);
        String date = DateHolder.getInstance().dateToString();
        int activityLevel = pref.prefGetUserPal();
        int weight = pref.prefGetUserWeight();
        UsersDay usersDay = new UsersDay(date, activityLevel, weight);
        foodViewModel.insertUsersDay(usersDay);
        EditText et = findViewById(R.id.editMainWeight);
        et.setText(Integer.toString(usersDay.getWeight()));
        Spinner spinner = findViewById(R.id.spinnerMainActivityLevel);
        spinner.setSelection(usersDay.getActivityLevel());
    }

    private void updateNutrients() {
        String date = DateHolder.getInstance().dateToString();
        LiveData<List<FoodEaten>> listLiveData = foodViewModel.findFoodEatenByDate(date);
        listLiveData.observe(this, new Observer<List<FoodEaten>>() {
            @Override
            public void onChanged(@Nullable List<FoodEaten> foodEatens) {
                HashMap<String, String> nutrients = new HashMap<>();
                nutrients = calcNutrients(foodEatens);
                TextView tv = findViewById(R.id.textMainSumCalories);
                tv.setText(nutrients.get("Calories"));
                tv = findViewById(R.id.textMainSumCarbs);
                tv.setText(nutrients.get("Carbs"));
                tv = findViewById(R.id.textMainSumLipids);
                tv.setText(nutrients.get("Lipids"));
                tv = findViewById(R.id.textMainSumProteins);
                tv.setText(nutrients.get("Proteins"));
            }
        });
    }

    private HashMap<String, String> calcNutrients(List<FoodEaten> foodEatens) {
        int calories = 0;
        int carbs = 0;
        int lipids = 0;
        int proteins = 0;
        for (int i = 0; i < foodEatens.size(); i++){
            calories += foodEatens.get(i).calculateTotalEnergy();
            carbs += foodEatens.get(i).calculateTotalCarbohydrates();
            lipids += foodEatens.get(i).calculateTotalFat();
            proteins += foodEatens.get(i).calculateTotalProteins();
        }
        HashMap<String, String> nutrients = new HashMap<>();
        nutrients.put("Calories", Integer.toString(calories));
        nutrients.put("Carbs", Integer.toString(carbs));
        nutrients.put("Lipids", Integer.toString(lipids));
        nutrients.put("Proteins", Integer.toString(proteins));

        return nutrients;
    }
}
