package com.karinaksenovskaya.myapplication.presentation.error

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.karinaksenovskaya.myapplication.presentation.error.ErrorStore.Intent
import com.karinaksenovskaya.myapplication.presentation.error.ErrorStore.Label
import com.karinaksenovskaya.myapplication.presentation.error.ErrorStore.State
import javax.inject.Inject

interface ErrorStore : Store<Intent, State, Label> {


    sealed interface Intent {

        data object ReturnClick : Intent
    }

    data class State(
        val errorMsg: String
    )

    sealed interface Label {

        data object ReturnClick : Label
    }
}

class ErrorStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory
) {

    fun create(errorMsg: String): ErrorStore =
        object : ErrorStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ErrorStore",
            initialState = State(errorMsg),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ReturnClick -> {
                    publish(Label.ReturnClick)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = this
    }
}
