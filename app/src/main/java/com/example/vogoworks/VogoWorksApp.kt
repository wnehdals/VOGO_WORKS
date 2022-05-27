package com.example.vogoworks

import android.app.Application
import androidx.work.*
import com.example.vogoworks.util.MyLocationManager
import com.example.vogoworks.worker.GPSWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
@HiltAndroidApp
class VogoWorksApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }


}