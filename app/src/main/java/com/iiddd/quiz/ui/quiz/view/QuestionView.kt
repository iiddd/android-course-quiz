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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iiddd.quiz.R
import com.iiddd.quiz.domain.models.Answer
import com.iiddd.quiz.domain.models.Question
import com.iiddd.quiz.ui.components.PrimaryButton
import com.iiddd.quiz.ui.entity.QuestionUiState

@Composable
fun QuestionView(
    uiState: State<QuestionUiState>,
    questionsTotal: Int,
//    onAnswerSelected: (questionIndex: Int) -> Unit,
    onSubmit: (Int) -> Unit
) {
    when (uiState.value) {
        is QuestionUiState.Success -> ReadyQuestionScreen(
            uiState = uiState.value as QuestionUiState.Success,
            questionsTotal = questionsTotal,
//            onAnswerSelected = { onAnswerSelected(0) },
            onSubmit = onSubmit
        )
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun ReadyQuestionScreen(
    uiState: QuestionUiState.Success,
    questionsTotal: Int,
//    onAnswerSelected: () -> Unit,
    onSubmit: (Int) -> Unit
) {
    var answerIndex = 0
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
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
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val progress = remember(uiState.questionCounter) {
                (uiState.questionCounter / questionsTotal).toFloat()
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(8f),
                color = colorResource(id = R.color.dark_blue),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${uiState.questionCounter}/${questionsTotal}",
                modifier = Modifier.weight(1f)
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AnswerButton(
                buttonText = uiState.question.answerOptions[0].answerText,
                onOptionClick = { onAnswerSelected(0) }
            )
            AnswerButton(
                buttonText = uiState.question.answerOptions[1].answerText,
                onOptionClick = { onAnswerSelected(1) }
            )
            AnswerButton(
                buttonText = uiState.question.answerOptions[2].answerText,
                onOptionClick = {
                    answerIndex = 3
                    onAnswerSelected(2)
                }
            )
            AnswerButton(
                buttonText = uiState.question.answerOptions[3].answerText,
                onOptionClick = {
                    answerIndex = 4
                    onAnswerSelected(3)
                }
            )
            PrimaryButton(
                onClick = { onSubmit(answerIndex) },
                buttonText = stringResource(id = R.string.quiz_submit_button_text),
                isEnabled = answerIndex != 0
            )
        }
    }
}

@Composable
fun AnswerButton(
    buttonText: String,
    isSelected: Boolean = false,
    onOptionClick: () -> Unit
) {
    var buttonModifier = Modifier
    if (isSelected) {
        buttonModifier
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
    } else {

    }
    Button(
        onClick = onOptionClick,
        modifier = buttonModifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.black)
        )
    ) {
        Text(
            text = buttonText,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

private fun onAnswerSelected(index: Int) {
    answerIndex
}

@Composable
@Preview
private fun QuestionPreview() {
    MaterialTheme {
        ReadyQuestionScreen(
            uiState = QuestionUiState.Success(
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
                questionCounter = 5
            ),
            questionsTotal = 10,
//            onAnswerSelected = {},
            onSubmit = {}
        )
    }
}