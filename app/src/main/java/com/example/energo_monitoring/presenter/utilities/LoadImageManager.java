package com.example.energo_monitoring.presenter.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoadImageManager {

    private Context context;
    private ActivityResultLauncher<Intent> takePhotoResult;
    private ActivityResultLauncher<Intent> getPhotoFromGalleryResult;
    private ActivityResultLauncher<Intent> takePermissions;

    public LoadImageManager(Context context, ActivityResultLauncher<Intent> takePhotoResult,
                            ActivityResultLauncher<Intent> getPhotoFromGalleryResult,
                            ActivityResultLauncher<Intent> takePermissions) {
        this.context = context;
        this.takePhotoResult = takePhotoResult;
        this.getPhotoFromGalleryResult = getPhotoFromGalleryResult;
        this.takePermissions = takePermissions;
    }

    public static void takePhoto(ActivityResultLauncher<Intent> launcher){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        launcher.launch(cameraIntent);
    }

    public void pickFromGallery(){
        if(isPermissionGranted()) {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            getPhotoFromGalleryResult.launch(galleryIntent);
        } else {
            takePermission();
        }
    }

    public boolean isPermissionGranted(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int readExternalStorage = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            return readExternalStorage == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void takePermission(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                takePermissions.launch(intent);
            } catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                takePermissions.launch(intent);
            }
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        if(grantResults.length > 0){
            if(requestCode == 101){
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if(readExternalStorage){
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");
                    galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    getPhotoFromGalleryResult.launch(galleryIntent);
                }
            }
        }
    }
}
