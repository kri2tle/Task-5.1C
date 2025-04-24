package com.example.newsandtubeapp.itube;

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

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<VideoModel> videoList;
    private Context context;
    private OnVideoClickListener listener;

    public interface OnVideoClickListener {
        void onVideoClick(VideoModel video);
        void onDeleteClick(VideoModel video);
    }

    public PlaylistAdapter(Context context, List<VideoModel> videoList, OnVideoClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        VideoModel video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        
        Glide.with(context)
            .load(video.getThumbnailUrl())
            .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onVideoClick(video);
            }
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        ImageView deleteButton;

        PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
} 