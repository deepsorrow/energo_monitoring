package com.example.energo_monitoring.presentation.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.energo_monitoring.data.devices.DeviceFlowTransducer;
import com.example.energo_monitoring.data.FlowTransducerCheckLengthResult;
import com.example.energo_monitoring.data.api.ClientDataBundle;
import com.example.energo_monitoring.data.db.ResultDataDatabase;
import com.example.energo_monitoring.presentation.presenters.utilities.LoadImageManager;
import com.example.energo_monitoring.presentation.activities.CheckLengthOfStraightLinesAreasActivity;
import com.example.energo_monitoring.presentation.adapters.FlowTransducerPhotoAdapter;
import com.example.energo_monitoring.presentation.viewmodel.CheckLengthViewModel;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CheckLengthOfStraightLinesPresenter {

    CheckLengthOfStraightLinesAreasActivity activity;
    private final ArrayList<FlowTransducerCheckLengthResult> results;
    private final FlowTransducerPhotoAdapter photosAdapter;
    public ArrayList<Bitmap> photos;
    public int currentId;
    ActivityResultLauncher<Intent> takePhotoLauncher;
    public ArrayList<DeviceFlowTransducer> devices;
    CheckLengthViewModel model;
    public Uri lastCreatedPath;

    public CheckLengthOfStraightLinesPresenter(CheckLengthOfStraightLinesAreasActivity activity,
                                               ClientDataBundle dataBundle, int dataId) {
        this.activity = activity;
        devices = new ArrayList<>(dataBundle.getDeviceFlowTransducers());

        photosAdapter = new FlowTransducerPhotoAdapter(activity.getApplicationContext(), this);

        initPhotos();

        results = new ArrayList<>();
        for(int i = 0; i < devices.size(); ++i){
            results.add(new FlowTransducerCheckLengthResult(i, devices.get(i), dataId));
        }

        takePhotoLauncher = activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                                    lastCreatedPath);

                            results.get(model.getCurrentDeviceId().getValue()).photosString +=
                                    lastCreatedPath + ";";

                            addPhoto(photo);
                            photosAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show();
                        }
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

    public void saveLengths(String lengthBefore, String lengthAfter){
        results.get(currentId).setLengthBefore(lengthBefore);
        results.get(currentId).setLengthAfter(lengthAfter);
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
        if(vh.getLayoutPosition() == photos.size() - 1) {
            lastCreatedPath = LoadImageManager.getPhotoUri(activity);
            LoadImageManager.takePhoto(activity, takePhotoLauncher, lastCreatedPath);
        }
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

    public void insertDataToDb(Context context){
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    db.resultDataDAO().insertFlowTransducerCheckLengthResults(results);
                });
    }
}
