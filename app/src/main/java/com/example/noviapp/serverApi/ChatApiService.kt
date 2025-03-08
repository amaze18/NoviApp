package com.example.noviapp.serverApi

import com.example.noviapp.modal.ChatRequest
import com.example.noviapp.modal.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatApiService {
    @Headers("Content-Type: application/json")
    @POST("cv/chat")
    fun sendQuery(@Body request: ChatRequest): Call<ChatResponse>
}