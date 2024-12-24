package com.iiddd.quiz.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.iiddd.quiz.R

@Composable
fun AnswerButton(
    buttonText: String,
    state: AnswerButtonState, // Use the enum to define the state
    onOptionClick: () -> Unit,
) {
    val backgroundColor = when (state) {
        AnswerButtonState.NEUTRAL -> colorResource(id = R.color.white)
        AnswerButtonState.SELECTED -> colorResource(id = R.color.white)
        AnswerButtonState.CORRECT -> colorResource(id = R.color.green)
        AnswerButtonState.INCORRECT -> colorResource(id = R.color.red)
    }

    val borderColor = when (state) {
        AnswerButtonState.SELECTED -> colorResource(id = R.color.dark_blue)
        else -> colorResource(id = R.color.grey)
    }

    OutlinedButton(
        modifier = Modifier.height(60.dp),
        onClick = { onOptionClick() },
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 3.dp
        ),
        border = BorderStroke(1.dp, color = borderColor),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
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

enum class AnswerButtonState {
    NEUTRAL,
    CORRECT,
    INCORRECT,
    SELECTED
}

@Composable
@PreviewLightDark
fun AnswerButtonCorrectPreview() {
    MaterialTheme {
        AnswerButton(
            buttonText = "Test",
            state = AnswerButtonState.CORRECT,
        ) { }
    }
}

@Composable
@PreviewLightDark
fun AnswerButtonNeutralPreview() {
    MaterialTheme {
        AnswerButton(
            buttonText = "Test",
            state = AnswerButtonState.NEUTRAL,
        ) { }
    }
}

@Composable
@PreviewLightDark
fun AnswerButtonSelectedPreview() {
    MaterialTheme {
        AnswerButton(
            buttonText = "Test",
            state = AnswerButtonState.SELECTED,
        ) { }
    }
}

@Composable
@PreviewLightDark
fun AnswerButtonIncorrectPreview() {
    MaterialTheme {
        AnswerButton(
            buttonText = "Test",
            state = AnswerButtonState.INCORRECT,
        ) { }
    }
}