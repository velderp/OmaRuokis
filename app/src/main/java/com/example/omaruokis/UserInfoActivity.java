package com.example.omaruokis;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.omaruokis.utilities.InputChecker;
import com.example.omaruokis.utilities.UserPrefs;
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
        UserPrefs userPrefs = new UserPrefs(this);
        InputChecker checker = new InputChecker();
        String date = ((EditText) findViewById(R.id.editDob)).getText().toString();
        String weight = ((EditText) findViewById(R.id.editWeight)).getText().toString();
        String height = ((EditText) findViewById(R.id.editHeight)).getText().toString();
        RadioGroup rg = findViewById(R.id.radioGroupSex);

        if (userPrefs.prefSetUserDob(date)
                && rg.getCheckedRadioButtonId() != -1
                && userPrefs.prefSetUserWeight(weight)
                && userPrefs.prefSetUserHeight(height)) {
            String sex = (rg.getCheckedRadioButtonId() == R.id.radioButtonMale) ? "M" : "F";
            userPrefs.prefSetUserSex(sex);
            Spinner spinner = findViewById(R.id.spinnerActivityLevel);
            userPrefs.prefSetUserPal(spinner.getSelectedItemPosition());
            userPrefs.prefSetInfoFilled(true);
            Snackbar.make(view, getString(R.string.user_info_saved), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, getString(R.string.user_info_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void getUserInfo() {
        UserPrefs userPrefs = new UserPrefs(this);
        EditText et = findViewById(R.id.editDob);
        et.setText(userPrefs.prefGetUserDob());
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
        spinner.setSelection(userPrefs.prefGetUserPal());
    }


}
