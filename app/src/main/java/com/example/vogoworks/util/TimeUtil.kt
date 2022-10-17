package com.example.vogoworks.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    var launchStart: Int = 11 * 60 + 49
    var launchEnd: Int = 13 * 60
    var morning: Int = 6 * 60 + 30
    var evening: Int = 22 * 60 + 30
    @RequiresApi(Build.VERSION_CODES.O)
    fun isLaunchTime(): Boolean {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val formatted = current.format(formatter)
        val timeArr = formatted.split(":")
        val time = (timeArr[0].toInt() * 60) + timeArr[1].toInt()
        //Log.e("time",timeArr[0] + " / " + timeArr[1])
        //Log.e("time","${time}")

        //Log.e("start_time","${launchStart}")
        //Log.e("end_time","${launchEnd}")
        if (launchStart < time && time < launchEnd) {
            return true
        } else {
            return false
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(): Int {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME
        val formatted = current.format(formatter)
        val timeArr = formatted.split(":")
        return (timeArr[0].toInt() * 60) + timeArr[1].toInt()
    }
}