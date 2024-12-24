package com.iiddd.quiz.ui.quiz.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iiddd.quiz.R
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.ui.components.AnswerButton
import com.iiddd.quiz.ui.components.AnswerButtonState
import com.iiddd.quiz.ui.components.PrimaryButton
import com.iiddd.quiz.ui.quiz.QuizUiState

@Composable
fun QuestionView(
    uiState: State<QuizUiState>,
    questionsTotal: Int,
    onCompletion: () -> Unit,
    onSubmit: (Int) -> Unit
) {
    when (uiState.value) {
        is QuizUiState.Success -> ReadyQuestionScreen(
            uiState = uiState.value as QuizUiState.Success,
            questionsTotal = questionsTotal,
            onSubmit = onSubmit,
        )

        is QuizUiState.Complete -> {
            onCompletion()
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun ReadyQuestionScreen(
    uiState: QuizUiState.Success,
    questionsTotal: Int,
    onSubmit: (Int) -> Unit
) {
    var answerIndex by remember { mutableIntStateOf(-1) }
    var isSubmitted by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.question) {
        answerIndex = -1
        isSubmitted = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .weight(0.3f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = uiState.question.questionText,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            val context = LocalContext.current
            val drawableId = remember(uiState.question.imagePrefix) {
                context.resources.getIdentifier(
                    "flag_${uiState.question.imagePrefix}",
                    "drawable",
                    context.packageName
                )
            }

            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "welcome background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.dark_blue))
                    .border(2.dp, colorResource(id = R.color.dark_blue))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.02f)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val progress = (uiState.questionCounter.toFloat() / questionsTotal)
                .coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(8f),
                color = colorResource(id = R.color.green),
                trackColor = colorResource(id = R.color.grey),
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "${uiState.questionCounter}/${questionsTotal}",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1.3f)
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 16.dp)
                .weight(0.4f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            uiState.question.answerOptions.forEachIndexed { index, answer ->
                val buttonState = when {
                    isSubmitted && answer.isCorrect -> AnswerButtonState.CORRECT
                    isSubmitted && answerIndex == index && !answer.isCorrect -> AnswerButtonState.INCORRECT
                    answerIndex == index -> AnswerButtonState.SELECTED
                    else -> AnswerButtonState.NEUTRAL
                }
                AnswerButton(
                    buttonText = answer.answerText,
                    state = buttonState,
                    onOptionClick = {
                        answerIndex = index
                    }
                )
            }
            PrimaryButton(
                modifier = Modifier.height(60.dp),
                onClick = {
                    isSubmitted = true
                    onSubmit(answerIndex)
                },
                buttonText = stringResource(id = R.string.quiz_submit_button_text),
                isEnabled = answerIndex != -1
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun QuestionPreview() {
    MaterialTheme {
        ReadyQuestionScreen(
            uiState = QuizUiState.Success(
                question = Question(
                    id = 0,
                    questionText = "What country does this flag belong to?",
                    imagePrefix = "pl",
                    answerOptions = mutableListOf(
                        Answer(
                            index = 0,
                            answerText = "Algeria",
                            isCorrect = false
                        ),
                        Answer(
                            index = 0,
                            answerText = "Andorra",
                            isCorrect = false
                        ),
                        Answer(
                            index = 0,
                            answerText = "Angola",
                            isCorrect = false
                        ),
                        Answer(
                            index = 0,
                            answerText = "Antigua and Barbuda",
                            isCorrect = false
                        )
                    )
                ),
                questionCounter = 10
            ),
            questionsTotal = 10,
            onSubmit = {}
        )
    }
}