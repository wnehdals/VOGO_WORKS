package com.example.vogoworks.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class ViewModelBase : ViewModel() {
    private val TAG = ViewModelBase::class.simpleName
    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG,"ExceptionHandler : $throwable")
    }
    val isLoading = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
    }
}
