package com.ktxdevelopment.websocket.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ktxdevelopment.websocket.local.dao.InvestDao
import com.ktxdevelopment.websocket.model.local.InvestEntity

@Database(entities = [InvestEntity::class], version = 1, exportSchema = false)
abstract class InvestDatabase : RoomDatabase(){
    abstract fun getDao() : InvestDao
}