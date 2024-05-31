package com.namemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Name::class], version = 1)
abstract class NameRoomDatabase : RoomDatabase() {

    abstract fun nameDao(): NameDao

    companion object {
        @Volatile
        private var INSTANCE: NameRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NameRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NameRoomDatabase::class.java,
                    "name_db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(NameDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class NameDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.nameDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(nameDao: NameDao) {
            nameDao.deleteAll()
            var name = Name("Marcos")
            nameDao.insert(name)
            name = Name("Felipe")
            nameDao.insert(name)
        }
    }
}
