package com.example.noviapp.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
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

@Composable
fun ChatBubble(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isSent) Arrangement.End else Arrangement.Start
    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(
                        if (message.isSent) Color(0xFF6200EE) else Color.DarkGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(message.timestamp, color = Color.Gray, fontSize = 12.sp)
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
            .padding(8.dp)
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text("Type your message...") },
            modifier = Modifier
                .weight(1f)
                .background(Color.DarkGray, RoundedCornerShape(16.dp)),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                cursorColor = Color.White
            )
        )

        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (message.isNotBlank()) {
                    chatViewModel.sendMessage(message)
                    message = ""
                }
            },
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
        ) {
            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
        }
    }
}

@Preview
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}

class ChatViewModel : ViewModel() {
    var messages = mutableStateListOf<Message>()
        private set

    fun sendMessage(userQuery: String) {
        if (userQuery.isBlank()) return

        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        messages.add(Message(userQuery, true, timestamp))

        // ✅ Generate Request Time in Correct Format
        val dateFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH)
        val requestTime = dateFormat.format(Date())

        val request = ChatRequest(
            message = userQuery,
            email = "singewartanmay@gmail.com",
            bot_id = "delhi_mentor_male",
            previous_conversation = messages.map {
                ChatMessage(
                    if (it.isSent) "user" else "assistant",
                    it.text
                )
            },
            request_time = requestTime,
            bot_prompt = "You are my best friend!"
        )

        // ✅ Debugging: Log request payload
        Log.d("ChatViewModel", "Request Payload: ${Gson().toJson(request)}")

        viewModelScope.launch {
            RetrofitClient.instance.sendQuery(request).enqueue(object : Callback<ChatResponse> {
                override fun onResponse(
                    call: Call<ChatResponse>,
                    response: Response<ChatResponse>
                ) {
                    if (response.isSuccessful) {
                        val botReply = response.body()?.response ?: "No response"
                        messages.add(Message(botReply, false, timestamp))
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                        messages.add(Message("Error: $errorMsg", false, timestamp))
                        Log.e("ChatViewModel", "Server Error: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    messages.add(Message("Failed to reach server!", false, timestamp))
                    Log.e("ChatViewModel", "Network Error: ${t.message}")
                }
            })
        }
    }
}



object RetrofitClient {
    private const val BASE_URL = "https://walrus-app-htefl.ondigitalocean.app/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ChatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApiService::class.java)
    }
}



interface ChatApiService {
    @Headers("Content-Type: application/json")
    @POST("cv/chat")
    fun sendQuery(@Body request: ChatRequest): Call<ChatResponse>
}


data class Message(val text: String, val isSent: Boolean, val timestamp: String)
data class ChatMessage(val role: String, val content: String)

data class ChatRequest(
    val message: String,
    val email: String,
    val bot_id: String,
    val previous_conversation: List<ChatMessage>,
    val request_time: String,
    val bot_prompt: String
)

data class ChatResponse(val response: String, val message_id: Int, val reminder: Boolean)
