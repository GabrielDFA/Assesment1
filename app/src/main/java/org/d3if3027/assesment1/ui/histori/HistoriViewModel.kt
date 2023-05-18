package org.d3if3027.assesment1.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3027.assesment1.db.DaoBmr

class HistoriViewModel(private  val db: DaoBmr): ViewModel() {
    val data = db.getLastBmr()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO){
            db.clearData()
        }
    }
}