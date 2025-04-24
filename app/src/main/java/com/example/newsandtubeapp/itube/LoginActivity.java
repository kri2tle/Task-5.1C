package com.example.newsandtubeapp.itube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newsandtubeapp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button signupButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            // Initialize views
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            loginButton = findViewById(R.id.loginButton);
            signupButton = findViewById(R.id.signupButton);

            // Initialize database
            databaseHelper = new DatabaseHelper(this);

            // Set up login button
            loginButton.setOnClickListener(v -> handleLogin());

            // Set up signup button
            signupButton.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleLogin() {
        try {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // In a real app, you would validate against a backend server
            // For this example, we'll use a simple check
            if (databaseHelper.validateUser(email, password)) {
                // Login successful
                Intent intent = new Intent(LoginActivity.this, ITubeActivity.class);
                intent.putExtra("user_email", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error during login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 