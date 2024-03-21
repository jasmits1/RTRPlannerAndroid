package com.example.rtrplannerandroid.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rtrplannerandroid.PlannerDestinationsArgs
import com.example.rtrplannerandroid.data.IEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class EditEventState (
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val eventDate: Calendar = Calendar.getInstance(),
    val isLoading: Boolean = false
)

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val eventRepository: IEventRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskId: String? = savedStateHandle[PlannerDestinationsArgs.EVENT_ID_ARG]

    private val _uiState = MutableStateFlow(EditEventState())
    val uiState: StateFlow<EditEventState> = _uiState.asStateFlow()

    fun save() = CoroutineScope(Dispatchers.IO).launch {
        eventRepository.createEvent(
            id = taskId,
            title = uiState.value.title,
            description = uiState.value.description,
            location =  uiState.value.location,
            //eventDate = uiState.value.eventDate
            eventDate = Calendar.getInstance()
        )
    }

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun updateDescription(newDesc: String) {
        _uiState.update {
            it.copy(description = newDesc)
        }
    }

    fun updateLocation(newLoc: String) {
        _uiState.update {
            it.copy(location = newLoc)
        }
    }

    fun updateDate(newDate: Calendar) {
        _uiState.update {
            it.copy(eventDate = newDate)
        }
    }
}