package com.emil.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("v3/4f5145bd-e5cc-4c98-ae10-28f1c0beb459")
    fun getOffersData(): Deferred<Response<OffersResponse>>

    @GET("v3/f139cc65-62af-4952-acc7-0f2dcfb74117")
    fun getTicketsData():  Deferred<Response<TicketsResponse>>

    @GET("v3/9a59dd40-f7ae-4f09-a6ab-49dc2fe56695")
    fun getTicketOffersData(): Deferred<Response<TicketOffersResponse>>
}
