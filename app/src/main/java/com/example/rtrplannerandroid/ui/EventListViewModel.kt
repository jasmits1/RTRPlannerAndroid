package com.example.rtrplannerandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rtrplannerandroid.R
import com.example.rtrplannerandroid.data.Event
import com.example.rtrplannerandroid.data.IEventRepository
import com.example.rtrplannerandroid.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class EventListState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false
)
@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventRepository: IEventRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _eventsAsync = eventRepository.getEvents()
        .map { handleAsync(it) }
        .catch { emit(Async.Error(R.string.async_error)) }

    val uiState: StateFlow<EventListState> = combine(
        _isLoading, _eventsAsync
    ) { isLoading, eventsAsync ->
        when (eventsAsync) {
            Async.Loading -> {
                EventListState(isLoading = true)
            }
            is Async.Error -> {
                EventListState(

                )
            }
            is Async.Success -> {
                EventListState(
                    events = eventsAsync.data,
                    isLoading = false
                )
            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = EventListState(isLoading = true)
    )

    fun addTestEvent() = CoroutineScope(Dispatchers.IO).launch {
        eventRepository.createEvent(
            "Test Title",
            "Test Description",
            "test Location",
            Calendar.getInstance()
        )
    }

    private fun handleAsync(events: List<Event>?): Async<List<Event>> {
        if (events == null) {
            return Async.Error(R.string.async_error)
        }
        return Async.Success(events)
    }

}