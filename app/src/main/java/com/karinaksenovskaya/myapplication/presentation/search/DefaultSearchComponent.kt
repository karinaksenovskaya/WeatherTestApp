package com.karinaksenovskaya.myapplication.presentation.search

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

class DefaultSearchComponent @AssistedInject constructor(
    private val searchStoreFactory: SearchStoreFactory,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("onForecastForCityRequested") private val onForecastForCityRequested: (City) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext
) : SearchComponent, ComponentContext by componentContext {


    private val store = instanceKeeper.getStore { searchStoreFactory.create() }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    SearchStore.Label.ClickBack -> onBackClicked()
                    is SearchStore.Label.OpenForecast -> onForecastForCityRequested(it.city)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State> = store.stateFlow

    override fun changeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangeSearchQuery(query))
    }

    override fun onClickBack() {
        store.accept(SearchStore.Intent.ClickBack)
    }

    override fun onClickSearch() {
        store.accept(SearchStore.Intent.ClickSearch)
    }

    override fun onClickCity(city: City) {
        store.accept(SearchStore.Intent.ClickCity(city))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onForecastForCityRequested") onForecastForCityRequested: (City) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultSearchComponent
    }
}