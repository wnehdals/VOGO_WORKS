package com.example.vogoworks.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.vogoworks.R
import com.example.vogoworks.databinding.DialogTimePickerBinding

class TimePickerDialog(
    private val selectTimePickerListener: SelectTimePickerListener
): DialogFragment(), TimePicker.OnTimeChangedListener {
    private lateinit var binding: DialogTimePickerBinding
    private var hour: Int = 0
    private var minute: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogTimePickerBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dialogTimePicker.setOnTimeChangedListener(this@TimePickerDialog)
        this.hour = binding.dialogTimePicker.hour
        this.minute = binding.dialogTimePicker.minute
        initEvent()

    }
    private fun initEvent() {
        with(binding) {
            dialogTimePickerCancel.setOnClickListener {
                dismiss()
            }
            dialogTimePickerConfirm.setOnClickListener {
                selectTimePickerListener.selectTime(this@TimePickerDialog, hour, minute)
            }
        }
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute
    }
    companion object {
        val TAG = TimePickerDialog.javaClass.simpleName
    }
}