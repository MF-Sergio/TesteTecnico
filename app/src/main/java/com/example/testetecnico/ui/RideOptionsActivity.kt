package com.example.testetecnico.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testetecnico.R
import com.example.testetecnico.model.RideEstimate
import com.example.testetecnico.model.RideOption
import com.example.testetecnico.viewmodel.RideOptionsViewModel

class RideOptionsActivity : AppCompatActivity() {

    private lateinit var viewModel: RideOptionsViewModel
    private lateinit var optionsRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_options)

        // Inicia os componentes visuais
        optionsRecyclerView = findViewById(R.id.optionsListView)
        progressBar = findViewById(R.id.progressBar)

        // Configura o RecyclerView
        optionsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Cria/obtém o ViewModel
        viewModel = ViewModelProvider(this).get(RideOptionsViewModel::class.java)

        // Obter o objeto RideEstimate passado pela intent
        val rideEstimate: RideEstimate? = intent.getParcelableExtra("rideEstimate")
        if (rideEstimate != null) {
            setupUI(rideEstimate) // Configura a interface com os dados recebidos
        } else {
            Toast.makeText(this, "Erro ao carregar estimativa", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Observa alterações no ViewModel
        observeViewModel()
    }

    private fun setupUI(rideEstimate: RideEstimate) {
        // Configura o RecyclerView com a lista de motoristas
        val adapter = RideOptionsAdapter(rideEstimate.options) { selectedOption ->
            confirmRide(selectedOption, rideEstimate)
        }
        optionsRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        // Observa status de carregamento
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observa a confirmação da viagem
        viewModel.confirmedRide.observe(this) { ride ->
            if (ride != null) {
                startActivity(Intent(this, RideHistoryActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Erro ao confirmar viagem", Toast.LENGTH_SHORT).show()
            }
        }

        // Observa erros
        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun confirmRide(selectedOption: RideOption, estimate: RideEstimate) {
        // Confirmar a viagem no ViewModel
        viewModel.confirmRide(
            estimate.origin.toString(),
            estimate.destination.toString(),
            estimate.distance,
            estimate.duration,
            selectedOption
        )
    }
}