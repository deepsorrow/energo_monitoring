package com.example.energo_monitoring.checks.adapters;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class DataBindingAdapters {

    @BindingAdapter("android:drawableTop")
    public static void setDrawableTop(TextView textView, int id){
        textView.setCompoundDrawablesWithIntrinsicBounds(0, id, 0, 0);
    }

    @BindingAdapter("android:drawableLeft")
    public static void setDrawableLeft(TextView textView, int id){
        textView.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0);
    }
}
