package com.emil.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/ad9a46ba-276c-4a81-88a6-c068e51cce3a")
    fun getOffersData(): Deferred<Response<OffersResponse>>

    @GET("path/to/tickets/endpoint")
    fun getTicketsData(): Call<TicketsResponse>

    @GET("path/to/ticket_offers/endpoint")
    fun getTicketOffersData(): Call<TicketOffersResponse>
}
