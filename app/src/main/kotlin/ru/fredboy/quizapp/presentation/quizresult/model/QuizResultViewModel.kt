package ru.fredboy.quizapp.presentation.quizresult.model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.presentation.common.model.BaseViewModel
import ru.fredboy.quizapp.presentation.common.navigation.NavBackStackEvent
import ru.fredboy.quizapp.presentation.question.model.QuestionViewModelParams
import ru.fredboy.quizapp.presentation.question.navigation.QuestionNavKey
import java.util.concurrent.TimeUnit

class QuizResultViewModel(
    val params: QuizResultViewModelParams,
) : BaseViewModel() {

    val state: StateFlow<QuizResultState> = flow {
        emit(
            QuizResultState.Success(
                quizResultVo = QuizResultVo(
                    status = params.result,
                    title = params.title,
                    correctAnswers = params.correctAnswers,
                    totalQuestions = params.totalQuestions,
                    passingScore = params.passingScore,
                    parties = if (params.result == QuizStatus.PASSED) {
                        listOf(
                            Party(
                                speed = 0f,
                                maxSpeed = 15f,
                                damping = 0.9f,
                                angle = Angle.BOTTOM,
                                spread = Spread.ROUND,
                                colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                                emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(100),
                                position = Position.Relative(0.0, 0.0)
                                    .between(Position.Relative(1.0, 0.0)),
                            ),
                        )
                    } else {
                        emptyList()
                    },
                ),
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = QuizResultState.Loading,
    )

    fun onRetakeQuiz() {
        emitNavBackStackEvent(
            event = NavBackStackEvent.ReplaceTop(
                navKey = QuestionNavKey(
                    params = QuestionViewModelParams(
                        quizId = params.quizId,
                    ),
                ),
            ),
        )
    }
}
