package org.d3if3027.assesment1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityBmr::class], version = 1, exportSchema = false)
abstract class DbBmr : RoomDatabase() {
    abstract val dao: DaoBmr
    companion object {
        @Volatile
        private var INSTANCE: DbBmr? = null
        fun getInstance(context: Context): DbBmr {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DbBmr::class.java,
                        "bmi.db"
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