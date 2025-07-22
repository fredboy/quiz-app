package ru.fredboy.quizapp.view.quizlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase
import ru.fredboy.quizapp.domain.usecase.SaveQuizStatusUseCase
import ru.fredboy.quizapp.view.quizlist.model.QuizVo

class QuizListViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getQuizListUseCase: GetQuizListUseCase,
    private val saveQuizStatusUseCase: SaveQuizStatusUseCase,
    private val observeQuizStatusUseCase: ObserveQuizStatusUseCase,
) : ViewModel() {

    val quizzes = flow { emit(getQuizListUseCase()) }
        .flatMapLatest { quizzes ->
            quizzes.quizzes.map { quiz ->
                observeQuizStatusUseCase.invoke(quiz.id).map { status ->
                    QuizVo(quiz, status ?: QuizStatus.UNTAKEN)
                }
            }.let { flows -> combine(flows) { it.toList() } }
        }
        .map { QuizListState.Success(it) as QuizListState }
        .catch { emit(QuizListState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuizListState.Loading
        )

    fun onQuizClick(quizVo: QuizVo) {
        viewModelScope.launch {
            saveQuizStatusUseCase(
                quizId = quizVo.quiz.id,
                status = QuizStatus.entries[(quizVo.status.ordinal + 1) % QuizStatus.entries.size]
            )
        }
    }


}
