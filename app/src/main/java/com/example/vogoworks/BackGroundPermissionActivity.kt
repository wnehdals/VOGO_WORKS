package com.example.vogoworks

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.vogoworks.base.ViewBindingActivity
import com.example.vogoworks.databinding.ActivityBackGroundPermissionBinding
import com.example.vogoworks.util.hasPermission

class BackGroundPermissionActivity : ViewBindingActivity<ActivityBackGroundPermissionBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_back_ground_permission
    private val permission = android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
    private val requestMultiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Log.e("permission", it.toString())
            if (!it) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:" + this.packageName)
                startActivity(intent)
            }
        }

    override fun subscribe() {
    }

    override fun initEvent() {
        binding.backgroundPermissionStartButton.setOnClickListener {
            if (this.hasPermission(permission)) {
                Intent(this, MainActivity::class.java).also { startActivity(it) }
            } else {
                requestMultiplePermissionLauncher.launch(permission)
            }
        }
    }

    override fun initView() {

    }

}