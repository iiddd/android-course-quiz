package com.iiddd.quiz.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import com.iiddd.quiz.R

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    buttonText: String,
    isEnabled: Boolean
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.dark_blue)
        ),
        enabled = isEnabled
    ) {
        Text(
            text = buttonText.uppercase(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}