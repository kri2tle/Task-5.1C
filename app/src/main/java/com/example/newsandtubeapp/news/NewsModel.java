package com.example.newsandtubeapp.news;

import java.io.Serializable;

public class NewsModel implements Serializable {
    private String title;
    private String description;
    private String imageUrl;
    private boolean isTopStory;

    public NewsModel(String title, String description, String imageUrl, boolean isTopStory) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isTopStory = isTopStory;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isTopStory() {
        return isTopStory;
    }
} 