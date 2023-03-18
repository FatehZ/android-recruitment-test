package com.ktxdevelopment.websocket.di

import android.content.Context
import androidx.room.Room
import com.ktxdevelopment.websocket.local.db.InvestDatabase
import com.ktxdevelopment.websocket.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomHiltModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app: Context) = Room
        .databaseBuilder(app, InvestDatabase::class.java, Constants.DB_NAME)
        .build()


    @Singleton
    @Provides
    fun provideDao(db: InvestDatabase) = db.getDao()

}