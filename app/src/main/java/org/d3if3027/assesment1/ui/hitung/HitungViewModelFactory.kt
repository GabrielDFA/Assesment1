package org.d3if3027.assesment1.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3027.assesment1.db.DaoBmr
import org.d3if3027.assesment1.ui.HitungViewModel

class HitungViewModelFactory(private val db: DaoBmr) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HitungViewModel::class.java)) {
            return  HitungViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}