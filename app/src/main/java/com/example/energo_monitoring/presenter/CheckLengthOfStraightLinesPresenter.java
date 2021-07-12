package com.example.energo_monitoring.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.energo_monitoring.model.DeviceFlowTransducer;
import com.example.energo_monitoring.model.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.presenter.utilities.LoadImageManager;
import com.example.energo_monitoring.view.activity.CheckLengthOfStraightLinesAreasActivity;
import com.example.energo_monitoring.view.adapters.FlowTransducerPhotoAdapter;
import com.example.energo_monitoring.view.viewmodel.CheckLengthViewModel;

import java.util.ArrayList;

public class CheckLengthOfStraightLinesPresenter {

    CheckLengthOfStraightLinesAreasActivity activity;
    private ArrayList<FlowTransducerCheckLengthResult> results;
    private FlowTransducerPhotoAdapter photosAdapter;
    public ArrayList<Bitmap> photos;
    public int currentId;
    ActivityResultLauncher<Intent> takePhotoLauncher;
    public ArrayList<DeviceFlowTransducer> devices;
    CheckLengthViewModel model;

    public CheckLengthOfStraightLinesPresenter(CheckLengthOfStraightLinesAreasActivity activity,
                                               ClientDataBundle dataBundle) {
        this.activity = activity;
        devices = new ArrayList<>(dataBundle.getDeviceFlowTransducers());

        photosAdapter = new FlowTransducerPhotoAdapter(activity.getApplicationContext(), this);

        initPhotos();

        results = new ArrayList<>();
        for(DeviceFlowTransducer device : devices){
            results.add(new FlowTransducerCheckLengthResult(device));
        }

        takePhotoLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                        addPhoto(thumbnail);
                        photosAdapter.notifyDataSetChanged();
                    }
                });
    }

    public String saveAndGetLengthBefore(int id, String currentLength){
        if(!currentLength.isEmpty())
            results.get(currentId).setLengthBefore(currentLength);
        return String.valueOf(results.get(id).getLengthBefore());
    }

    public String saveAndGetLengthAfter(int id, String currentLength){
        if(!currentLength.isEmpty())
            results.get(currentId).setLengthAfter(currentLength);
        return String.valueOf(results.get(id).getLengthAfter());
    }

    public void saveAndSetPhotos(int id){
        results.get(currentId).setPhotos(photos);
        ArrayList<Bitmap> newPhotos = results.get(id).getPhotos();
        if(newPhotos == null || newPhotos.size() == 1)
            initPhotos();
        else
            photos = newPhotos;

        photosAdapter.notifyDataSetChanged();
    }

    public void addPhoto(Bitmap bitmapPhoto){
        // insert new photo to end, move photoButton to begin
        photos.remove(photos.size() - 1);
        photos.add(bitmapPhoto);
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    }

    public void onPhotoCardClicked(FlowTransducerPhotoAdapter.PhotoViewHolder vh){
        if(vh.getLayoutPosition() == photos.size() - 1)
            LoadImageManager.takePhoto(takePhotoLauncher);
    }

    public FlowTransducerPhotoAdapter getPhotosAdapter() {
        return photosAdapter;
    }

    public ActivityResultLauncher<Intent> getTakePhotoLauncher() {
        return takePhotoLauncher;
    }

    public void setViewModel(CheckLengthViewModel model){
        this.model = model;
    }

    public void onDeviceCardClicked(int id){
        model.getCurrentDeviceId().setValue(id);
    }

    public CheckLengthViewModel getModel() {
        return model;
    }

    public void initPhotos(){
        // create empty element to click it to take first image
        photos = new ArrayList<>();
        photos.add(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888));
    }

    public FlowTransducerCheckLengthResult getResult(int id){
        return results.get(id);
    }


}
