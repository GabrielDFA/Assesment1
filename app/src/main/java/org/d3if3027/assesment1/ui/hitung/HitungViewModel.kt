package org.d3if3027.assesment1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3027.assesment1.db.DaoBmr
import org.d3if3027.assesment1.db.DbBmr
import org.d3if3027.assesment1.db.EntityBmr
import org.d3if3027.assesment1.model.HasilBMR
import org.d3if3027.assesment1.model.calculateBMR

class HitungViewModel(private val db:DaoBmr) : ViewModel() {

    private val hasilBMR = MutableLiveData<HasilBMR?>()


    fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean) {
        val dataBmr = EntityBmr(
            berat = weight,
            tinggi = height,
            usia = age,
            isMale = isMale
        )
        hasilBMR.value = dataBmr.calculateBMR()

        viewModelScope.launch {
            withContext(Dispatchers.IO){
                db.insert(dataBmr)
            }
        }
    }

    fun getHasilBMR(): LiveData<HasilBMR?> = hasilBMR
}