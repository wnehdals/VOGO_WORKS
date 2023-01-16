package com.example.vogoworks.ui.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vogoworks.base.BaseViewModel
import com.jdm.domain.model.AlarmInfo
import com.jdm.domain.model.State
import com.jdm.domain.usecase.InsertAlarmInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    private val insertAlarmInfoUseCase: InsertAlarmInfoUseCase
) : BaseViewModel() {
    private val _insertAlarmInfoState = MutableLiveData<State>(State.Uninitialized)
    val insertAlarmInfoState: LiveData<State> get() = _insertAlarmInfoState


    var alarmInfo = AlarmInfo()

    fun isCorrectTimeInterval(endHour: Int, endMinute: Int): Boolean {
        if (alarmInfo.startHour <= endHour) {
            if (alarmInfo.startMinute <= endMinute) {
                return true
            } else {
                return false
            }
        } else {
            return false
        }
    }

    fun insertAlarmInfo() {
        viewModelScope.launch(ceh) {
            withContext(Dispatchers.IO) {
                val resp = insertAlarmInfoUseCase(alarmInfo)
                _insertAlarmInfoState.postValue(State.Success(resp))
            }
        }
    }
}
