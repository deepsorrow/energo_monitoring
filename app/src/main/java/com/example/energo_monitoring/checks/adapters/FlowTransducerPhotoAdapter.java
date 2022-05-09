package com.example.energo_monitoring.checks.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.checks.presenters.CheckLengthOfStraightLinesPresenter;
import com.example.energo_monitoring.R;

public class FlowTransducerPhotoAdapter extends RecyclerView.Adapter<FlowTransducerPhotoAdapter.PhotoViewHolder> {

    CheckLengthOfStraightLinesPresenter presenter;
    ActivityResultLauncher<Intent> takePhotoLauncher;
    Context context;

    public FlowTransducerPhotoAdapter(Context context, CheckLengthOfStraightLinesPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder{
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void loadImageFromBitmap(Bitmap bitmap){
            ImageView photoImageView = itemView.findViewById(R.id.photoImageView);
            photoImageView.setImageBitmap(bitmap);
        }
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_flow_transducers_photo_card, parent, false);

        PhotoViewHolder vh = new PhotoViewHolder(view);

        CardView cardView = view.findViewById(R.id.photoCard);
        cardView.setOnClickListener(v -> presenter.onPhotoCardClicked(vh));

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.loadImageFromBitmap(presenter.photos.get(position));
    }

    @Override
    public int getItemCount() {
        return presenter.photos.size();
    }

}
