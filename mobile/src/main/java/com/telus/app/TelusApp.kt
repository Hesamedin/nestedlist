package com.telus.app

import android.app.Application
import timber.log.Timber

class TelusApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}