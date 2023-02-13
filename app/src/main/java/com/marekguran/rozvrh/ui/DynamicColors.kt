package com.marekguran.rozvrh.ui

import android.app.Application
import com.google.android.material.color.DynamicColors

class DynamicColors : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}