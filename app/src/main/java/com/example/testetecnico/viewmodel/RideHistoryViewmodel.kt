package com.example.testetecnico.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testetecnico.model.Ride

class RideHistoryViewModel : ViewModel() {
    private val _rides = MutableLiveData<List<Ride>>(emptyList())
    val rides: LiveData<List<Ride>> = _rides

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    // ... other functions to fetch ride history, filter by driver, etc.

    fun fetchRideHistory(customerId: String, driverId: Int?) {
        // The logic to fetch ride history using the given parameters would go here
    }
}
