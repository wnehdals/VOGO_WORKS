package com.example.vogoworks

import android.content.DialogInterface
import android.content.Intent
import android.os.Looper
import android.util.Log
import com.example.vogoworks.base.ViewBindingActivity
import com.example.vogoworks.databinding.ActivityInitialBinding
import com.example.vogoworks.util.Const.BASE_LATITUDE
import com.example.vogoworks.util.Const.BASE_LONGITUDE
import com.example.vogoworks.util.Const.SET_BASE_GPS
import com.example.vogoworks.util.Const.VOGO_LATITUDE
import com.example.vogoworks.util.Const.VOGO_LONGITUDE
import com.example.vogoworks.util.DialogUtil
import com.google.android.gms.location.*
import com.orhanobut.hawk.Hawk

class InitialActivity : ViewBindingActivity<ActivityInitialBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_initial
    private var receiveLocationCnt = 0
    lateinit var fusedLocationClient: FusedLocationProviderClient
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            for (location in locationResult.locations) {
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    receiveLocationCnt++
                    if (receiveLocationCnt == 4) {
                        Hawk.put(BASE_LATITUDE, latitude)
                        Hawk.put(BASE_LONGITUDE, longitude)
                        stopLocationUpdate()
                        goToBackGroundPermissionActivity()
                    }
                    Log.e("service", "${latitude} / ${longitude}")
                }
            }
        }
    }
    override fun subscribe() {
    }

    override fun initEvent() {
    }

    override fun initView() {
        DialogUtil.makeSimpleDialog(this, "기준 위치 설정", getString(R.string.gps_guide_alert_message), "예", "아니오",
            object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    showSettingDialog()
                }
            }, object  : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    finish()
                }
            }, false
        ).show()
    }
    fun showSettingDialog() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.getMainLooper())
        showProgressDialog("설정중...")
    }
    fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10 * 1000
            fastestInterval = 5 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    fun goToBackGroundPermissionActivity() {
        hideProgressDialog()
        Hawk.put(SET_BASE_GPS, true)
        val intent = Intent(this, BackGroundPermissionActivity::class.java)
        VOGO_LATITUDE = Hawk.get(BASE_LATITUDE)
        VOGO_LONGITUDE = Hawk.get(BASE_LONGITUDE)
        startActivity(intent)
        finish()
    }
}