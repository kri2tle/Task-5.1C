package com.example.newsandtubeapp.itube;

import java.io.Serializable;

public class VideoModel implements Serializable {
    private String videoId;
    private String title;
    private String thumbnailUrl;

    public VideoModel(String videoId, String title, String thumbnailUrl) {
        this.videoId = videoId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
} 