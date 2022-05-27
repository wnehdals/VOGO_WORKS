package com.example.vogoworks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(): ViewModel() {
    private val _permissionStep = MutableLiveData<Int>(1)
    val permissionStep: LiveData<Int> get() = _permissionStep

    fun setPermissionStep(step: Int) {
        _permissionStep.value = step
    }
}