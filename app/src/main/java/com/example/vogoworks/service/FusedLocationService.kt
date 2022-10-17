package com.example.vogoworks.service

import android.app.*
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.os.Parcelable
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.vogoworks.MainActivity
import com.example.vogoworks.R
import com.example.vogoworks.util.Const
import com.example.vogoworks.util.Const.ACTION_START_LOCATION_SERVICE
import com.example.vogoworks.util.Const.ACTION_STOP_LOCATION_SERVICE
import com.example.vogoworks.util.Const.LOCATION_SERVICE_ID
import com.example.vogoworks.util.Const.VOGO_LATITUDE
import com.example.vogoworks.util.Const.VOGO_LONGITUDE
import com.example.vogoworks.util.TimeUtil
import com.example.vogoworks.util.hasPermission
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*

class FusedLocationService : Service() {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    val alpha = 0.0002
    val maxLatitude = VOGO_LATITUDE + alpha
    val minLatitude = VOGO_LATITUDE - alpha
    val maxLongitude = VOGO_LONGITUDE + alpha
    val minLongitude = VOGO_LONGITUDE - alpha
    lateinit var notificationManager: NotificationManagerCompat
    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            for (location in locationResult.locations) {
                var isLaunchTime = TimeUtil.isLaunchTime()
                if (isLaunchTime) {
                    Log.e("jdm_tag","launch time")
                    break
                }
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude

                    Log.e("service", "${latitude} / ${longitude}")
                    if (!isValide(latitude, longitude)) {
                        var title = "하이웍스 근태 체크"
                        var body = "하이웍스 근태를 체크해주세요"
                        var channelId = "VOGO 근태"
                        var pushNotiId = 1
                        val intent = Intent(this@FusedLocationService, MainActivity::class.java)
                            .putExtra("hiworks",true)
                        var notification = getNotification(title, body, channelId, pushNotiId,intent)
                        notificationManager.notify(pushNotiId, notification)
                    }
                }
            }
        }
    }
    fun getPackageList(): Boolean {
        var isExist = false
        val pkgMgr = packageManager
        val mApps: List<ResolveInfo>
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0)
        try {
            for (i in mApps.indices) {
                if (mApps[i].activityInfo.packageName.startsWith(Const.RUN_APP_PACKAGE)) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }
        return isExist
    }
    private fun isValide(latitude: Double, longitude: Double): Boolean {
        if (TimeUtil.morning < TimeUtil.getTime() && TimeUtil.getTime() < TimeUtil.launchStart) {
            if ((minLatitude <= latitude && latitude <= maxLatitude) && (minLongitude <= longitude && longitude <= maxLongitude)) {
                Log.e("isValide","오전 false / ${minLatitude} / ${minLongitude}")
                return false
            } else {
                Log.e("isValide","오전 true/ ${minLatitude} / ${minLongitude}")
                return true
            }
        } else  if (TimeUtil.launchStart <= TimeUtil.getTime() && TimeUtil.getTime() <= TimeUtil.evening){
            if ((minLatitude <= latitude && latitude <= maxLatitude) && (minLongitude <= longitude && longitude <= maxLongitude)) {
                Log.e("isValide","오후 true/ ${minLatitude} / ${minLongitude}")
                return true
            } else {
                Log.e("isValide","오후 false/ ${minLatitude} / ${minLongitude}")
                return false
            }
        }
        return true

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        notificationManager = NotificationManagerCompat.from(applicationContext)
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                if (action == ACTION_START_LOCATION_SERVICE) {
                    startLocationUpdates()
                } else if (action == ACTION_STOP_LOCATION_SERVICE) {
                    stopLocationUpdates()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
    fun getNotification(title: String, body: String, channelId: String, pushNotiId: Int, intent: Intent): Notification {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder: NotificationCompat.Builder? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(channelId) == null) {
                var channel = NotificationChannel(channelId, "알림", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(applicationContext, channelId)
        } else {
            builder = NotificationCompat.Builder(applicationContext)
        }
        builder
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(false)
        return builder.build()
    }
    fun startLocationUpdates() {
        var title = "VOGO 웍스"
        var body = "VOGO 웍스 사용을 중지하려면 탭하세요"
        var channelId = "VOGO 웍스"
        val intent = Intent(this, MainActivity::class.java)
        if (hasPermission(permissions)) {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.getMainLooper())
            startForeground(LOCATION_SERVICE_ID, getNotification(title = title, body = body, channelId = channelId, pushNotiId = 0, intent = intent))
        } else {
            return
        }
    }
    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }
    fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10 * 1000
            fastestInterval = 5 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}