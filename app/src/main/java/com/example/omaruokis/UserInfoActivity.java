package com.example.omaruokis;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.project.omaruokis.R;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        UserPrefs userPrefs = new UserPrefs(this);
        if (userPrefs.prefGetInfoFilled()) {
            getUserInfo();
        }
    }

    public void checkAndSave(View view) {

        if (checkInputValidity()) {
            saveUserInfo();
            Snackbar.make(view, getString(R.string.user_info_saved), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, getString(R.string.user_info_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private boolean checkInputValidity() {
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

    private void saveUserInfo() {
        UserPrefs userPrefs = new UserPrefs(this);
        String value = ((EditText) findViewById(R.id.editDOB)).getText().toString();
        userPrefs.prefSetUserDOB(value);
        RadioGroup rg = findViewById(R.id.radioGroupSex);
        value = (rg.getCheckedRadioButtonId() == R.id.radioButtonMale) ? "M" : "F";
        userPrefs.prefSetUserSex(value);
        value = ((EditText) findViewById(R.id.editWeight)).getText().toString();
        userPrefs.prefSetUserWeight(Integer.parseInt(value));
        value = ((EditText) findViewById(R.id.editHeight)).getText().toString();
        userPrefs.prefSetUserHeight(Integer.parseInt(value));
        Spinner spinner = findViewById(R.id.spinnerActivityLevel);
        userPrefs.prefSetUserPAL(spinner.getSelectedItemPosition());
        userPrefs.prefSetInfoFilled(true);
    }

    private void getUserInfo() {
        UserPrefs userPrefs = new UserPrefs(this);
        EditText et = findViewById(R.id.editDOB);
        et.setText(userPrefs.prefGetUserDOB());
        et = findViewById(R.id.editWeight);
        et.setText(Integer.toString(userPrefs.prefGetUserWeight()));
        et = findViewById(R.id.editHeight);
        et.setText(Integer.toString(userPrefs.prefGetUserHeight()));
        RadioGroup rg = findViewById(R.id.radioGroupSex);
        if (userPrefs.prefGetUserSex().equals("M")) {
            rg.check(R.id.radioButtonMale);
        } else {
            rg.check(R.id.radioButtonFemale);
        }
        Spinner spinner = findViewById(R.id.spinnerActivityLevel);
        spinner.setSelection(userPrefs.prefGetUserPAL());
    }


}
