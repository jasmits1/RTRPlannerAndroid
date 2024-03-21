package com.example.rtrplannerandroid.di

import android.content.Context
import androidx.room.Room
import com.example.rtrplannerandroid.data.EventRepositoryImpl
import com.example.rtrplannerandroid.data.IEventRepository
import com.example.rtrplannerandroid.data.db.EventDao
import com.example.rtrplannerandroid.data.db.EventDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindEventRepository(repositoryImpl: EventRepositoryImpl): IEventRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EventDatabase::class.java,
            "Event.db"
        ).build()
    }

    @Provides
    fun provideEventDao(database: EventDatabase): EventDao = database.eventDao()
}