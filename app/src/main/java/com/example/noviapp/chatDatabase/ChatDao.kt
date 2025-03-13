package com.example.noviapp.chatDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(chat: ChatEntity)

    // Query to get messages only for the specified bot name
    @Query("SELECT * FROM chat_messages WHERE botName = :botName ORDER BY timestamp ASC")
    suspend fun getMessagesForBot(botName: String): List<ChatEntity>

    // Query to clear messages for a specific bot
    @Query("DELETE FROM chat_messages WHERE botName = :botName")
    suspend fun clearChatHistoryForBot(botName: String)
}
