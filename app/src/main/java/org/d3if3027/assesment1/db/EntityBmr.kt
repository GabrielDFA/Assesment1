package org.d3if3027.assesment1.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bmr")
class EntityBmr(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var berat: Float,
    var tinggi: Float,
    var usia: Int,
    var isMale: Boolean
)