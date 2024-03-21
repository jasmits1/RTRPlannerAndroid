package com.example.rtrplannerandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rtrplannerandroid.R
import com.example.rtrplannerandroid.data.Event
import com.example.rtrplannerandroid.data.IEventRepository
import com.example.rtrplannerandroid.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar
import javax.inject.Inject

data class DailySummaryState(
    val events: List<Event> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class DailySummaryViewModel @Inject constructor(
    private val eventRepository: IEventRepository
) : ViewModel() {
    private val today = Calendar.getInstance()
    private val _isLoading = MutableStateFlow(false)
    private val _eventsAsync = eventRepository.getEvents()
        .map { it.filter {event ->
            event.eventDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    event.eventDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        } }

    val uiState: StateFlow<DailySummaryState> = combine(
        _isLoading, _eventsAsync
    ) { isLoading, eventsAsync ->
                DailySummaryState(
                    events = eventsAsync,
                    isLoading = isLoading
                )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DailySummaryState(isLoading = true)
    )
}