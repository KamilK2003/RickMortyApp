package com.kamilk2003.rickmortyapp.services.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kamilk2003.rickmortyapp.objects.models.room.RoomCharacter

@Database(
    entities = [RoomCharacter::class],
    version = 1
)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun careDao(): AppDao
    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppRoomDatabase::class.java,
                            "app_database"
                        )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}