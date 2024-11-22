package com.karinaksenovskaya.myapplication.presentation.extensions

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

fun ComponentContext.componentScope() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()).apply {
    lifecycle.doOnDestroy { cancel() }
}

fun Float.tempToFormattedString(): String = "${roundToInt()}°C"

fun Calendar.formattedShortDayOfWeek(): String {
    val format = SimpleDateFormat("EEEE", Locale.getDefault())
    return format.format(time)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}