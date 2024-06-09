package com.iiddd.quiz.ui.question.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iiddd.quiz.R

@Preview
@Composable
fun QuestionView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.question),
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.flag_id),
                contentDescription = "welcome background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(color = colorResource(id = R.color.dark_blue))
                    .border(2.dp, colorResource(id = R.color.dark_blue))
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Text")
            Text(text = "1/10")
        }
        Column(modifier = Modifier.padding(horizontal = 26.dp, vertical = 16.dp)) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dark_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}