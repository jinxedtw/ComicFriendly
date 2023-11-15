//package com.ac.autoclick.database
//
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.comic.app.ComicFriendlyApplication
//import com.comic.app.database.Converters
//
//@Database(
//    entities = [],
//    version = 1,
//    exportSchema = false
//)
//@TypeConverters(Converters::class)
//abstract class AppDataBase : RoomDatabase() {
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDataBase? = null
//
//        private const val DATABASE_NAME = "database"
//
//        fun newInstance(): AppDataBase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    ComicFriendlyApplication.context,
//                    AppDataBase::class.java,
//                    DATABASE_NAME
//                )
//                    .fallbackToDestructiveMigration().build().also {
//                        INSTANCE = it
//                    }
//            }
//        }
//    }
//}