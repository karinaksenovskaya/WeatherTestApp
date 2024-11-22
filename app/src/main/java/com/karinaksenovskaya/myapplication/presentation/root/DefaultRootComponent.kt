package com.karinaksenovskaya.myapplication.presentation.root

import android.annotation.SuppressLint
import android.os.Parcelable
import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.karinaksenovskaya.myapplication.data.network.api.GeoFactory
import com.karinaksenovskaya.myapplication.domain.entity.City
import com.karinaksenovskaya.myapplication.presentation.details.DefaultDetailsComponent
import com.karinaksenovskaya.myapplication.presentation.details.OpenReason
import com.karinaksenovskaya.myapplication.presentation.error.DefaultErrorComponent
import com.karinaksenovskaya.myapplication.presentation.search.DefaultSearchComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    private val errorComponentFactory: DefaultErrorComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
    @Assisted("fusedLocationClient") private val fusedLocationClient: FusedLocationProviderClient
) : RootComponent, ComponentContext by componentContext {


    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>>

    init {
        stack = childStack(
            source = navigation,
            initialConfiguration = Config.Loading,
            handleBackButton = true,
            childFactory = ::child
        )

        getLocation(fusedLocationClient)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(fusedLocationClient: FusedLocationProviderClient) {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                location?.let {
                    Log.d(
                        "MainActivity1",
                        "Location obtained: ${location.latitude}, ${location.longitude}"
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = GeoFactory.nominatimService.getReverseGeocoding(
                                location.latitude, location.longitude
                            )
                            val city = response.address.city ?: response.address.town ?: response.address.village
                            val country = response.address.country
                            Log.d("MainActivity1", "City: $city, Country: $country")

                            val testCity = City(1, city.toString(), country.toString())
                            withContext(Dispatchers.Main) {
                                navigation.replaceCurrent(
                                    Config.Details(
                                        testCity,
                                        OpenReason.CURRENT_PLACE
                                    )
                                )
                            }
                        } catch (e: Exception) {
                            Log.d("MainActivity1", "Nominatim error: ${e.message}")
                            withContext(Dispatchers.Main){
                                navigation.replaceCurrent(Config.Error("Нет подключения к интернету"))
                            }
                        }
                    }
                } ?: run {
                    Log.d("MainActivity1", "Location is null")
                    navigation.replaceCurrent(Config.Error("Местоположение не найдено"))
                }
            }.addOnFailureListener { e ->
                Log.d("MainActivity1", "Failed to get location: ${e.message}")
                navigation.replaceCurrent(Config.Error("Ошибка загрузки местоположения"))
            }
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onSearchCityClicked = {
                        navigation.push(Config.Search)
                    },
                    onBackClicked = {
                        navigation.pop()
                    },
                    openReason = config.openReason,
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }

            Config.Search -> {
                val component = searchComponentFactory.create(
                    onBackClicked = {
                        navigation.pop()
                    },
                    onForecastForCityRequested = {
                        navigation.push(Config.Details(it, OpenReason.SEARCH_PLACE))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }

            Config.Loading -> {
                RootComponent.Child.Loading
            }

            is Config.Error -> {
                val component = errorComponentFactory.create(
                    onReturnClicked = {
                        navigation.replaceCurrent(Config.Loading)
                        getLocation(fusedLocationClient = fusedLocationClient)
                    },
                    errorMsg = config.errorMsg,
                    fusedLocationClient = fusedLocationClient,
                    componentContext = componentContext
                )
                RootComponent.Child.Error(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data class Details(val city: City, val openReason: OpenReason) : Config

        @Parcelize
        data object Search : Config

        @Parcelize
        data object Loading : Config

        @Parcelize
        data class Error(val errorMsg: String) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
            @Assisted("fusedLocationClient") fusedLocationClient: FusedLocationProviderClient
        ): DefaultRootComponent
    }
}