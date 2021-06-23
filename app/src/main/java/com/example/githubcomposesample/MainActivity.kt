package com.example.githubcomposesample

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val painter = painterResource(id = R.drawable.github_mark_120_px)
            val fontFamily = FontFamily(Font(R.font.open_sans_light, FontWeight.Light))
            val userNameOrPassWordNotFoundErrorAlertVisibilityState = remember {
                mutableStateOf(false)
            }
            val successFullyLoggedInAlertVisibilityState = remember {
                mutableStateOf(false)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                Image(
                    painter = painter,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Sign in to Github",
                    fontFamily = fontFamily,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                UserNameOrPassWordNotFoundErrorAlert(
                    userNameOrPassWordNotFoundErrorAlertVisibilityState
                )
                SuccessFullyLoggedInAlert(successFullyLoggedInAlertVisibilityState)
                UserNameAndPassWordCard(
                    userNameOrPassWordNotFoundErrorAlertVisibilityState,
                    successFullyLoggedInAlertVisibilityState
                )
                Spacer(modifier = Modifier.height(10.dp))
                CreateAnAccountAlert(title = "New to Github?", subtitle = "Create an account.")

            }

        }
    }
}

@Composable
fun UserNameOrPassWordNotFoundErrorAlert(userNameOrPassWordNotFoundErrorAlertVisibilityState: MutableState<Boolean>) {
    if (userNameOrPassWordNotFoundErrorAlertVisibilityState.value) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(50.dp)
                .background(Color(0xFFFFCDD2))
                .border(BorderStroke(1.dp, Color(0xFFE57373)), shape = RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Incorrect username or password",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.close_svg),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        userNameOrPassWordNotFoundErrorAlertVisibilityState.value = false
                    }
            )


        }
    }
}


@Composable
fun SuccessFullyLoggedInAlert(successFullyLoggedInAlertVisibilityState: MutableState<Boolean>) {
    if (successFullyLoggedInAlertVisibilityState.value) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(50.dp)
                .background(Color(0xFFA5D6A7))
                .border(BorderStroke(1.dp, Color(0xFF388E3C)), shape = RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hurray. Logged in successfully",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Composable
fun CreateAnAccountAlert(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxWidth()
            .height(70.dp)
            .border(BorderStroke(1.dp, Color(0xFFE0E0E0)), shape = RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = title, fontSize = 16.sp)
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color(0xFF1565C0),
                modifier = Modifier.padding(start = 5.dp, top = 1.dp)
            )
        }

    }
}

