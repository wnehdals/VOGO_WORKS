package com.example.vogoworks.ui.main

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.util.Log
import androidx.activity.viewModels
import com.example.vogoworks.R
import com.example.vogoworks.base.BaseActivity
import com.example.vogoworks.databinding.ActivityMainBinding
import com.example.vogoworks.service.FusedLocationService
import com.example.vogoworks.ui.write.WriteActivity
import com.example.vogoworks.util.Const.ACTION_START_LOCATION_SERVICE
import com.example.vogoworks.util.Const.ACTION_STOP_LOCATION_SERVICE
import com.example.vogoworks.util.Const.RUN_APP_PACKAGE
import com.example.vogoworks.util.Const.SERVICE_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.activity_main

    private val alarmAdapter: AlarmAdapter by lazy { AlarmAdapter(this) }
    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            mainFt.setOnClickListener {
                Intent(this@MainActivity, WriteActivity::class.java).also { startActivity(it) }
            }

        }
    }

    override fun initView() {
        with(binding) {
            mainRv.adapter = alarmAdapter
            mainRv.setHasFixedSize(true)
        }
    }

    private fun isLocationServiceRunning(): Boolean {
        val activityManager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getRunningServices(Int.MAX_VALUE).forEach {
            Log.e("serviceName", it.service.className)
            if (it.service.className == SERVICE_NAME) {
                if (it.foreground) {
                    return true
                }
            } else {
                return false
            }
        }
        return false
    }
    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent(applicationContext, FusedLocationService::class.java)
                .setAction(ACTION_START_LOCATION_SERVICE)
                .also { startService(it) }
        }
    }
    private fun stopLocationService() {
        if (isLocationServiceRunning()) {
            Intent(applicationContext, FusedLocationService::class.java)
                .setAction(ACTION_STOP_LOCATION_SERVICE)
                .also { startService(it) }
        }
    }
}