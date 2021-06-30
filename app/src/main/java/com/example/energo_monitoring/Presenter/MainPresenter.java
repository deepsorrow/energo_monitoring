package com.example.energo_monitoring.Presenter;

import android.content.Context;

public class MainPresenter {
    //private final ServerService serverService;
    public Context context;

    public MainPresenter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
