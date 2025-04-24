package com.example.newsandtubeapp.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.newsandtubeapp.R;
import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.TopStoryViewHolder> {
    private List<NewsModel> topStories;
    private Context context;
    private NewsAdapter.OnNewsClickListener listener;

    public TopStoriesAdapter(Context context, List<NewsModel> topStories, NewsAdapter.OnNewsClickListener listener) {
        this.context = context;
        this.topStories = topStories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TopStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_story, parent, false);
        return new TopStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoryViewHolder holder, int position) {
        NewsModel story = topStories.get(position);
        holder.titleTextView.setText(story.getTitle());
        
        Glide.with(context)
            .load(story.getImageUrl())
            .into(holder.storyImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNewsClick(story);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    static class TopStoryViewHolder extends RecyclerView.ViewHolder {
        ImageView storyImageView;
        TextView titleTextView;

        TopStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storyImageView = itemView.findViewById(R.id.storyImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
} 