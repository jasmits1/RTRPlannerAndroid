package com.example.rtrplannerandroid.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.annotation.concurrent.Immutable

@Entity(
    tableName = "events"
)

@Immutable
data class Event(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "location") val location: String = "",
    @ColumnInfo(name = "event_date") val eventDate: Calendar = Calendar.getInstance()
) {
    fun getTimeString(): String {
        val time = eventDate.time
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(time)
    }

    fun getDateString(): String {
        val time = eventDate.time
        val formatter = SimpleDateFormat("dd MMM")
        return formatter.format(time)
    }
}
