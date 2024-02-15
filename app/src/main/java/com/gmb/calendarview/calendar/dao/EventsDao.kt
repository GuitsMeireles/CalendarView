package com.gmb.calendarview.calendar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmb.calendarview.calendar.Events
import kotlinx.coroutines.flow.Flow

@Dao
interface EventsDao {
    @Insert
    suspend fun insertEvents(evento: Events)

    @Query("SELECT * FROM eventos WHERE data = :data")
    fun obterEventsPorData(data: Long): Flow<List<Events>>
}
