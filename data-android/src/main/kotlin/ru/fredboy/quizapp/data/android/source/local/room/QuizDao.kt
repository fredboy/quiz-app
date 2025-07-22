package ru.fredboy.quizapp.data.android.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.data.android.model.QuizStatusEntity
import ru.fredboy.quizapp.data.android.model.relation.QuestionWithAnswers
import ru.fredboy.quizapp.data.android.model.relation.QuizWithQuestions

@Dao
internal interface QuizDao {

    // region Inserts

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertQuizzes(quizzes: List<QuizEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAnswers(answers: List<AnswerEntity>)

    // endregion

    // region Deletes

    @Query("DELETE FROM quizzes")
    suspend fun clearQuizzes()

    @Query("DELETE FROM questions")
    suspend fun clearQuestions()

    @Query("DELETE FROM answers")
    suspend fun clearAnswers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatus(status: QuizStatusEntity)

    // endregion

    // region Queries

    @Query("SELECT * FROM quizzes")
    suspend fun getAllQuizzes(): List<QuizEntity>

    @Query("SELECT * FROM questions WHERE id = :questionId AND quiz_id = :quizId")
    suspend fun getQuestion(questionId: Int, quizId: Int): QuestionEntity?

    @Query("SELECT * FROM answers WHERE question_id = :questionId AND quiz_id = :quizId")
    suspend fun getAnswers(questionId: Int, quizId: Int): List<AnswerEntity>

    @Transaction
    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    suspend fun getQuizWithQuestions(quizId: Int): QuizWithQuestions?

    @Transaction
    suspend fun getQuestionWithAnswers(questionId: Int, quizId: Int): QuestionWithAnswers? {
        val question = getQuestion(questionId, quizId) ?: return null
        val answers = getAnswers(questionId, quizId).takeIf { it.isNotEmpty() } ?: return null

        return QuestionWithAnswers(
            question = question,
            answers = answers,
        )
    }

    @Query("SELECT * FROM quiz_status WHERE quiz_id = :quizId")
    fun observeStatus(quizId: Int): Flow<QuizStatusEntity?>

    // endregion

    @Update
    suspend fun updateQuiz(quiz: QuizEntity)
}
