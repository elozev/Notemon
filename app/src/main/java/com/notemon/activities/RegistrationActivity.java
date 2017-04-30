package com.notemon.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.notemon.R;
import com.notemon.models.User;
import com.notemon.rest.RestMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = RegistrationActivity.class.getSimpleName();
    @BindView(R.id.regUsernameEditText)
    EditText usernameEditText;
    @BindView(R.id.regPasswordEditText)
    EditText passEditText;
    @BindView(R.id.regEmailEditText)
    EditText emailEditText;
    @BindView(R.id.firstNameEditText)
    EditText firstNameEditText;
    @BindView(R.id.lastNameEditText)
    EditText lastNameEditText;
    @BindView(R.id.signUpButton)
    Button signUpButton;

    private boolean isUsernameEmpty = true;
    private boolean isPassEmpty = true;
    private boolean isEmailEmpty = true;
    private boolean isFirstEmpty = true;
    private boolean isLastEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        signUpButton.setEnabled(false);
    }

    @OnTextChanged(value = R.id.regUsernameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextUsernameChanged(Editable editable) {
        if (editable.length() > 4) {
            isUsernameEmpty = false;
        } else {
            isUsernameEmpty = true;
            usernameEditText.setError("required");
        }
        enableSignUpButton();
    }

    @OnTextChanged(value = R.id.regPasswordEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextPasswordChanged(Editable editable) {
        if (editable.length() > 4) {
            isPassEmpty = false;
        } else {
            isPassEmpty = true;
            passEditText.setError("5 characters at least");
        }
        enableSignUpButton();
    }

    @OnTextChanged(value = R.id.regEmailEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextEmailChanged(Editable editable) {

        if (editable.length() > 4 && validateEmail(editable.toString())) {
            isEmailEmpty = false;
        } else {
            isEmailEmpty = true;
            emailEditText.setError("not valid");
        }
        enableSignUpButton();
    }

    @OnTextChanged(value = R.id.firstNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextFirstNameChanged(Editable editable) {
        if (editable.length() > 1) {
            isFirstEmpty = false;
        } else {
            isFirstEmpty = true;
            firstNameEditText.setError("not valid");
        }
        enableSignUpButton();
    }

    @OnTextChanged(value = R.id.lastNameEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextLastNameChanged(Editable editable) {
        if (editable.length() > 1) {
            isLastEmpty = false;
        } else {
            isLastEmpty = true;
            lastNameEditText.setError("not valid");
        }
        enableSignUpButton();
    }

    private void enableSignUpButton() {
        if (!isUsernameEmpty && !isPassEmpty && !isEmailEmpty && !isFirstEmpty && !isLastEmpty) {
            signUpButton.setEnabled(true);
        }
    }

    private boolean validateEmail(String s) {
        return s.contains("@") && s.contains(".") && s.length() > 4;
    }

    @OnClick(R.id.signUpButton)
    public void onSignUpClick() {
        Log.d(TAG, usernameEditText.getText().toString());
        Log.d(TAG, passEditText.getText().toString());
        Log.d(TAG, emailEditText.getText().toString());
        Log.d(TAG, firstNameEditText.getText().toString());
        Log.d(TAG, lastNameEditText.getText().toString());

        User user = new User();
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passEditText.getText().toString());
        user.setEmail(emailEditText.getText().toString());
        user.setFirstName(firstNameEditText.getText().toString());
        user.setLastName(lastNameEditText.getText().toString());

        makeRegistrationCall(user);
    }

    private void makeRegistrationCall(User user) {
        RestMethods.createUser(user, RegistrationActivity.this);
    }

}
