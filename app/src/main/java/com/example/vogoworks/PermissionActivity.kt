package com.example.vogoworks

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.work.Constraints
import androidx.work.NetworkType
import com.example.vogoworks.base.ViewBindingActivity
import com.example.vogoworks.databinding.ActivityPermisionBinding
import com.example.vogoworks.util.GPSListener
import com.example.vogoworks.util.hasPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionActivity : ViewBindingActivity<ActivityPermisionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_permision
    private val locationManager: LocationManager by lazy {
        this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private val viewModel: PermissionViewModel by viewModels()
    private val locationRequestTimeInterval = (1000 * 30).toLong() // 30초
    private val locationRequestDistanceInterval = 10.0f // 10 meter
    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        //android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
    private val STEP_1 = 1
    private val STEP_2 = 2
    private val STEP_3 = 3
    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var permissionFlag = true
            for (entry in it.entries) {
                if (!entry.value) {
                    permissionFlag = false
                    break
                }
            }
            if (!permissionFlag) {
                Toast.makeText(this, getString(R.string.location_permission_no), Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                viewModel.setPermissionStep(STEP_3)
            }
        }

    override fun initView() {

    }
    private fun step1Check () {
        if (isEnableLocationSensor()) {
             viewModel.setPermissionStep(STEP_2)
        } else {
            enableLocationSensorSettings()
        }
    }

    override fun initEvent() {
        binding.mainGuideStartButton.setOnClickListener {
            //shouldShowRequestPermissionRationale(permission[0])
            requestMultiplePermissionLauncher.launch(permission)
        }
    }

    override fun subscribe() {
        viewModel.permissionStep.observe(this) {
            when (it) {
                STEP_1 -> {
                    step1Check()
                }
                STEP_2 -> {
                    if (this.hasPermission(permission)) {
                        viewModel.setPermissionStep(STEP_3)
                    } else {

                    }
                }
                STEP_3 -> {
                    Intent(this, BackGroundPermissionActivity::class.java).also {
                        startActivity(it)
                    }
                }
            }
        }

    }

    private fun enableLocationSensorSettings() {
        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).run { startActivity(this) }
    }

    private fun isEnableLocationSensor(): Boolean {
        return isGPSEnabled() || isNetworkEnabled()
    }

    private fun isGPSEnabled() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private fun isNetworkEnabled() =
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

}