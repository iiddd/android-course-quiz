package com.iiddd.quiz.ui.welcome.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iiddd.quiz.R
import com.iiddd.quiz.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeView(
    username: String,
    onSaveUserName: (String) -> Unit,
    onStartClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.curve_line),
            contentDescription = "welcome background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.bg_header_text),
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.card_header_text),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(id = R.string.card_subheader_text),
                        modifier = Modifier.padding(8.dp)
                    )
                    val focusManager = LocalFocusManager.current
                    var input by remember {
                        mutableStateOf(username)
                    }
                    OutlinedTextField(
                        value = input,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id = R.color.dark_blue)
                        ),
                        onValueChange = {
                            input = it
                        },
                        trailingIcon = {
                            if (input.isNotEmpty()) {
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .offset(x = 10.dp)
                                        .clickable {
                                            input = ""
                                        }
                                        .padding(end = 16.dp)
                                )
                            }
                        },
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onSaveUserName(input)
                                focusManager.clearFocus()
                            }),
                        shape = RoundedCornerShape(8),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )
                    Text(
                        text = stringResource(id = R.string.landing_input_helper_text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textAlign = TextAlign.Start
                    )
                    PrimaryButton(
                        onClick = onStartClick,
                        buttonText = stringResource(id = R.string.start_button_text),
                        isEnabled = true
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun WelcomePreview() {
    MaterialTheme {
        WelcomeView(
            username = "",
            onSaveUserName = {},
            onStartClick = {},
        )
    }
}