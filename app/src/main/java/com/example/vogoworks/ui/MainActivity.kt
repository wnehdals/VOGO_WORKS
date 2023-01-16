package com.example.vogoworks.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.util.Log
import com.example.vogoworks.R
import com.example.vogoworks.base.BaseActivity
import com.example.vogoworks.databinding.ActivityMainBinding
import com.example.vogoworks.service.FusedLocationService
import com.example.vogoworks.ui.write.WriteActivity
import com.example.vogoworks.util.Const.ACTION_START_LOCATION_SERVICE
import com.example.vogoworks.util.Const.ACTION_STOP_LOCATION_SERVICE
import com.example.vogoworks.util.Const.RUN_APP_PACKAGE
import com.example.vogoworks.util.Const.SERVICE_NAME


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main
    var flag = false
    override fun subscribe() {
    }

    override fun initEvent() {
        with(binding) {
            mainFt.setOnClickListener {
                Intent(this@MainActivity, WriteActivity::class.java).also { startActivity(it) }
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
                if (mApps[i].activityInfo.packageName.startsWith(RUN_APP_PACKAGE)) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }
        return isExist
    }

    override fun initView() {

    }

    override fun onStart() {
        super.onStart()
        flag = intent.getBooleanExtra("hiworks", false)
        if (flag) {
            if (getPackageList()) {
                val intent = packageManager.getLaunchIntentForPackage(RUN_APP_PACKAGE)
                intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
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