@Composable
fun UserNameAndPassWordCard(
    userNameOrPassWordNotFoundErrorAlertVisibilityState: MutableState<Boolean>,
    successFullyLoggedInAlertVisibilityState: MutableState<Boolean>
) {

    val editTextUsernameState = remember {
        mutableStateOf("")
    }
    val editTextPasswordState = remember {
        mutableStateOf("")
    }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        border = BorderStroke(0.5.dp, Color(0xFFc7c7c7))
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFfafafa)),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(30.dp))
            LabelTextView(title = "Username or email address", labelType = LABEL_TYPE_EMAIL)
            Spacer(modifier = Modifier.height(10.dp))
            CreateUsernameField(editTextUsernameState)
            Spacer(modifier = Modifier.height(10.dp))
            LabelTextView(title = "Password", labelType = LABEL_TYPE_PASSWORD)
            Spacer(modifier = Modifier.height(10.dp))
            CreatePasswordField(editTextPasswordState)
            Spacer(modifier = Modifier.height(10.dp))
            SubmitButton(
                editTextUsernameState,
                editTextPasswordState,
                userNameOrPassWordNotFoundErrorAlertVisibilityState,
                successFullyLoggedInAlertVisibilityState
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Composable
fun CreatePasswordField(editTextPasswordState: MutableState<String>) {

    val editTextBorderState = remember {
        mutableStateOf(
            BorderStroke(
                width = 0.5.dp,
                color = Color(0xFFE0E0E0)
            )
        )
    }
    val editTextFocusRequester = FocusRequester()

    TextField(
        value = editTextPasswordState.value,
        onValueChange = {
            editTextPasswordState.value = it
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .height(50.dp)
            .focusRequester(editTextFocusRequester)
            .onFocusChanged {
                editTextBorderState.value = if (it.isFocused)
                    BorderStroke(
                        width = 2.dp,
                        color = Color(0xFF64B5F6)
                    )
                else
                    BorderStroke(
                        width = 0.5.dp,
                        color = Color(0xFFE0E0E0)
                    )

                Log.e("currentFocusState", it.isFocused.toString())
            }
            .border(
                border = editTextBorderState.value,
                shape = RoundedCornerShape(10.dp)
            )

    )

}

@Composable
fun SubmitButton(
    editTextUsernameState: MutableState<String>,
    editTextPasswordState: MutableState<String>,
    userNameOrPassWordNotFoundErrorAlertVisibilityState: MutableState<Boolean>,
    successFullyLoggedInAlertVisibilityState: MutableState<Boolean>
) {

    val submitTextState = remember {
        mutableStateOf("Sign In")
    }
    val submitBackgroundColorState = remember {
        mutableStateOf(
            Color(0xFF00C853)
        )
    }
    Button(
        onClick = {
            validateFields(
                submitBackgroundColorState,
                submitTextState,
                editTextUsernameState,
                editTextPasswordState,
                userNameOrPassWordNotFoundErrorAlertVisibilityState,
                successFullyLoggedInAlertVisibilityState

            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .height(50.dp)
            .border(
                border = BorderStroke(
                    width = 0.dp,
                    color = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            ),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = submitBackgroundColorState.value
        )
    ) {
        Text(text = submitTextState.value, color = Color.White, fontSize = 18.sp)
    }
}

fun validateFields(
    submitBackgroundColorState: MutableState<Color>,
    submitTextState: MutableState<String>,
    editTextUsernameState: MutableState<String>,
    editTextPasswordState: MutableState<String>,
    userNameOrPassWordNotFoundErrorAlertVisibilityState: MutableState<Boolean>,
    successFullyLoggedInAlertVisibilityState: MutableState<Boolean>
): Boolean {
    submitBackgroundColorState.value = Color(0xFFA5D6A7)
    submitTextState.value = "Signing In..."
    var fieldsValidated = false

    Handler().postDelayed({
        submitBackgroundColorState.value = Color(0xFF00C853)
        submitTextState.value = "Sign In"
        if (editTextPasswordState.value.isEmpty() || editTextUsernameState.value.isEmpty()) {
            userNameOrPassWordNotFoundErrorAlertVisibilityState.value = true
            successFullyLoggedInAlertVisibilityState.value = false
            Log.e("validateFields", "empty")
            fieldsValidated = false
        } else {
            Log.e("validateFields", "notEmpty")
            fieldsValidated = true
            successFullyLoggedInAlertVisibilityState.value = true
            userNameOrPassWordNotFoundErrorAlertVisibilityState.value = false
        }
    }, 2000)
    return fieldsValidated
}


@Composable
fun LabelTextView(title: String, labelType: Int) {
    if (labelType == LABEL_TYPE_PASSWORD) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp
            )
            Text(
                text = "Forgot password?",
                fontSize = 14.sp,
                color = Color(0xFF1565C0)
            )
        }
    } else {
        Text(
            text = title,
            modifier = Modifier.padding(start = 20.dp),
            fontSize = 16.sp
        )
    }

}

@Composable
fun CreateUsernameField(editTextUsernameState: MutableState<String>) {

    val editTextBorderState = remember {
        mutableStateOf(
            BorderStroke(
                width = 0.5.dp,
                color = Color(0xFFE0E0E0)
            )
        )
    }
    val editTextFocusRequester = FocusRequester()
    TextField(
        value = editTextUsernameState.value,
        onValueChange = {
            editTextUsernameState.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFFFFFFFF),
            focusedIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .height(50.dp)
            .focusRequester(editTextFocusRequester)
            .onFocusChanged {
                editTextBorderState.value = if (it.isFocused)
                    BorderStroke(
                        width = 2.dp,
                        color = Color(0xFF64B5F6)
                    )
                else
                    BorderStroke(
                        width = 0.5.dp,
                        color = Color(0xFFE0E0E0)
                    )

                Log.e("currentFocusState", it.isFocused.toString())
            }
            .border(
                border = editTextBorderState.value,
                shape = RoundedCornerShape(10.dp)
            )

    )
}



