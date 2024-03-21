package com.example.rtrplannerandroid.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface IEventRepository {

    fun getEvents(): Flow<List<Event>>

    fun getEventByUri(eventId: String): Flow<Event?>

    suspend fun createEvent(title: String, description: String, location: String, eventDate: Calendar)

}