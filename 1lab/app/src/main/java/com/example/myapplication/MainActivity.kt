package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import java.util.regex.Pattern


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var message by remember { mutableStateOf("") }
            var isNameInvalid by remember { mutableStateOf(false) }

            var textForOutput: String = if (isNameInvalid) "" else message
            var textForAlert: String = textForOutput
            textForOutput = if (textForOutput != "")
                "Hello, $textForOutput!" else textForOutput;

            Column {
                Text(
                    "Enter your name:",
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Monospace
                )
                OutlinedTextField(
                    value = message,
                    textStyle = TextStyle(fontSize = 25.sp),
                    onValueChange = {
                        message = it
                        isNameInvalid = !isValidName(it)!!
                    },
                    placeholder = { Text("Your name here") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Blue,
                        unfocusedBorderColor = Color.Gray,
                        errorBorderColor = Color.Red
                    ),
                    isError = isNameInvalid
                )
                if (isNameInvalid) {
                    Text(
                        "Name is invalid",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Monospace,
                        color = Color.Red
                    )
                }
                Text(
                    textForOutput,
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Cursive
                )
                AlertButton(textForAlert)
            }
        }
    }


    @Composable
    private fun AlertButton(message: String) {
        val showDialog = remember { mutableStateOf(false) }
        val showMessage: String = if (message != "")
            "Your name is $message!" else "You didn't enter your name or your name is invalid"
        if (showDialog.value) {
            MyAlert(msg = showMessage,
                showDialog = showDialog.value,
                onDismiss = { showDialog.value = false })
        }
        Surface {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    onClick = { showDialog.value = true }
                ) {
                    Text("Show name in alert")
                }
            }
    }

    @Composable
    fun MyAlert(
        msg: String,
        showDialog: Boolean,
        onDismiss: () -> Unit
    ) {
        if (showDialog) {
            AlertDialog(
                title = {
                    Text(msg)
                },
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = onDismiss) {
                        Text("Dismiss")
                    }
                },
                dismissButton = {}
            )
        }
    }

    private fun isValidName(nameStr: String?) =
        nameStr?.let {
            Pattern
                .compile(
                    "^[a-z-]+\$",
                    Pattern.CASE_INSENSITIVE
                ).matcher(it).find()
        }
}