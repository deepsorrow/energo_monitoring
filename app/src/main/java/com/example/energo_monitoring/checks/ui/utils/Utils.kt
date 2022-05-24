package com.example.energo_monitoring.checks.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energo_monitoring.R;
import com.example.energo_monitoring.checks.data.db.OtherInfo;
import com.example.energo_monitoring.checks.data.db.ResultDataDatabase;
import com.example.energo_monitoring.checks.ui.activities.CheckMainActivity;
import com.example.energo_monitoring.checks.ui.adapters.PhotosRecyclerAdapter;

import java.io.IOException;

public class Utils {
    /**
     * Записать прогресс для восстанавления из главной страницы при необходимости
     * @param dataId - идентификатор данных для отправки на сервер
     */
    public static void logProgress(Context context, int currentScreen, int dataId) {
        ResultDataDatabase db = ResultDataDatabase.getDatabase(context);
        OtherInfo otherInfo = db.resultDataDAO().getOtherInfo(dataId);
        if (otherInfo == null)
            otherInfo = new OtherInfo(dataId);

        otherInfo.completedScreens = Math.max(otherInfo.completedScreens, currentScreen);
        otherInfo.localVersion += 1;
        otherInfo.currentScreen = currentScreen;

        db.resultDataDAO().insertOtherInfo(otherInfo);
    }

    public static void initPhotos(Fragment fragment, int recyclerId, PhotosRecyclerAdapter recyclerAdapter, int columns) {
        Context context = fragment.requireContext();
        CheckMainActivity activity = (CheckMainActivity) fragment.requireActivity();

        RecyclerView recyclerView = fragment.getView().findViewById(recyclerId);

        ActivityResultLauncher<Intent> launcher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            Bitmap photo = MediaStore.Images.Media.getBitmap(activity.getContentResolver(),
                                    activity.getLastCreatedPath());

                            recyclerAdapter.addPhoto(photo);
                            recyclerAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                });

        recyclerAdapter.setPhotoLauncher(launcher);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);

        if (columns == 1) {
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    public static void initPhotos(Fragment fragment, int recyclerId, int listItem, int columns) {
        PhotosRecyclerAdapter recyclerAdapter = new PhotosRecyclerAdapter(fragment.requireContext(), listItem);
        initPhotos(fragment, recyclerId, recyclerAdapter, columns);
    }

    public static void initPhotos(Fragment fragment, int recyclerId) {
        PhotosRecyclerAdapter recyclerAdapter = new PhotosRecyclerAdapter(fragment.requireContext(), R.layout.list_item_final_photos_photo_card);
        initPhotos(fragment, recyclerId, recyclerAdapter, 1);
    }
}
