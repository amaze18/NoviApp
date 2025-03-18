package com.example.noviapp.presentation

import BackgroundBlobs
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noviapp.modal.Message
import com.example.noviapp.trialUi.TypingIndicator2
import com.example.noviapp.viewModel.ChatViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import botDetails
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewChatScreenWithDrawer() {
    ChatScreenWithDrawer(
        botName = "demo_bot",
        traits = "Bold, Funny",
        language = "English"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenWithDrawer(
    botName: String?,
    traits: String?,
    language: String?
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBotName = botName ?: "Yash Oberoi"
    val testimonial = botDetails.find { it.name == currentBotName } ?: botDetails.first()

    ModalNavigationDrawer(
        drawerContent = {
            SideBarContent(
                botName = testimonial.name,
                location = testimonial.location,
                persona = testimonial.persona,
                gender = testimonial.gender,
                aboutText = testimonial.quote,
                imageResId = testimonial.imageResId,
                onEditClick = {
                    // Handle Edit click
                },
                onAddDiaryClick = {
                    // Handle Add Diary click
                },
                onClearChatClick = {
                    // Handle Clear Chat click
                }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isOpen) drawerState.close()
                                else drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                ChatScreen(
                    botName = botName,
                    traits = traits,
                    language = language
                )
            }
        }
    }
}

@Composable
fun ChatScreen(
    botName: String?,
    traits: String?,
    language: String?,
    chatViewModel: ChatViewModel = viewModel()
) {
    val currentBot = botName ?: "delhi_mentor_male"
    val selectedTraits = traits ?: ""
    val selectedLanguage = language ?: "English"

    LaunchedEffect(currentBot) {
        chatViewModel.setCurrentBot(currentBot)
    }

    val isBotTyping by remember { derivedStateOf { chatViewModel.isBotTyping } }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundBlobs()
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                modifier = Modifier.weight(1f)
                    .padding(vertical = 4.dp),
                reverseLayout = false
            ) {
                items(chatViewModel.messages) { message ->
                    ChatBubble(message)
                }
                if (isBotTyping) {
                    item {
                        TypingIndicator2()
                    }
                }

            }
            MessageInput(chatViewModel)
            Spacer(modifier = Modifier.height(2.dp))
            Box( modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Novi Ai can still make mistakes its constantly learning from you, please be kind !!" ,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    // Define colors based on whether the message is sent by the user.
    val bubbleColor = if (message.isSent) Color(0xFFC084FC) else Color.White.copy(alpha = 0.3f)
    val borderColor = if (message.isSent) Color(0xFFC084FC) else Color.White.copy(alpha = 0.5f)
    val textColor = if (message.isSent) Color.White else Color.Black

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
                    .clip(RoundedCornerShape(12.dp))
                    .background(bubbleColor)
                    .border(
                        width = 2.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(horizontal = 5.dp, vertical = 5.dp)
                    .shadow(
                        elevation = 50.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color.Transparent,
                        spotColor = Color.Transparent
                    )
            ) {
                // Optional blurred background layer
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .blur(10.dp)
                )
                Text(
                    text = message.text.trim(),
                    color = textColor,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(6.dp)
                )
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
                )
                .padding(horizontal = 5.dp, vertical = 5.dp)
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
                ),
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
            shape = RoundedCornerShape(25.dp),
            // Use transparent container color so our custom gradient shows
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            // Control internal spacing for text/icon
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFF70E1), Color(0xFFFFA781))
                    ),
                    shape = RoundedCornerShape(25.dp)
                )
                .padding(horizontal = 5.dp)
        ) {
            Text(text = "Send", color = Color.White)
        }

    }
}

@Composable
fun SideBarContent(
    botName: String,
    location: String,
    persona: String,
    gender: String,
    aboutText: String,
    imageResId: Int = android.R.drawable.sym_def_app_icon,
    onEditClick: () -> Unit,
    onAddDiaryClick: () -> Unit,
    onClearChatClick: () -> Unit
) {
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF8F8F8),
            Color(0xFFF8F7F7)
        )
    )
    val editButtonGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFF8EC7), Color(0xFFFF70E1))
    )
    val bottomButtonGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFF70E1), Color(0xFFFFA781))
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .widthIn(min = 250.dp, max = 300.dp)
            .background(brush = backgroundGradient)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
                    painter = painterResource(id = imageResId),
            contentDescription = "Bot image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = botName,
            style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
        )
        Text(
            text = location,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        Text(
            text = "Persona: $persona",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        Text(
            text = "Gender: $gender",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        GradientButton(
            text = "Edit",
            gradient = editButtonGradient,
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            },
            onClick = onEditClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = aboutText,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        GradientButton(
            text = "Add Diary",
            gradient = bottomButtonGradient,
            onClick = onAddDiaryClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        GradientButton(
            text = "Clear Chat",
            gradient = bottomButtonGradient,
            onClick = onClearChatClick
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    gradient: Brush,
    icon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(24.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(brush = gradient, shape = RoundedCornerShape(24.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            icon?.let {
                it()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text = text, color = Color.White)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen(botName = "demo_bot", traits = "Bold, Funny", language = "English")
}
