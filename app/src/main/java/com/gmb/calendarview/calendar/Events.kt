package com.gmb.calendarview.calendar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class Events(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val data: Long // Armazene a data como timestamp
)
