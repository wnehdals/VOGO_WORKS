package com.example.vogoworks.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    open var ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        toast.value = throwable.message
    }

   val toast = MutableLiveData<String>()
}