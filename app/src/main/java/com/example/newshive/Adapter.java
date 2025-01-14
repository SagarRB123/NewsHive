package com.example.newshive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Model> modelArrayList;

    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // Handle item click to open webview with the URL
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, webView.class);
            intent.putExtra("url", modelArrayList.get(position).getUrl());
            context.startActivity(intent);
        });

        // Set the content of the views
        holder.time.setText("Published At: " + modelArrayList.get(position).getPublishedAt());
        holder.author.setText(modelArrayList.get(position).getAuthor());
        holder.heading.setText(modelArrayList.get(position).getTitle());
        holder.content.setText(modelArrayList.get(position).getDescription());

        // Load image using Glide and handle null URLs
        String imageUrl = modelArrayList.get(position).getUrlToImage();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Load image if URL is not null
            Glide.with(context)
                    .load(imageUrl)
                    .into(holder.imageView);
        } else {
            // If the URL is null or empty, set a default image
            Glide.with(context)
                    .load(R.drawable.default_image) // Replace with your default image resource
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView heading, content, author, time;
        CardView cardView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.main_heading);
            content = itemView.findViewById(R.id.content);
            author = itemView.findViewById(R.id.author);
            time = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageview); // Ensure you have an ImageView in the layout
        }
    }
}
