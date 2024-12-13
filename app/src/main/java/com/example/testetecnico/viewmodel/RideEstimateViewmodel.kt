// RideEstimateViewModel.kt
package com.example.testetecnico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testetecnico.api.RideEstimateRequest
import com.example.testetecnico.api.RideService
import com.example.testetecnico.model.RideEstimate
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RideEstimateViewModel : ViewModel() {
    private val _rideEstimate = MutableLiveData<RideEstimate?>(null)
    val rideEstimate: LiveData<RideEstimate?> = _rideEstimate

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    fun estimateRide(customerId: String, origin: String, destination: String) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            val request = RideEstimateRequest(customerId, origin, destination)
            RideService.create().estimateRide(request).enqueue(object : Callback<RideEstimate> {
                override fun onResponse(call: Call<RideEstimate>, response: Response<RideEstimate>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _rideEstimate.value = response.body()
                    } else {
                        _error.value = "Error: ${response.code()} ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<RideEstimate>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = "Network Error: ${t.message}"
                }
            })
        }
    }
}
