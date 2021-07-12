package com.example.energo_monitoring.view.adapters;

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

import com.example.energo_monitoring.presenter.utilities.LoadImageManager;
import com.example.energo_monitoring.R;

import java.util.ArrayList;

public class FinalPhotosRecyclerAdapter extends RecyclerView.Adapter<FinalPhotosRecyclerAdapter.PhotoViewHolder> {

    private ArrayList<Bitmap> photos;
    ActivityResultLauncher<Intent> takePhotoLauncher;
    Context context;

    public FinalPhotosRecyclerAdapter(Context context) {
        this.context = context;

        // create empty element to click it to take first image
        photos = new ArrayList<>();
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    }

    public void addPhoto(Bitmap bitmapPhoto){
        // insert new photo to end, move photoButton to begin
        photos.remove(photos.size() - 1);
        photos.add(bitmapPhoto);
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    }

    public void setPhotoLauncher(ActivityResultLauncher<Intent> launcher){
        this.takePhotoLauncher = launcher;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

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
                .inflate(R.layout.list_item_final_photos_photo_card, parent, false);

        PhotoViewHolder vh = new PhotoViewHolder(view);

        CardView cardView = view.findViewById(R.id.photoCard);
        cardView.setOnClickListener(v -> {
            if(vh.getLayoutPosition() == photos.size() - 1)
                LoadImageManager.takePhoto(takePhotoLauncher);
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        //if(position != photos.size()){
            holder.loadImageFromBitmap(photos.get(position));
        //}
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}
