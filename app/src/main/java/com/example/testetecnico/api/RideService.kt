package com.example.testetecnico.api

import com.example.testetecnico.model.RideEstimate
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RideService {

    @POST("/ride/estimate")
    fun estimateRide(@Body request: RideEstimateRequest): Call<RideEstimate>

    @PATCH("/ride/confirm")
    fun confirmRide(@Body request: RideConfirmRequest): Call<RideConfirmResponse>

    @GET("/ride/{customer_id}")
    fun getRides(@Path("customer_id") customerId: String, @Query("driver_id") driverId: Int?): Call<RideHistoryResponse>

    companion object {
        private const val BASE_URL = "https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws"

        fun create(): RideService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RideService::class.java)
        }
    }
}

data class RideEstimateRequest(
    val customer_id: String,
    val origin: String,
    val destination: String
)

data class RideConfirmRequest(
    val customer_id: String,
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

data class RideConfirmResponse(
    val success: Boolean
)


data class RideHistoryResponse(
    val customer_id: String,
    val rides: List<Ride>
)

data class Ride(
    val id: Int,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Double,
    val duration: String,
    val driver: Driver,
    val value: Double
)