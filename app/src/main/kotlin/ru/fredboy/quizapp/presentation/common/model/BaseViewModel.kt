package ru.fredboy.quizapp.presentation.common.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.presentation.common.navigation.NavBackStackEvent

abstract class BaseViewModel : ViewModel() {

    private val _navBackStackEventFlow = MutableSharedFlow<NavBackStackEvent>()

    val navBackStackEventFlow: SharedFlow<NavBackStackEvent> = _navBackStackEventFlow

    fun emitNavBackStackEvent(event: NavBackStackEvent) {
        viewModelScope.launch {
            _navBackStackEventFlow.emit(event)
        }
    }
}
