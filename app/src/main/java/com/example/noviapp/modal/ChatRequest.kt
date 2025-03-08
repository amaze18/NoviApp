package com.example.noviapp.modal

data class ChatRequest(
    val message: String,
    val email: String,
    val bot_id: String,
    val previous_conversation: List<ChatMessage>,
    val request_time: String,
    val bot_prompt: String
)