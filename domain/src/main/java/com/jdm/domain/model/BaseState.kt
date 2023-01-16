package com.jdm.domain.model

sealed class State {
    object Uninitialized : State() // 초기 상태

    object Loading : State() // 로딩 상태

    data class Success<T>(
        val resp: T
    ) : State()
    data class Fail<T>(
        val resp: T
    ) : State()
}
