package com.example.noviapp.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noviapp.chatDatabase.ChatDatabase
import com.example.noviapp.chatDatabase.ChatEntity
import com.example.noviapp.modal.ChatMessage
import com.example.noviapp.modal.ChatRequest
import com.example.noviapp.modal.ChatResponse
import com.example.noviapp.modal.Message
import com.example.noviapp.repository.BotConfigRepository
import com.example.noviapp.serverApi.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    var messages = mutableStateListOf<Message>()
        private set

    // New state to track whether the bot is typing
    var isBotTyping by mutableStateOf(false)
        private set

    private val chatDao = ChatDatabase.getDatabase(application).chatDao()

    var currentBotName: String = "delhi_mentor_male"
        private set

    fun setCurrentBot(botName: String) {
        currentBotName = botName
        loadChatHistory(botName)
    }

    private fun loadChatHistory(botName: String) {
        viewModelScope.launch {
            val savedMessages = chatDao.getMessagesForBot(botName)
            messages.clear()
            messages.addAll(savedMessages.map { Message(it.text, it.isSent, it.timestamp) })
        }
    }

    fun sendMessage(userQuery: String) {
        if (userQuery.isBlank()) return

        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val userMessage = Message(userQuery, true, timestamp)
        messages.add(userMessage)

        // Save user message to Room with the current bot name
        viewModelScope.launch {
            chatDao.insertMessage(
                ChatEntity(
                    text = userQuery,
                    isSent = true,
                    timestamp = timestamp,
                    botName = currentBotName
                )
            )
        }

        // Set the typing indicator on
        isBotTyping = true

        val dateFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH)
        val requestTime = dateFormat.format(Date())

        val botId = "delhi_mentor_male"
        val (selectedBotId, botPrompt) = BotConfigRepository.getBotConfig(botId)
            ?: ("default_bot" to "Default bot prompt")

        val request = ChatRequest(
            message = userQuery,
            email = "singewartanmay@gmail.com",
            bot_id = selectedBotId,
            previous_conversation = messages.map {
                ChatMessage(
                    if (it.isSent) "user" else "assistant",
                    it.text
                )
            },
            request_time = requestTime,
            bot_prompt = botPrompt
        )

        Log.d("ChatViewModel", "Request Payload: ${Gson().toJson(request)}")

        viewModelScope.launch {
            RetrofitClient.instance.sendQuery(request).enqueue(object : Callback<ChatResponse> {
                override fun onResponse(
                    call: Call<ChatResponse>,
                    response: Response<ChatResponse>
                ) {
                    // Disable typing indicator once a response is received
                    isBotTyping = false

                    if (response.isSuccessful) {
                        val botReply = response.body()?.response ?: "No response"
                        val botMessage = Message(botReply, false, timestamp)
                        messages.add(botMessage)

                        // Save bot message with the current bot name
                        viewModelScope.launch {
                            chatDao.insertMessage(
                                ChatEntity(
                                    text = botReply,
                                    isSent = false,
                                    timestamp = timestamp,
                                    botName = currentBotName
                                )
                            )
                        }
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                        val errorMessage = Message("Error: $errorMsg", false, timestamp)
                        messages.add(errorMessage)

                        // Save error message to Room with current bot name
                        viewModelScope.launch {
                            chatDao.insertMessage(
                                ChatEntity(
                                    text = "Error: $errorMsg",
                                    isSent = false,
                                    timestamp = timestamp,
                                    botName = currentBotName
                                )
                            )
                        }

                        Log.e("ChatViewModel", "Server Error: $errorMsg")
                    }
                }

                override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                    // Disable typing indicator on failure as well
                    isBotTyping = false

                    val errorMessage = "Failed to reach server!"
                    messages.add(Message(errorMessage, false, timestamp))

                    // Save network failure message to Room with current bot name
                    viewModelScope.launch {
                        chatDao.insertMessage(
                            ChatEntity(
                                text = errorMessage,
                                isSent = false,
                                timestamp = timestamp,
                                botName = currentBotName
                            )
                        )
                    }

                    Log.e("ChatViewModel", "Network Error: ${t.message}")
                }
            })
        }
    }

    // Clear messages for the current bot
    fun clearChat() {
        viewModelScope.launch {
            chatDao.clearChatHistoryForBot(currentBotName)
            messages.clear()
        }
    }
}
