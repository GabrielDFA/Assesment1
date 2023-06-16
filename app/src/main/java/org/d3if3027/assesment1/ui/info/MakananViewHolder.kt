import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3027.assesment1.model.Makanan
import org.d3if3027.assesment1.network.MakananApi

class MakananViewModel : ViewModel() {
    private val _makanan = MutableLiveData<List<Makanan>>()
    val makanan: LiveData<List<Makanan>> get() = _makanan

    fun fetchMakanan() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = MakananApi.service.getMakanan()
                _makanan.postValue(response)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
