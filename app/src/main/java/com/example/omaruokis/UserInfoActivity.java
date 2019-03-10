package com.example.omaruokis;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.omaruokis.utilities.UserPrefs;
import com.project.omaruokis.R;

import java.util.Locale;

public class UserInfoActivity extends AppCompatActivity {

    private EditText dobEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private RadioGroup sexRadioGroup;
    private Spinner activityLevelSpinner;
    private UserPrefs userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        dobEditText = findViewById(R.id.editDob);
        weightEditText = findViewById(R.id.editWeight);
        heightEditText = findViewById(R.id.editHeight);
        sexRadioGroup = findViewById(R.id.radioGroupSex);
        activityLevelSpinner = findViewById(R.id.spinnerActivityLevel);
        userPrefs = new UserPrefs(this);

        if (userPrefs.prefGetInfoFilled()) {
            getUserInfo();
        }
    }

    public void checkAndSave(View view) {
        String date = dobEditText.getText().toString();
        String weight = weightEditText.getText().toString();
        String height = heightEditText.getText().toString();

        if (userPrefs.prefSetUserDob(date)
                && sexRadioGroup.getCheckedRadioButtonId() != -1
                && userPrefs.prefSetUserWeight(weight)
                && userPrefs.prefSetUserHeight(height)) {
            String sex = (sexRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonMale) ? "M" : "F";
            userPrefs.prefSetUserSex(sex);
            userPrefs.prefSetUserPal(activityLevelSpinner.getSelectedItemPosition());
            userPrefs.prefSetInfoFilled(true);
            getUserInfo();
            Snackbar.make(view, getString(R.string.user_info_saved), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(view, getString(R.string.user_info_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void getUserInfo() {
        dobEditText.setText(userPrefs.prefGetUserDob());
        weightEditText.setText(String.format(Locale.forLanguageTag(MainActivity.LOCALE), "%d",
                userPrefs.prefGetUserWeight()));
        heightEditText.setText(String.format(Locale.forLanguageTag(MainActivity.LOCALE), "%d",
                userPrefs.prefGetUserHeight()));
        if (userPrefs.prefGetUserSex().equals("M")) {
            sexRadioGroup.check(R.id.radioButtonMale);
        } else {
            sexRadioGroup.check(R.id.radioButtonFemale);
        }
        activityLevelSpinner.setSelection(userPrefs.prefGetUserPal());
    }


}
