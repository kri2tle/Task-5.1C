package com.example.newsandtubeapp.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.newsandtubeapp.R;
import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {
    private static final String ARG_NEWS = "news";
    private NewsModel news;

    public static NewsDetailFragment newInstance(NewsModel news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            news = (NewsModel) getArguments().getSerializable(ARG_NEWS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        ImageView newsImageView = view.findViewById(R.id.newsDetailImageView);
        TextView titleTextView = view.findViewById(R.id.newsDetailTitleTextView);
        TextView descriptionTextView = view.findViewById(R.id.newsDetailDescriptionTextView);
        RecyclerView relatedNewsRecyclerView = view.findViewById(R.id.relatedNewsRecyclerView);

        // Set news details
        titleTextView.setText(news.getTitle());
        descriptionTextView.setText(news.getDescription());
        Glide.with(this)
            .load(news.getImageUrl())
            .into(newsImageView);

        // Set up related news
        NewsAdapter relatedNewsAdapter = new NewsAdapter(getContext(), getRelatedNews(), null);
        relatedNewsRecyclerView.setAdapter(relatedNewsAdapter);

        return view;
    }

    private List<NewsModel> getRelatedNews() {
        // This is a sample implementation. In a real app, you would fetch related news from an API
        List<NewsModel> relatedNews = new ArrayList<>();
        relatedNews.add(new NewsModel(
            "Related News 1",
            "This is a related news item.",
            "https://picsum.photos/300/204",
            false
        ));
        relatedNews.add(new NewsModel(
            "Related News 2",
            "This is another related news item.",
            "https://picsum.photos/300/205",
            false
        ));
        return relatedNews;
    }
} 