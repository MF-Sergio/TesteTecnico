package com.example.testetecnico.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testetecnico.model.Ride
import com.example.testetecnico.model.RideOption
import com.example.testetecnico.model.Driver


class RideOptionsViewModel : ViewModel() {

    private val _selectedRideOption = MutableLiveData<RideOption?>(null)
    val selectedRideOption: LiveData<RideOption?> = _selectedRideOption

    // ... other variables and functions for confirming ride, error handling, etc.


    fun setSelectedRideOption(rideOption: RideOption) {
        _selectedRideOption.value = rideOption
    }
    //The RideOptionsViewModel will hold the selected RideOption

    private val _confirmedRide = MutableLiveData<Ride?>(null)
    val confirmedRide: LiveData<Ride?> = _confirmedRide

    fun confirmRide(
        customerId: String,
        origin: String,
        destination: String,
        distance: Double,
        duration: String,
        driver: Driver,
        value: Double

    ) {
        // The logic to confirm the ride using the PATCH method and store it
        // locally would go in this function
        //After the confirmation, update the _confirmedRide LiveData
    }
}
