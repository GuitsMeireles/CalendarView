package com.gmb.calendarview.calendar

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmb.calendarview.calendar.dao.EventsDao

@Database(entities = [Events::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventsDao
}
