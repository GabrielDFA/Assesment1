package org.d3if3027.assesment1.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Dao
import org.d3if3027.assesment1.db.DaoBmr

class HistoriViewModelFactory(private val db: DaoBmr): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriViewModel::class.java)){
            return HistoriViewModel(db)as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}