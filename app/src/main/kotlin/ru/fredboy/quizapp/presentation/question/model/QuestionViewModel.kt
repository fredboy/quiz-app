package ru.fredboy.quizapp.presentation.question.model

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizUseCase
import ru.fredboy.quizapp.domain.usecase.SaveQuizStatusUseCase
import ru.fredboy.quizapp.presentation.common.model.BaseViewModel
import ru.fredboy.quizapp.presentation.common.navigation.NavigationEvent

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionViewModel(
    private val getQuizUseCase: GetQuizUseCase,
    private val invalidateCachedQuizUseCase: InvalidateCachedQuizUseCase,
    private val saveQuizStatusUseCase: SaveQuizStatusUseCase,
    private val params: QuestionViewModelParams,
) : BaseViewModel() {

    private val reloadTrigger = MutableSharedFlow<QuestionReloadEvent>(replay = 0)

    private val questionIndexFlow = MutableStateFlow(0)

    private val selectedAnswerIdFlow = MutableStateFlow<Int?>(null)

    private var correctAnswers = 0

    private val quizFlow = reloadTrigger
        .onStart { emit(QuestionReloadEvent.Initial) }
        .flatMapLatest { event ->
            flow {
                emit(null)

                if (event !is QuestionReloadEvent.Initial) {
                    logger.i { "Invalidating cached quiz (id ${params.quizId}) before reload" }
                    invalidateCachedQuizUseCase(params.quizId)
                }

                emit(getQuizUseCase(params.quizId))
            }.catch { throwable -> throw throwable }
        }

    val questionState: StateFlow<QuestionState> = combine(
        flow = quizFlow,
        flow2 = questionIndexFlow,
        flow3 = selectedAnswerIdFlow,
    ) { quiz, questionIndex, selectedAnswerId ->
        quiz?.let {
            QuestionState.Success(
                questionVo = QuestionVo(
                    question = quiz.questions[questionIndex],
                    current = questionIndex + 1,
                    total = quiz.questions.size,
                    selectedAnswerId = selectedAnswerId,
                    passingScore = quiz.passingScore,
                ),
            )
        } ?: QuestionState.Loading
    }.catch { throwable ->
        logger.e(throwable) { "Error loading question" }
        emit(QuestionState.Error(throwable.localizedMessage))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = QuestionState.Loading,
    )

    fun onAnswerSelected(answerId: Int) {
        viewModelScope.launch {
            selectedAnswerIdFlow.emit(answerId)
        }
    }

    fun onNextClicked() {
        viewModelScope.launch {
            val state = questionState.value as? QuestionState.Success ?: return@launch

            val nextIndex = state.questionVo.current
            val quizQuestions = state.questionVo.total

            if (state.questionVo.selectedAnswerId == state.questionVo.question.correctAnswerId) {
                correctAnswers++
            }

            if (nextIndex >= quizQuestions) {
                saveQuizStatusUseCase(
                    quizId = params.quizId,
                    status = if (correctAnswers >= state.questionVo.passingScore) {
                        QuizStatus.PASSED
                    } else {
                        QuizStatus.FAILED
                    },
                )

                _navigationEventFlow.emit(NavigationEvent.PopBackStack)
            } else {
                questionIndexFlow.emit(nextIndex)
                selectedAnswerIdFlow.emit(null)
            }
        }
    }

    fun onReload() {
        viewModelScope.launch {
            reloadTrigger.emit(QuestionReloadEvent.Reload)
        }
    }

    companion object {
        private val logger = Logger.withTag("QuestionViewModel")
    }
}
