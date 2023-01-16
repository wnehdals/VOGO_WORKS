package com.example.vogoworks.ui.permission

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vogoworks.R
import com.example.vogoworks.base.BaseActivity
import com.example.vogoworks.databinding.ActivityPermisionBinding
import com.example.vogoworks.util.hasPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionActivity : BaseActivity<ActivityPermisionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_permision
    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
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
                setResult(RESULT_OK)
                finish()
            }
        }

    override fun initView() {
    }


    override fun initEvent() {
        binding.mainGuideStartButton.setOnClickListener {
            if (isEnableLocationSensor()) {
                if (this.hasPermission(permission)) {
                } else {
                    requestMultiplePermissionLauncher.launch(permission)
                }
            } else {
                enableLocationSensorSettings()
            }
        }
    }

    override fun subscribe() {
    }


}