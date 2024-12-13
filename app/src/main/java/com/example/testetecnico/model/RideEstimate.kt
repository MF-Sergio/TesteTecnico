package com.example.testetecnico.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RideEstimate(
    val origin: Location,
    val destination: Location,
    val distance: Double,
    val duration: String,
    val options: List<RideOption>,
    val routeResponse: MyRouteData? // Now using MyRouteData
) : Parcelable

@Parcelize
data class Location(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class RideOption(
    val id: Int,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: Review,
    val value: Double
) : Parcelable

@Parcelize
data class Review(
    val rating: Double,
    val comment: String
) : Parcelable


@Parcelize
data class MyRouteData(
    val steps: List<String>
) : Parcelable