package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainLayout() {
        setContent {
            var isError by remember{ mutableStateOf(false) }
            val cards = remember { mutableStateListOf<CardInfo>() }
            var message by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        OutlinedTextField(
                            value = message,
                            textStyle = TextStyle(fontSize = 15.sp),
                            onValueChange = {
                                message = it
                            },
                            placeholder = { Text("Name here") },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Blue,
                                unfocusedBorderColor = Color.Gray,
                                errorBorderColor = Color.Red
                            ),
                            isError = isError
                        )
                        if (isError) {
                            Text(
                                "Name is invalid",
                                fontSize = 15.sp,
                                fontFamily = FontFamily.Monospace,
                                color = Color.Red
                            )
                        }
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        IconButton (
                            modifier = Modifier.width(50.dp),
                            onClick = {
                                if (message.isNotEmpty()) {
                                    val cardInfo = CardInfo(UUID.randomUUID().toString(), message)
                                    cards += cardInfo
                                    isError = false
                                } else {
                                    isError = true
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                modifier = Modifier.size(30.dp),
                                contentDescription = "Close"
                            )
                        }
                        IconButton (
                            modifier = Modifier
                                .width(50.dp)
                                .align(Alignment.CenterVertically),
                            onClick = { message = "" },
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                modifier = Modifier.size(30.dp),
                                contentDescription = "Close"
                            )
                        }
                    }
                }
            CardLayout(cards)
        }
    }

    @Composable
    fun CardLayout(cardsInfo: MutableList<CardInfo>) {
        BoxWithLayout {
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth(1f)
                    .absolutePadding(
                        left = 7.dp,
                        top = 85.dp,
                        bottom = 0.dp,
                        right = 0.dp,
                    )
                    .verticalScroll(rememberScrollState()),
            ) {
                if (cardsInfo.isEmpty()) {
                    Text(
                        text = "Person list is empty.",
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(50.dp)
                    )
                } else {
                    for (cardInfo in cardsInfo) {
                        MyCard(cardInfo.name, onDelete = { cardsInfo.remove(cardInfo) })
                    }
                }
            }
        }
    }

    @Composable
    fun BoxWithLayout(content: @Composable RowScope.()->Unit){
        Row {
            content()
        }
    }

    @Composable
    fun MyCard(
        name: String,
        onDelete: () -> Unit,
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth(0.98f)
                .padding(5.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row (
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(imageVector = ImageVector.vectorResource(id = R.drawable.user_icon),
                        contentDescription = "User icon",
                        modifier = Modifier.align(Alignment.CenterVertically))
                    Text(
                        text = name,
                        modifier = Modifier
                            .absolutePadding(
                                left = 0.dp,
                                top = 20.dp,
                                bottom = 20.dp,
                                right = 0.dp,
                            ),
                        textAlign = TextAlign.Center,
                    )
                }
                IconButton (
                    modifier = Modifier
                        .width(50.dp)
                        .align(Alignment.CenterVertically),
                    onClick = onDelete,
                ) {
                    Icon(Icons.Filled.Clear, contentDescription = "Close")
                }
            }
        }
    }
}