package com.example.energo_monitoring.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.energo_monitoring.model.api.ClientDataBundle;
import com.example.energo_monitoring.presenter.utilities.LoadImageManager;
import com.example.energo_monitoring.view.activity.ProjectPhotoActivity;

import static android.app.Activity.RESULT_OK;

public class ProjectPhotoPresenter {

    private ActivityResultLauncher<Intent> takePhotoResult;
    private ActivityResultLauncher<Intent> getPhotoFromGalleryResult;
    private ActivityResultLauncher<Intent> takePermissions;
    private LoadImageManager loadImageManager;
    private ProjectPhotoActivity projectPhotoActivity;

    public ProjectPhotoPresenter(ProjectPhotoActivity projectPhotoActivity) {
        this.projectPhotoActivity = projectPhotoActivity;
    }

    public void registerLaunchers(ImageView photoPreview, TextView photoWasNotFoundText){
        takePhotoResult = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bitmap thumbnail = (Bitmap) result.getData().getExtras().get("data");
                        photoPreview.setImageBitmap(thumbnail);

                        photoWasNotFoundText.setText("");
                    }
                });

        getPhotoFromGalleryResult = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri selectedImage = result.getData().getData();

                        photoWasNotFoundText.setText("");

                        photoPreview.setImageURI(selectedImage);
                    }
                });

        takePermissions = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK){
                        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
                            if(Environment.isExternalStorageManager()){
                                loadImageManager.pickFromGallery();
                            } else{
                                loadImageManager.takePermission();
                            }
                        }
                    }
                });

        loadImageManager = new LoadImageManager(projectPhotoActivity, takePhotoResult,
                getPhotoFromGalleryResult, takePermissions);
    }

    public ActivityResultLauncher<Intent> getTakePhotoResult() {
        return takePhotoResult;
    }

    public ActivityResultLauncher<Intent> getGetPhotoFromGalleryResult() {
        return getPhotoFromGalleryResult;
    }

    public ActivityResultLauncher<Intent> getTakePermissions() {
        return takePermissions;
    }

    public LoadImageManager getLoadImageManager() {
        return loadImageManager;
    }

    public Bitmap getBitmapFromBase64(ClientDataBundle clientDataBundle){
        String photoBase64 = clientDataBundle.getProject().getPhotoBase64()
                .replace("data:image/png;base64,", "")
                .replace("data:image/jpeg;base64,","");

        byte[] decodedString = Base64.decode(photoBase64, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
    }
}
