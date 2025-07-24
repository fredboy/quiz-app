package ru.fredboy.quizapp.presentation.quizlist.model

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizzesUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase
import ru.fredboy.quizapp.presentation.common.model.BaseViewModel
import ru.fredboy.quizapp.presentation.common.navigation.NavBackStackEvent
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams
import ru.fredboy.quizapp.presentation.quizdetails.navigation.QuizDetailsNavKey

@OptIn(ExperimentalCoroutinesApi::class)
class QuizListViewModel(
    private val getQuizListUseCase: GetQuizListUseCase,
    private val observeQuizStatusUseCase: ObserveQuizStatusUseCase,
    private val invalidateCachedQuizzesUseCase: InvalidateCachedQuizzesUseCase,
) : BaseViewModel() {

    private val reloadTrigger = MutableSharedFlow<QuizListReloadEvent>(replay = 0)

    private var lastSuccessState: QuizListState.Success? = null

    val quizListState: StateFlow<QuizListState> = reloadTrigger
        .onStart { emit(QuizListReloadEvent.Initial(lastSuccessState)) }
        .flatMapLatest { event ->
            flow {
                val state = when (event) {
                    is QuizListReloadEvent.Initial -> lastSuccessState ?: QuizListState.Loading

                    QuizListReloadEvent.Reload -> QuizListState.Loading

                    is QuizListReloadEvent.Refresh ->
                        QuizListState.Refreshing(event.backgroundState)
                }

                emit(state)

                if (event !is QuizListReloadEvent.Initial) {
                    logger.i { "Invalidating caches before reload" }
                    invalidateCachedQuizzesUseCase()
                }

                val quizzesData = getQuizListUseCase()
                val quizVoFlows = quizzesData.quizzes.map { quiz ->
                    observeQuizStatusUseCase(quiz.id).map { status ->
                        QuizVo(quiz, status)
                    }
                }

                emitAll(
                    combine(quizVoFlows) { quizArray ->
                        val success = QuizListState.Success(quizArray.toList())
                        lastSuccessState = success
                        success
                    },
                )
            }.catch { throwable ->
                logger.e(throwable) { "Error loading quiz list" }
                emit(QuizListState.Error(throwable.localizedMessage))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500L),
            initialValue = QuizListState.Loading,
        )

    fun onQuizClick(quizVo: QuizVo) {
        emitNavBackStackEvent(
            event = NavBackStackEvent.Push(
                navKey = QuizDetailsNavKey(
                    params = QuizDetailsViewModelParams(
                        quizId = quizVo.quiz.id,
                    ),
                ),
            ),
        )
    }

    fun onReload(reloadEvent: QuizListReloadEvent) {
        logger.d { "Reload triggered" }
        viewModelScope.launch {
            reloadTrigger.emit(reloadEvent)
        }
    }

    companion object {
        private val logger = Logger.Companion.withTag("QuizListViewModel")
    }
}
