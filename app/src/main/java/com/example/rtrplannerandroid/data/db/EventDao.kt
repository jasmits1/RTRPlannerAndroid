package com.example.rtrplannerandroid.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.rtrplannerandroid.data.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :eventId")
    fun getEventByURI(eventId: String): Flow<Event>

    @Upsert
    fun upsert(event: Event)
}