package ru.fredboy.quizapp.presentation.quizdetails.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase

@OptIn(ExperimentalCoroutinesApi::class)
class QuizDetailsViewModel(
    private val getQuizUseCase: GetQuizUseCase,
    private val observeQuizStatusUseCase: ObserveQuizStatusUseCase,
    private val invalidateCachedQuizUseCase: InvalidateCachedQuizUseCase,
    private val params: QuizDetailsViewModelParams,
) : ViewModel() {

    private val reloadTrigger = MutableSharedFlow<QuizDetailsReloadEvent>(replay = 0)

    val quizDetailsState: StateFlow<QuizDetailsState> = reloadTrigger
        .onStart { emit(QuizDetailsReloadEvent.Initial) }
        .flatMapLatest { event ->
            flow {
                val state = when (event) {
                    QuizDetailsReloadEvent.Initial, QuizDetailsReloadEvent.Reload ->
                        QuizDetailsState.Loading

                    is QuizDetailsReloadEvent.Refresh ->
                        QuizDetailsState.Refreshing(event.backgroundState)
                }

                emit(state)

                if (event !is QuizDetailsReloadEvent.Initial) {
                    logger.i { "Invalidating cached quiz (id ${params.quizId}) before reload" }
                    invalidateCachedQuizUseCase(params.quizId)
                }

                val quizDetails = getQuizUseCase(params.quizId)

                val successFlow = observeQuizStatusUseCase(params.quizId)
                    .map { status -> QuizDetailsVo(quizDetails, status) }
                    .map { quizDetailsVo ->
                        QuizDetailsState.Success(quizDetailsVo) as QuizDetailsState
                    }

                emitAll(successFlow)
            }.catch { throwable ->
                logger.e(throwable) { "Error loading quiz details" }
                emit(QuizDetailsState.Error(throwable.localizedMessage))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = QuizDetailsState.Loading,
        )

    fun onReload(reloadEvent: QuizDetailsReloadEvent) {
        logger.d { "Reload triggered" }
        viewModelScope.launch {
            reloadTrigger.emit(reloadEvent)
        }
    }

    companion object {
        private val logger = Logger.withTag("QuizDetailsViewModel")
    }
}
