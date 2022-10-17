package com.example.vogoworks

import android.app.Application
import androidx.work.*
import com.example.vogoworks.util.Const.BASE_LATITUDE
import com.example.vogoworks.util.Const.BASE_LONGITUDE
import com.example.vogoworks.util.Const.VOGO_LATITUDE
import com.example.vogoworks.util.Const.VOGO_LONGITUDE
import com.example.vogoworks.util.MyLocationManager
import com.example.vogoworks.worker.GPSWorker
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
@HiltAndroidApp
class VogoWorksApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        VOGO_LATITUDE = Hawk.get(BASE_LATITUDE) ?: 37.4965092
        VOGO_LONGITUDE = Hawk.get(BASE_LONGITUDE) ?: 127.0297701
    }


}