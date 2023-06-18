package org.d3if3027.assesment1.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3027.assesment1.db.DaoBmr
import org.d3if3027.assesment1.db.DbBmr
import org.d3if3027.assesment1.db.EntityBmr
import org.d3if3027.assesment1.model.HasilBMR
import org.d3if3027.assesment1.model.calculateBMR
import org.d3if3027.assesment1.network.ApiStatus
import org.d3if3027.assesment1.network.MakananApi
import org.d3if3027.assesment1.network.UpdateWorker
import java.util.concurrent.TimeUnit

class HitungViewModel(private val db:DaoBmr) : ViewModel() {

    private val hasilBMR = MutableLiveData<HasilBMR?>()
    private val status = MutableLiveData<ApiStatus?>()

    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                val hasil = MakananApi.service.getMakanan()
                Log.d("HitungViewModel", "Success: $hasil")
                status.postValue(ApiStatus.SUCCESS)
            }catch (e: Exception){
                Log.d("HitungViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }


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
    fun getStatus(): LiveData<ApiStatus?> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}