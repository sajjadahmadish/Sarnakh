package project.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import project.data.local.db.dao.MarkerDao
import project.data.model.*

@Database(entities = [Marker::class], version = 18, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun markerDao(): MarkerDao


}
