package com.marekguran.rozvrh.ui;

import android.app.Application;

public class DynamicColors extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        com.google.android.material.color.DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
