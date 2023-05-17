package org.d3if3027.assesment1.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3027.assesment1.model.HasilBMR

class MainViewModel : ViewModel() {

    private val hasilBMR = MutableLiveData<HasilBMR?>()

    fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean) {
        val bmr: Float
        if (isMale) {
            bmr = (66 + (9.6 * weight) + (1.8 * height) - (4.7 * age)).toFloat()
        } else {
            bmr = (65 + (13.7 * weight) + (5 * height) - (6.8 * age)).toFloat()
        }
        hasilBMR.value = HasilBMR(bmr)
    }

    fun getHasilBMR(): LiveData<HasilBMR?> = hasilBMR
}