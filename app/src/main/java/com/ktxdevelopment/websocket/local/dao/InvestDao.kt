package com.ktxdevelopment.websocket.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ktxdevelopment.websocket.model.local.InvestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(entity: InvestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMessages(messages: List<InvestEntity>)

    @Query("SELECT * FROM invest_table")
    fun getAllMessages(): Flow<List<InvestEntity>>

    @Query("DELETE FROM invest_table")
    suspend fun clearDb()
}