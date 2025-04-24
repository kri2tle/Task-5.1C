package com.example.newsandtubeapp.itube;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsandtubeapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ITubeActivity extends AppCompatActivity implements PlaylistAdapter.OnVideoClickListener {
    private YouTubePlayerView youTubePlayerView;
    private EditText videoUrlEditText;
    private Button addToPlaylistButton;
    private RecyclerView playlistRecyclerView;
    private DatabaseHelper databaseHelper;
    private PlaylistAdapter playlistAdapter;
    private YouTubePlayer youTubePlayer;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itube);

        try {
            // Get user email from intent
            userEmail = getIntent().getStringExtra("user_email");
            if (userEmail == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Enable back button in action bar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle("iTube - " + userEmail);
            }

            // Initialize views
            youTubePlayerView = findViewById(R.id.youtubePlayerView);
            videoUrlEditText = findViewById(R.id.videoUrlEditText);
            addToPlaylistButton = findViewById(R.id.addToPlaylistButton);
            playlistRecyclerView = findViewById(R.id.playlistRecyclerView);

            // Initialize database
            databaseHelper = new DatabaseHelper(this);

            // Initialize YouTube player
            getLifecycle().addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer player) {
                    youTubePlayer = player;
                }
            });

            // Set up playlist adapter
            playlistAdapter = new PlaylistAdapter(this, databaseHelper.getAllVideos(userEmail), this);
            playlistRecyclerView.setAdapter(playlistAdapter);

            // Set up add to playlist button
            addToPlaylistButton.setOnClickListener(v -> addVideoToPlaylist());
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing iTube: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void addVideoToPlaylist() {
        try {
            String videoUrl = videoUrlEditText.getText().toString().trim();
            String videoId = extractVideoId(videoUrl);

            if (videoId != null) {
                // In a real app, you would fetch video details from YouTube API
                // For this example, we'll use placeholder data
                VideoModel video = new VideoModel(
                    videoId,
                    "Video Title", // This would come from YouTube API
                    "https://img.youtube.com/vi/" + videoId + "/default.jpg"
                );
                databaseHelper.addVideo(video, userEmail);
                playlistAdapter = new PlaylistAdapter(this, databaseHelper.getAllVideos(userEmail), this);
                playlistRecyclerView.setAdapter(playlistAdapter);
                videoUrlEditText.setText("");
            } else {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error adding video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String extractVideoId(String videoUrl) {
        try {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(videoUrl);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error extracting video ID: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void onVideoClick(VideoModel video) {
        try {
            if (youTubePlayer != null) {
                youTubePlayer.loadVideo(video.getVideoId(), 0);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error playing video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(VideoModel video) {
        try {
            databaseHelper.deleteVideo(video.getVideoId(), userEmail);
            playlistAdapter = new PlaylistAdapter(this, databaseHelper.getAllVideos(userEmail), this);
            playlistRecyclerView.setAdapter(playlistAdapter);
        } catch (Exception e) {
            Toast.makeText(this, "Error deleting video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            youTubePlayerView.release();
        } catch (Exception e) {
            Toast.makeText(this, "Error releasing player: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} 