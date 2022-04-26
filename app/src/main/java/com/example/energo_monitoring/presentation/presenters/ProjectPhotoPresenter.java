package com.example.energo_monitoring.presentation.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import com.example.energo_monitoring.data.api.ClientDataBundle;
import com.example.energo_monitoring.data.api.ClientInfo;
import com.example.energo_monitoring.data.api.OrganizationInfo;
import com.example.energo_monitoring.data.api.ProjectDescription;
import com.example.energo_monitoring.data.db.ResultDataDatabase;
import com.example.energo_monitoring.presentation.presenters.utilities.LoadImageManager;
import com.example.energo_monitoring.presentation.activities.ProjectPhotoActivity;

import static android.app.Activity.RESULT_OK;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProjectPhotoPresenter {

    private ActivityResultLauncher<Intent> takePhotoResult;
    private ActivityResultLauncher<Intent> getPhotoFromGalleryResult;
    private ActivityResultLauncher<Intent> takePermissions;
    private LoadImageManager loadImageManager;
    private ProjectPhotoActivity projectPhotoActivity;
    private int dataId;

    public ProjectPhotoPresenter(ProjectPhotoActivity projectPhotoActivity, int dataId) {
        this.projectPhotoActivity = projectPhotoActivity;
        this.dataId = dataId;
    }

    public void registerLaunchers(ImageView photoPreview, TextView photoWasNotFoundText) {
        takePhotoResult = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Context context = photoPreview.getContext();
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(context.getContentResolver(),
                                    projectPhotoActivity.lastCreatedPath);

                            photoPreview.setImageBitmap(photo);

                            photoWasNotFoundText.setText("");

                            insertPhotoProjectToDb(context, dataId);
                        } catch (IOException e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

        getPhotoFromGalleryResult = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Context context = photoPreview.getContext();

                    if (result.getResultCode() == RESULT_OK) {
                        Uri selectedImage = result.getData().getData();

                        photoWasNotFoundText.setText("");

                        photoPreview.setImageURI(selectedImage);

                        insertPhotoProjectToDb(context, dataId);
                    }
                });

        takePermissions = projectPhotoActivity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                            if (Environment.isExternalStorageManager()) {
                                loadImageManager.pickFromGallery();
                            } else {
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

    public Bitmap getBitmapFromBase64(ClientDataBundle clientDataBundle) {
        String photoBase64 = clientDataBundle.getProject().getPhotoBase64()
                .replace("data:image/png;base64,", "")
                .replace("data:image/jpeg;base64,", "")
                .replace("data:image/gif;base64,", "");

        byte[] decodedString = Base64.decode(photoBase64, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
    }

    public void insertDataToDb(Context context, Response<ClientDataBundle> response, int dataId) {
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    ClientInfo clientInfo = response.body().getClientInfo();
                    clientInfo.dataId = dataId;
                    db.resultDataDAO().insertClientInfo(clientInfo);

                    ProjectDescription projectDescription = response.body().getProject();
                    projectDescription.dataId = dataId;

//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BitmapDrawable drawable = (BitmapDrawable) projectPhotoActivity.binding.photoPreview.getDrawable();
                    if(drawable != null) {
                        Bitmap bitmap = drawable.getBitmap();

                        File file = File.createTempFile("projectPhoto", ".png", context.getFilesDir());
                        FileOutputStream fOut = new FileOutputStream(file);

                        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                        fOut.flush();
                        fOut.close();

                        Uri path = FileProvider.getUriForFile(context,
                                context.getApplicationContext().getPackageName() + ".provider", file);

                        projectDescription.photoPath = path.toString();
                    }

                    db.resultDataDAO().insertProjectDescription(projectDescription);

                    OrganizationInfo organizationInfo = response.body().organizationInfo;
                    organizationInfo.dataId = dataId;
                    db.resultDataDAO().insertOrganizationInfo(organizationInfo);
                });
    }

    public void insertPhotoProjectToDb(Context context, int dataId) {
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe((value) -> {
                    ProjectDescription projectDescription = db.resultDataDAO().getProjectDescription(dataId);

                    BitmapDrawable drawable = (BitmapDrawable) projectPhotoActivity.binding.photoPreview.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    File file = File.createTempFile("projectPhoto", ".png", context.getFilesDir());
                    FileOutputStream fOut = new FileOutputStream(file);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();

                    Uri path = FileProvider.getUriForFile(context,
                            context.getApplicationContext().getPackageName() + ".provider", file);

                    projectDescription.photoPath = path.toString();

                    db.resultDataDAO().insertProjectDescription(projectDescription);
                });
    }
}
