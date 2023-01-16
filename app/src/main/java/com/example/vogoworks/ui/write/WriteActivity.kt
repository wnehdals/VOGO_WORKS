package com.example.vogoworks.ui.write

import android.content.Intent
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.vogoworks.R
import com.example.vogoworks.base.BaseActivity
import com.example.vogoworks.databinding.ActivityWriteBinding
import com.example.vogoworks.ui.permission.PermissionActivity
import com.example.vogoworks.util.LoadingDialog
import com.example.vogoworks.util.SelectTimePickerListener
import com.example.vogoworks.util.TimePickerDialog
import com.example.vogoworks.util.hasPermission
import com.google.android.gms.location.*
import com.jdm.domain.model.State
import dagger.hilt.android.AndroidEntryPoint
import soup.neumorphism.ShapeType.Companion.FLAT
import soup.neumorphism.ShapeType.Companion.PRESSED

@AndroidEntryPoint
class WriteActivity : BaseActivity<ActivityWriteBinding>() {
    val writeViewModel: WriteViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.activity_write
    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(getString(R.string.location_searching))
    }
    private val permission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private val permissionActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            binding.searchLocationBt.performClick()
        }
    }
    private var receiveLocationCnt = 0
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.e("jdm_tag", "${latitude} / ${longitude}")
                    receiveLocationCnt++
                    if (receiveLocationCnt == 2) {
                        setLatitude(latitude.toFloat())
                        setLongitude(longitude.toFloat())
                        stopLocationUpdate()
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }
    private val START_TIME_CHECK = 1
    private val END_TIME_CHECK = 2
    private val LOCATION_CHECK = 3
    private val COMPLETE = 4
    override fun subscribe() {
        writeViewModel.insertAlarmInfoState.observe(this) {
            when (it) {
                is State.Uninitialized -> {}
                is State.Loading -> {}
                is State.Success<*> -> {
                    finish()
                }
                is State.Fail<*> -> {

                }
            }
        }
    }
    private fun checkAlarmInfoInput(): Int {
        return if (writeViewModel.alarmInfo.startHour == -1 || writeViewModel.alarmInfo.startMinute == -1) {
            START_TIME_CHECK
        } else if (writeViewModel.alarmInfo.endHour == -1 || writeViewModel.alarmInfo.endMinute == -1) {
            END_TIME_CHECK
        } else if (writeViewModel.alarmInfo.latitude == 0f || writeViewModel.alarmInfo.longitude == 0f) {
            LOCATION_CHECK
        } else {
            COMPLETE
        }
    }

    override fun initEvent() {
        with(binding) {
            alarmWriteCompleteTv.setOnClickListener {
                when (checkAlarmInfoInput()) {
                    START_TIME_CHECK -> {
                        showToast(getString(R.string.please_input_alarm_start_time))
                    }
                    END_TIME_CHECK -> {
                        showToast(getString(R.string.please_input_alarm_end_time))
                    }
                    LOCATION_CHECK -> {
                        showToast(getString(R.string.please_input_alarm_location))
                    }
                    COMPLETE -> {
                        writeViewModel.insertAlarmInfo()
                    }
                }
            }
            locationDirectionTv.setOnClickListener {
                setCompleteSelect()
                locationDirectionTv.isSelected = !locationDirectionTv.isSelected
                if (locationDirectionTv.isSelected) {
                    locationDirectionTv.setShapeType(PRESSED)
                    locationDirectionGuideTv.text = getString(R.string.location_in_guide)
                    locationDirectionTv.setText(getString(R.string.en_in))
                    locationDirectionTv.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.color_745ff2
                        )
                    )
                } else {
                    locationDirectionTv.setShapeType(FLAT)
                    locationDirectionGuideTv.text = getString(R.string.location_out_guide)
                    locationDirectionTv.setText(getString(R.string.en_out))
                    locationDirectionTv.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.color_fb7474
                        )
                    )
                }
            }
            alarmTimeStartTv.setOnClickListener {
                TimePickerDialog(object : SelectTimePickerListener {
                    override fun selectTime(dialog: DialogFragment, hour: Int, minute: Int) {
                        writeViewModel.alarmInfo.startHour = hour
                        writeViewModel.alarmInfo.startMinute = minute
                        alarmTimeStartTv.text =
                            String.format(
                                "%02d:%02d",
                                writeViewModel.alarmInfo.startHour,
                                writeViewModel.alarmInfo.startMinute
                            )
                        alarmTimeStartTv.setTextColor(
                            ContextCompat.getColor(
                                this@WriteActivity,
                                R.color.color_252525
                            )
                        )
                        dialog.dismiss()
                    }
                }).show(supportFragmentManager, TimePickerDialog.TAG)

            }
            alarmTimeEndTv.setOnClickListener {
                if (writeViewModel.alarmInfo.startHour == -1)
                    return@setOnClickListener

                TimePickerDialog(object : SelectTimePickerListener {
                    override fun selectTime(dialog: DialogFragment, hour: Int, minute: Int) {
                        if (writeViewModel.isCorrectTimeInterval(hour, minute)) {
                            writeViewModel.alarmInfo.endHour = hour
                            writeViewModel.alarmInfo.endMinute = minute
                            alarmTimeEndTv.text =
                                String.format(
                                    "%02d:%02d",
                                    writeViewModel.alarmInfo.endHour,
                                    writeViewModel.alarmInfo.endMinute
                                )
                            alarmTimeEndTv.setTextColor(
                                ContextCompat.getColor(
                                    this@WriteActivity,
                                    R.color.color_252525
                                )
                            )
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                this@WriteActivity,
                                "시작시간보다 작을 수 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        setCompleteSelect()
                    }
                }).show(supportFragmentManager, TimePickerDialog.TAG)
            }
            searchLocationBt.setOnClickListener {
                if (this@WriteActivity.hasPermission(permission)) {
                    loadingDialog.show(supportFragmentManager, LoadingDialog.TAG)
                    getCurrentLocation()
                } else {
                    goToPermissionActivity()
                }
            }
        }
    }

    override fun initView() {

    }
    fun setCompleteSelect() {
        binding.alarmWriteCompleteTv.isSelected = checkAlarmInfoInput() == COMPLETE
    }
    fun setLatitude(latitude: Float) {
        binding.locationLatitudeTv.text = "위도 : ${String.format("%.02f", latitude)}"
        writeViewModel.alarmInfo.latitude = String.format("%.06f", latitude).toFloat()
        setCompleteSelect()
    }
    fun setLongitude(longitude: Float) {
        binding.locationLongitudeTv.text = "경도 : ${String.format("%.02f", longitude)}"
        writeViewModel.alarmInfo.longitude = String.format("%.06f", longitude).toFloat()
        setCompleteSelect()
    }
    fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.getMainLooper())
    }
    fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 2 * 1000
            fastestInterval = 2 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
    private fun goToPermissionActivity() {
        Intent(this, PermissionActivity::class.java).also { permissionActivityLauncher.launch(it) }
    }
}