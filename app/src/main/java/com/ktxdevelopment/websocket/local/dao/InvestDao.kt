package com.ktxdevelopment.websocket.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ktxdevelopment.websocket.model.local.InvestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InvestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvest(entity: InvestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllInvests(invests: List<InvestEntity>)

    @Transaction
    suspend fun refreshAll(invests: List<InvestEntity>) {
        clearDb()
        insertAllInvests(invests)
    }

    @Query("SELECT * FROM invest_table")
    fun getAllInvests(): Flow<List<InvestEntity>>

    @Query("DELETE FROM invest_table")
    suspend fun clearDb()
}