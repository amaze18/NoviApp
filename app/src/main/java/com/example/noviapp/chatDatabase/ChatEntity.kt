package com.example.noviapp.chatDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_messages")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val isSent: Boolean,
    val timestamp: String,
    val botName: String
)

