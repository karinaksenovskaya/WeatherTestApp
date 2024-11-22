package com.karinaksenovskaya.myapplication.presentation.error

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.google.android.gms.location.FusedLocationProviderClient
import com.karinaksenovskaya.myapplication.presentation.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultErrorComponent @AssistedInject constructor(
    private val errorStoreFactory: ErrorStoreFactory,
    @Assisted("onBackClicked") private val onReturnClicked: () -> Unit,
    @Assisted("errorMsg") private val errorMsg: String,
    @Assisted("fusedLocationClient") private val fusedLocationClient: FusedLocationProviderClient,
    @Assisted("componentContext") componentContext: ComponentContext
) : ErrorComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore { errorStoreFactory.create(errorMsg) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    ErrorStore.Label.ReturnClick -> onReturnClicked()
                }
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ErrorStore.State> = store.stateFlow

    override fun onCLickReturn() {
        store.accept(ErrorStore.Intent.ReturnClick)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onReturnClicked: () -> Unit,
            @Assisted("errorMsg") errorMsg: String,
            @Assisted("fusedLocationClient") fusedLocationClient: FusedLocationProviderClient,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultErrorComponent
    }

}