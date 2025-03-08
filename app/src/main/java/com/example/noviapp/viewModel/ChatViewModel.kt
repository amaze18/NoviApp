package com.example.noviapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class ChatViewModel : ViewModel() {
    var messages = mutableStateListOf<Message>()
        private set

    fun sendMessage(userQuery: String) {
        if (userQuery.isBlank()) return

        val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        messages.add(Message(userQuery, true, timestamp))

        val dateFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH)
        val requestTime = dateFormat.format(Date())

        val botId = "delhi_mentor_male"
        val (selectedBotId, botPrompt) = BotConfigRepository.getBotConfig(botId) ?: ("default_bot" to "Default bot prompt")

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