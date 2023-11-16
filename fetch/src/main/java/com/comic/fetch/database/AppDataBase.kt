package com.ac.autoclick.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.comic.fetch.database.Converters
import com.comic.fetch.database.entity.Comic
import com.comic.fetch.initializer.FetchInitializer

@Database(
    entities = [Comic::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        private const val DATABASE_NAME = "database"

        fun newInstance(): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    FetchInitializer.context,
                    AppDataBase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration().build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}