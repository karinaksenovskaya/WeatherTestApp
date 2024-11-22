package com.karinaksenovskaya.myapplication.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.karinaksenovskaya.myapplication.presentation.details.DetailsComponent
import com.karinaksenovskaya.myapplication.presentation.error.ErrorComponent
import com.karinaksenovskaya.myapplication.presentation.search.SearchComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        data class Details(val component: DetailsComponent) : Child
        data class Search(val component: SearchComponent) : Child
        data object Loading : Child
        data class Error(val component: ErrorComponent) : Child
    }
}