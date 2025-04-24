package com.example.newsandtubeapp.itube;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.newsandtubeapp.R;

public class SignupActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signupButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        try {
            // Initialize views
            nameEditText = findViewById(R.id.nameEditText);
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            signupButton = findViewById(R.id.signupButton);

            // Initialize database
            databaseHelper = new DatabaseHelper(this);

            // Set up signup button
            signupButton.setOnClickListener(v -> handleSignup());
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing signup: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignup() {
        try {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // In a real app, you would validate email format and password strength
            // For this example, we'll use simple validation
            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if user already exists
            if (databaseHelper.userExists(email)) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Register new user
            if (databaseHelper.registerUser(name, email, password)) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                // Navigate to login
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error during signup: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 