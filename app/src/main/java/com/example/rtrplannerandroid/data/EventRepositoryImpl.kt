package com.example.rtrplannerandroid.data

import com.example.rtrplannerandroid.data.db.EventDao
import com.example.rtrplannerandroid.di.ApplicationScope
import com.example.rtrplannerandroid.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepositoryImpl @Inject constructor(
    private val localDataSource: EventDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : IEventRepository {
    override fun getEvents(): Flow<List<Event>> {
        return localDataSource.getAllEvents()
    }

    override fun getEventByUri(eventId: String): Flow<Event?> {
        return localDataSource.getEventByURI(eventId)
    }

    override suspend fun createEvent(
        id: String?,
        title: String,
        description: String,
        location: String,
        eventDate: Calendar
    ) {
        localDataSource.upsert(Event(
            id = (id?: UUID.randomUUID().toString()),
            title = title,
            description = description,
            location = location,
            eventDate = eventDate
        ))
    }
}