package com.karinaksenovskaya.myapplication.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.karinaksenovskaya.myapplication.presentation.details.DetailsContent
import com.karinaksenovskaya.myapplication.presentation.error.ErrorContent
import com.karinaksenovskaya.myapplication.presentation.search.SearchContent
import com.karinaksenovskaya.myapplication.presentation.stubs.LoadingContent
import com.karinaksenovskaya.myapplication.ui.theme.MyApplicationTheme

@Composable
fun RootContent(component: RootComponent) {
    MyApplicationTheme {
        Children(stack = component.stack) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> {
                    DetailsContent(instance.component)
                }

                is RootComponent.Child.Search -> {
                    SearchContent(instance.component)
                }

                RootComponent.Child.Loading -> {
                    LoadingContent()
                }

                is RootComponent.Child.Error -> {
                    ErrorContent(instance.component)
                }
            }
        }
    }
}