import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emil.network.ApiService
import com.emil.network.OffersResponse
import com.emil.network.Offer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    private val _offersData = MutableLiveData<List<Offer>>()
    val offersData: LiveData<List<Offer>> = _offersData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("MainViewModel", "Coroutine error occurred: $exception")
    }

    init {
        loadOffers()
    }

    internal fun loadOffers() {
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            try {
                val response = apiService.getOffersData().await()
                if (response.isSuccessful) {
                    _offersData.postValue(response.body()?.offers ?: emptyList())
                    Log.d("MainViewModel", "Offers loaded : ${response.body()?.offers}")
                } else {
                    Log.e("MainViewModel", "Failed to load offers: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading offers ", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

    }
}
