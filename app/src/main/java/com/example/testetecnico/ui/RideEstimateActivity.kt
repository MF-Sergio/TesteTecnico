package com.example.testetecnico.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testetecnico.R
import com.example.testetecnico.model.RideEstimate
import com.example.testetecnico.viewmodel.RideEstimateViewModel

class RideEstimateActivity : AppCompatActivity() {

    private val viewModel: RideEstimateViewModel by viewModels()
    private lateinit var customerIdEditText: EditText
    private lateinit var originEditText: EditText
    private lateinit var destinationEditText: EditText
    private lateinit var estimateButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_estimate)

        // Initialize UI components
        customerIdEditText = findViewById(R.id.customer_id_edittext)
        originEditText = findViewById(R.id.origin_edittext)
        destinationEditText = findViewById(R.id.destination_edittext)
        estimateButton = findViewById(R.id.estimate_button)
        progressBar = findViewById(R.id.progress_bar)

        // Set up button click listener
        estimateButton.setOnClickListener {
            val customerId = customerIdEditText.text.toString().trim()
            val origin = originEditText.text.toString().trim()
            val destination = destinationEditText.text.toString().trim()

            if (validateInput(customerId, origin, destination)) {
                viewModel.estimateRide(customerId, origin, destination)
            }
        }

        // Observe ViewModel states
        observeViewModel()
    }

    private fun validateInput(customerId: String, origin: String, destination: String): Boolean {
        if (customerId.isEmpty() || origin.isEmpty() || destination.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun observeViewModel() {
        // Observe loading state
        viewModel.isLoading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        // Observe errors
        viewModel.error.observe(this, Observer { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Observe ride estimate result
        viewModel.rideEstimate.observe(this, Observer { rideEstimate ->
            rideEstimate?.let {
                navigateToRideOptions(it)
            }
        })
    }

    private fun navigateToRideOptions(rideEstimate: RideEstimate) {
        val intent = Intent(this, RideOptionsActivity::class.java).apply {
            putExtra("rideEstimate", rideEstimate)
        }
        startActivity(intent)
    }
}