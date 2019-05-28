package com.androidmaps.ghidturistic.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.androidmaps.ghidturistic.R;
import com.androidmaps.ghidturistic.main.MapsActivity;
import com.google.android.gms.common.util.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends Activity {

    private EditText emailEdittext, passwordEdittext, confirmPasswordEdittext;
    private Button ctaButton;
    private TextView loginPageButton, signUpPageButton;

    private FirebaseAuth firebaseAuth;

    private boolean shouldSignUpUser = false;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            startMapActivity();
        }
        initViews();
        initCallbakcs();
        changeToLogin();
    }

    private void initViews() {
        emailEdittext = findViewById(R.id.email_edittext);
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
        String email = emailEdittext.getText().toString();
        String password = passwordEdittext.getText().toString();
        if (Strings.isEmptyOrWhitespace(email) || Strings.isEmptyOrWhitespace(password)) {
            if (Strings.isEmptyOrWhitespace(email)) {
                emailEdittext.setError("Username empty");
            }
            if (Strings.isEmptyOrWhitespace(password)) {
                passwordEdittext.setError("Password Empty");
            }
            return;
        }
        if (shouldSignUpUser) {
            String confirmPassword = confirmPasswordEdittext.getText().toString();
            if (password.equals(confirmPassword)) {
                signUpUser(email, password);
                return;
            }
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    startMapActivity();
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(AuthActivity.this, "No user found with these credentials. Maybe you want to sign up instead?",
                                   Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void signUpUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                FirebaseUser user = firebaseAuth.getCurrentUser();
                startMapActivity();
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(AuthActivity.this, "Authentication failed.",
                               Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMapActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
