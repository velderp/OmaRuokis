package com.project.omaruokis;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class UserInfoActivity extends AppCompatActivity {

    static final String PREF_USER = "UserInfo";
    static final String USER_DOB = "DateOfBirth";
    static final String USER_SEX = "Sex";
    static final String USER_WEIGHT = "Weight";
    static final String USER_HEIGHT = "Height";
    static final String USER_PAL = "ActivityLevel";
    static final String USER_INFO_FILLED = "InfoFilled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        SharedPreferences prefGet = getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE);
        if (prefGet.getBoolean(USER_INFO_FILLED, false)) {
            readUserInfo();
        }
    }

    public void checkAndSave(View view) {

        if (checkValidity()) {
            writeUserInfo();
            Snackbar.make(view, getString(R.string.user_info_saved), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, getString(R.string.user_info_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private boolean checkValidity() {
        InputChecker checker = new InputChecker();
        String date = ((EditText) findViewById(R.id.editDOB)).getText().toString();
        String weight = ((EditText) findViewById(R.id.editWeight)).getText().toString();
        String height = ((EditText) findViewById(R.id.editHeight)).getText().toString();
        RadioGroup rg = findViewById(R.id.radioGroupSex);
        return checker.checkDateValidity(date, 1900, 2100)
                && checker.dateBeforeCurrent(date)
                && rg.getCheckedRadioButtonId() != -1
                && checker.checkInt(weight,1, 635)
                && checker.checkInt(height, 24, 272);
    }

    private void writeUserInfo() {
        SharedPreferences prefPut = getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        String value = ((EditText) findViewById(R.id.editDOB)).getText().toString();
        prefEditor.putString(USER_DOB, value);
        RadioGroup rg = findViewById(R.id.radioGroupSex);
        value = (rg.getCheckedRadioButtonId() == R.id.radioButtonMale) ? "M" : "F";
        prefEditor.putString(USER_SEX, value);
        value = ((EditText) findViewById(R.id.editWeight)).getText().toString();
        prefEditor.putInt(USER_WEIGHT, Integer.parseInt(value));
        value = ((EditText) findViewById(R.id.editHeight)).getText().toString();
        prefEditor.putInt(USER_HEIGHT, Integer.parseInt(value));
        Spinner spinner = findViewById(R.id.spinnerActivityLevel);
        prefEditor.putInt(USER_PAL, spinner.getSelectedItemPosition());
        prefEditor.putBoolean(USER_INFO_FILLED, true);
        prefEditor.apply();
    }
    private void readUserInfo() {
        SharedPreferences prefGet = getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE);
        EditText et = findViewById(R.id.editDOB);
        et.setText(prefGet.getString(USER_DOB, ""));
        et = findViewById(R.id.editWeight);
        et.setText(Integer.toString(prefGet.getInt(USER_WEIGHT, 1)));
        et = findViewById(R.id.editHeight);
        et.setText(Integer.toString(prefGet.getInt(USER_HEIGHT, 1)));
        RadioGroup rg = findViewById(R.id.radioGroupSex);
        if (prefGet.getString(USER_SEX, "M").equals("M")) {
            rg.check(R.id.radioButtonMale);
        } else {
            rg.check(R.id.radioButtonFemale);
        }
        Spinner spinner = findViewById(R.id.spinnerActivityLevel);
        spinner.setSelection(prefGet.getInt(USER_PAL, 0));
    }
}
