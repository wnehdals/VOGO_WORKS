package com.example.vogoworks

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vogoworks.base.ViewBindingActivity
import com.example.vogoworks.databinding.ActivityMainBinding
import com.example.vogoworks.util.GPSListener

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main
    private val locationManager: LocationManager by lazy {
        this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val locationRequestTimeInterval = (1000 * 30).toLong() // 30초
    private val locationRequestDistanceInterval = 10.0f // 10 meter
    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    private val gpsListener = GPSListener()
    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var permissionFlag = true
            for(entry in it.entries) {
                if (!entry.value) {
                    permissionFlag = false
                    break
                }
            }
            if (!permissionFlag) {
                Toast.makeText(this, getString(R.string.location_permission_no), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                setLocationManager()
            }
        }
    override fun subscribe() {
    }
    @SuppressLint("MissingPermission")
    private fun setLocationManager() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            locationRequestTimeInterval,
            locationRequestDistanceInterval,
            gpsListener
        )
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            locationRequestTimeInterval,
            locationRequestDistanceInterval,
            gpsListener
        )

    }
}