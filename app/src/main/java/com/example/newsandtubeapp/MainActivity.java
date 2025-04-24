package com.example.newsandtubeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.newsandtubeapp.news.NewsActivity;
import com.example.newsandtubeapp.itube.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnNews, btnITube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            btnNews = findViewById(R.id.btnNews);
            btnITube = findViewById(R.id.btnITube);

            btnNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(MainActivity.this, NewsActivity.class));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error opening News: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnITube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Error opening iTube: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing app: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}