package com.example.energo_monitoring.presentation.presenters.utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

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

    public static void takePhoto(Context context, ActivityResultLauncher<Intent> launcher, Uri path){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, path);
        launcher.launch(cameraIntent);
    }

    public static Uri getPhotoUri(Context context){
        File file = null;
        try {
            file = File.createTempFile("photo", ".png", context.getFilesDir());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }

        return FileProvider.getUriForFile(context,
                context.getApplicationContext().getPackageName() + ".provider", file);
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

    public static String getBase64FromPath(Context context, String paths){
        String result = "";
        for(String path : paths.split(";")) {
            if(path.isEmpty())
                continue;

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                        Uri.parse(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            result += encoded + ";";
        }

        if(!result.isEmpty())
            result.substring(0, result.length() - 1);

        return result;
    }

//    public static String getFilePath(Context context, Uri contentUri){
//        String[] projection = { MediaStore.Images.Media.EXTERNAL_CONTENT_URI };
//        Cursor cursor = null;
//        try {
//            String selection = null;
//            String[] selectionArgs = null;
//
//            cursor = context.getContentResolver().query(contentUri, projection, selection,
//                    selectionArgs, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            if (cursor.moveToFirst()) {
//                return cursor.getString(column_index);
//            }
//        } catch (Exception e) {
//        }
//
//        return "";
//    }
}
