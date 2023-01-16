package com.example.vogoworks.util

import androidx.fragment.app.DialogFragment

interface SelectTimePickerListener {
    fun selectTime(dialog: DialogFragment, hour: Int, minute: Int)
}