package com.example.noviapp.presentation

import BackgroundBlobs
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noviapp.modal.Message
import com.example.noviapp.trialUi.AnimatedBotMessageText
import com.example.noviapp.viewModel.ChatViewModel

@Composable
fun ChatScreen(botName: String?, chatViewModel: ChatViewModel = viewModel()) {
    val currentBot = botName ?: "delhi_mentor_male"

    LaunchedEffect(currentBot) {
        chatViewModel.setCurrentBot(currentBot)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BackgroundBlobs()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Chatting with: $currentBot",
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.Black
            )
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                items(chatViewModel.messages) { message ->
                    ChatBubble(message)
                }
            }
            MessageInput(chatViewModel)
        }
    }
}




@Composable
fun ChatBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = if (message.isSent) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.75f)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.3f))
                    .border(
                        width = 2.dp,
                        color = Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(24.dp)
                    ).padding(horizontal = 5.dp, vertical = 5.dp)
                    .shadow(
                        elevation = 50.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color.Transparent,
                        spotColor = Color.Transparent
                    )
            ){
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(10.dp)
                )
                Text(
                    text = message.text.trim(),
                    color = Color.Black,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(6.dp)
                )
//                AnimatedBotMessageText(message = message.text.trim())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.timestamp,
                color = Color(0xFFFF70E1),
                fontSize = 12.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 8.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(chatViewModel: ChatViewModel) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White.copy(alpha = 0.5f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(24.dp)
                ).padding(horizontal = 5.dp, vertical = 5.dp)
                .shadow(
                    elevation = 50.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.White.copy(alpha = 0.5f),
                    spotColor = Color.White.copy(alpha = 0.5f)
                )
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Type your message...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 40.dp, max = 120.dp)
                    .verticalScroll(rememberScrollState()),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = Color.Black
                ) ,
                 textStyle = TextStyle(color = Color.Black),
                maxLines = 5
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                if (message.isNotBlank()) {
                    chatViewModel.sendMessage(message)
                    message = ""
                }
            },
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFFFF70E1), Color(0xFFFFA781))
                    )
                )
                .size(80.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = ButtonDefaults.ContentPadding
        ) {
            Text("Send", color = Color.White)
        }
    }
}

