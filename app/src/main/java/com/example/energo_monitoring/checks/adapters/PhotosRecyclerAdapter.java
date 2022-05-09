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

import com.example.energo_monitoring.checks.activities.CheckMainActivity;
import com.example.energo_monitoring.checks.presenters.utilities.LoadImageManager;
import com.example.energo_monitoring.R;

import java.util.ArrayList;

public class PhotosRecyclerAdapter extends RecyclerView.Adapter<PhotosRecyclerAdapter.PhotoViewHolder> {

    public ArrayList<Bitmap> photos;
    ActivityResultLauncher<Intent> takePhotoLauncher;
    Context context;
    public int listItem;

    public PhotosRecyclerAdapter(Context context, int listItem) {
        this.context = context;
        this.listItem = listItem;

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
                .inflate(listItem, parent, false);

        PhotoViewHolder vh = new PhotoViewHolder(view);

        CardView cardView = view.findViewById(R.id.photoCard);
        cardView.setOnClickListener(v -> {
            if(vh.getLayoutPosition() == photos.size() - 1) {
                ((CheckMainActivity) context).setLastCreatedPath(LoadImageManager.getPhotoUri(context));
                LoadImageManager.takePhoto(
                        context,
                        takePhotoLauncher,
                        ((CheckMainActivity) context).getLastCreatedPath());
            }
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
