package com.karinaksenovskaya.myapplication.presentation.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.karinaksenovskaya.myapplication.domain.entity.City
import com.karinaksenovskaya.myapplication.presentation.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultDetailsComponent @AssistedInject constructor(
    private val detailsStoreFactory: DetailsStoreFactory,
    @Assisted("onSearchCityClicked") private val onSearchCityClicked: () -> Unit,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("city") private val city: City,
    @Assisted("openReason") private val openReason: OpenReason,
    @Assisted("componentContext") componentContext: ComponentContext
) : DetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { detailsStoreFactory.create(openReason, city) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    DetailsStore.Label.ClickSearch -> onSearchCityClicked()
                    DetailsStore.Label.ClickBack -> onBackClicked()
                }
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailsStore.State> = store.stateFlow

    override fun onCLickSearch() {
        store.accept(DetailsStore.Intent.ClickSearch)
    }

    override fun onCLickBack() {
        store.accept(DetailsStore.Intent.ClickBack)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onSearchCityClicked") onSearchCityClicked: () -> Unit,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("city") city: City,
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultDetailsComponent
    }
}