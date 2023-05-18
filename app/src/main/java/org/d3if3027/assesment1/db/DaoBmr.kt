package org.d3if3027.assesment1.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoBmr {

    @Insert
    fun insert(bmr: EntityBmr)

    @Query("SELECT * FROM bmr ORDER BY id DESC")
    fun getLastBmr(): LiveData<List<EntityBmr>>

    @Query("DELETE FROM bmr")
    fun clearData()
}