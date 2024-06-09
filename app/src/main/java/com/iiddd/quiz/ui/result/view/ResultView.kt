package com.iiddd.quiz.ui.result.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iiddd.quiz.R

@Composable
fun ResultView(
    onClick: () -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.curve_line),
            contentDescription = "welcome background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.result_header),
                fontSize = 24.sp,
                color = Color.White
            )
            Image(
                painter = painterResource(id = R.drawable.ic_trophy),
                contentDescription = "welcome background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = stringResource(id = R.string.result_congrats_success),
                fontSize = 26.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.result_name),
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.result_score),
                fontSize = 20.sp,
                color = colorResource(id = R.color.grey),
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Button(
                onClick = {onClick},
                shape = RoundedCornerShape(8),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.white)
                ),
                modifier = Modifier.padding(horizontal = 26.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.finish_button_text),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.dark_blue),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
@Preview
fun ResultPreview() {
    MaterialTheme {
        ResultView {}
    }
}