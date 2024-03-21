package com.example.rtrplannerandroid.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
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
    val isLoading: Boolean = false,
    val isSaved: Boolean = false
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
            eventDate = uiState.value.eventDate
        )
        _uiState.update {
            it.copy(isSaved = true)
        }
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

    fun updateDate(newYear: Int, newMonth: Int, newDay: Int) {
        _uiState.update {
            //it.copy(year = newYear, month = newMonth, day = newDay)
            val cal = Calendar.getInstance()
            cal.set(newYear, newMonth, newDay, uiState.value.eventDate.get(Calendar.HOUR_OF_DAY), uiState.value.eventDate.get(Calendar.MINUTE))
            it.copy(eventDate = cal)
        }
    }

    fun updateTime(newHour: Int, newMinute: Int) {
        _uiState.update {
            val cal = Calendar.getInstance()
            cal.set(
                uiState.value.eventDate.get(Calendar.YEAR),
                uiState.value.eventDate.get(Calendar.MONTH),
                uiState.value.eventDate.get(Calendar.DAY_OF_MONTH),
                newHour,
                newMinute
            )
            it.copy(eventDate = cal)
        }
    }
}