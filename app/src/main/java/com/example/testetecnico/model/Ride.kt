package com.example.testetecnico.model

import java.time.LocalDateTime

data class Ride(
    val id: Int,
    val date: LocalDateTime,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)

data class Driver(
    val id: Int,
    val name: String
)