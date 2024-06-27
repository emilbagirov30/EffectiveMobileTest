import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emil.network.ApiService
import com.emil.network.OffersResponse
import com.emil.network.Offer
import com.emil.network.TicketOffer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    private val _offersData = MutableLiveData<List<Offer>>()
    val offersData: LiveData<List<Offer>> = _offersData
    private val _ticketsOffersData = MutableLiveData<List<TicketOffer>>()
    val ticketsOffersData: LiveData<List<TicketOffer>> = _ticketsOffersData
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
    internal fun loadTicketsOffers() {
        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
            try {
                val response = apiService.getTicketOffersData().await()
                if (response.isSuccessful) {
                    _ticketsOffersData.postValue(response.body()?.ticketOffers ?: emptyList())
                    Log.d("MainViewModel", "Ticket Offers loaded: ${response.body()?.ticketOffers}")
                } else {
                    Log.e("MainViewModel", "Failed to load ticket offers: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error loading ticket offers ", e)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()

    }
}
