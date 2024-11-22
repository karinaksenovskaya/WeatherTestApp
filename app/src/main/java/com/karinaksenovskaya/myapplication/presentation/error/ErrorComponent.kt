package com.karinaksenovskaya.myapplication.presentation.error

import kotlinx.coroutines.flow.StateFlow

interface ErrorComponent {


    val model: StateFlow<ErrorStore.State>

    fun onCLickReturn()
}