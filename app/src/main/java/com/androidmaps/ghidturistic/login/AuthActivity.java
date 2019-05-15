package com.androidmaps.ghidturistic.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.androidmaps.ghidturistic.MapsActivity;
import com.androidmaps.ghidturistic.R;
import com.google.android.gms.common.util.Strings;

public class AuthActivity extends Activity {

    private static final String LOGIN_STATUS = "login_status";

    private EditText usernameEdittext, passwordEdittext, confirmPasswordEdittext;
    private Button ctaButton;
    private TextView loginPageButton, signUpPageButton;

    private SharedPreferences sharedPreferences;

    private boolean shouldSignUpUser = false;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false);
        if (isUserLoggedIn) {
            startMapActivity();
        }
        initViews();
        initCallbakcs();
        changeToLogin();
    }

    private void initViews() {
        usernameEdittext = findViewById(R.id.username_edittext);
        passwordEdittext = findViewById(R.id.password_edittext);
        confirmPasswordEdittext = findViewById(R.id.confirm_password_edittext);
        ctaButton = findViewById(R.id.auth_button);
        loginPageButton = findViewById(R.id.login_page_button);
        signUpPageButton = findViewById(R.id.sign_up_page_button);
    }

    private void initCallbakcs() {
        ctaButton.setOnClickListener(view -> loginUser());
        loginPageButton.setOnClickListener(view -> changeToLogin());
        signUpPageButton.setOnClickListener(view -> changeToSignUp());
    }

    private void changeToLogin() {
        shouldSignUpUser = false;
        ctaButton.setText(R.string.login);
        confirmPasswordEdittext.setVisibility(View.GONE);
        signUpPageButton.setBackground(null);
        loginPageButton.setBackgroundColor(getResources().getColor(R.color.light_grey));
    }

    private void changeToSignUp() {
        shouldSignUpUser = true;
        ctaButton.setText(R.string.sign_up);
        confirmPasswordEdittext.setVisibility(View.VISIBLE);
        loginPageButton.setBackground(null);
        signUpPageButton.setBackgroundColor(getResources().getColor(R.color.light_grey));
    }

    private void loginUser() {
        String username = usernameEdittext.getText().toString();
        String password = passwordEdittext.getText().toString();
        if (Strings.isEmptyOrWhitespace(username) || Strings.isEmptyOrWhitespace(password)) {
            if (Strings.isEmptyOrWhitespace(username)) {
                usernameEdittext.setError("Username empty");
            }
            if (Strings.isEmptyOrWhitespace(password)) {
                passwordEdittext.setError("Password Empty");
            }
            return;
        }
        if (shouldSignUpUser) {
            String confirmPassword = confirmPasswordEdittext.getText().toString();
            if (password.equals(confirmPassword)) {
                signUpUser(username, password);
            }
        }
        sharedPreferences.edit().putBoolean(LOGIN_STATUS, true).apply();
        startMapActivity();
    }

    private void signUpUser(String username, String password) {
        //TODO register new user
    }

    private void startMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
