package ru.fredboy.quizapp.presentation.common.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import ru.fredboy.quizapp.presentation.common.navigation.NavigationEvent

abstract class BaseViewModel : ViewModel() {

    protected val _navigationEventFlow = MutableSharedFlow<NavigationEvent>()

    val navigationEventFlow: SharedFlow<NavigationEvent> = _navigationEventFlow
}
