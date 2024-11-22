package com.karinaksenovskaya.myapplication.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>

    fun onCLickSearch()

    fun onCLickBack()

}