package com.example.newsandtubeapp.news;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsandtubeapp.R;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.OnNewsClickListener {
    private RecyclerView topStoriesRecyclerView;
    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private TopStoriesAdapter topStoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            // Enable back button in action bar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("News");
            }

            topStoriesRecyclerView = findViewById(R.id.topStoriesRecyclerView);
            newsRecyclerView = findViewById(R.id.newsRecyclerView);

            // Initialize adapters
            newsAdapter = new NewsAdapter(this, getSampleNews(), this);
            topStoriesAdapter = new TopStoriesAdapter(this, getSampleTopStories(), this);

            // Set adapters to RecyclerViews
            topStoriesRecyclerView.setAdapter(topStoriesAdapter);
            newsRecyclerView.setAdapter(newsAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing News: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<NewsModel> getSampleNews() {
        List<NewsModel> newsList = new ArrayList<>();
        try {
            newsList.add(new NewsModel(
                "Breaking News 1",
                "This is a sample news description for the first news item.",
                "https://picsum.photos/300/200",
                false
            ));
            newsList.add(new NewsModel(
                "Breaking News 2",
                "This is a sample news description for the second news item.",
                "https://picsum.photos/300/201",
                false
            ));
        } catch (Exception e) {
            Toast.makeText(this, "Error loading news: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return newsList;
    }

    private List<NewsModel> getSampleTopStories() {
        List<NewsModel> topStories = new ArrayList<>();
        try {
            topStories.add(new NewsModel(
                "Top Story 1",
                "This is a sample top story description.",
                "https://picsum.photos/300/202",
                true
            ));
            topStories.add(new NewsModel(
                "Top Story 2",
                "This is another sample top story description.",
                "https://picsum.photos/300/203",
                true
            ));
        } catch (Exception e) {
            Toast.makeText(this, "Error loading top stories: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return topStories;
    }

    @Override
    public void onNewsClick(NewsModel news) {
        try {
            // Handle news item click
            NewsDetailFragment fragment = NewsDetailFragment.newInstance(news);
            getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .addToBackStack(null)
                .commit();
        } catch (Exception e) {
            Toast.makeText(this, "Error showing news detail: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 