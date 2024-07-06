package com.emil.network

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download")
    fun getOffersData(): Deferred<Response<OffersResponse>>

    @GET("uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    fun getTicketsData():  Deferred<Response<TicketsResponse>>

    @GET("u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download")
    fun getTicketOffersData(): Deferred<Response<TicketOffersResponse>>
}
