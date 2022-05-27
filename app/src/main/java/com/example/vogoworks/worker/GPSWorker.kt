package com.example.vogoworks.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.vogoworks.util.MyLocationManager

class GPSWorker(appContext: Context, workerParameter: WorkerParameters): CoroutineWorker(appContext, workerParameter) {
    val myLocationManager = MyLocationManager.getInstance(appContext,::callback)
    var latitude: Double = 37.4966277
    var longitude: Double = 127.0298918
    fun callback(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
        Log.e("workmanager","latitude : ${this.latitude} / longitude : ${this.longitude}")
    }
    override suspend fun doWork(): Result {
        myLocationManager.startLocationUpdates()
        return Result.success()
    }
}