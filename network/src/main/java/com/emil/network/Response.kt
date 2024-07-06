package com.emil.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class OffersResponse(
    @Json(name = "offers") val offers: List<Offer>
)

data class Offer(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "town") val town: String,
    @Json(name = "price") val price: Price
)

data class Price(
    @Json(name = "value") val value: Int
)

data class TicketsResponse(
    @Json(name = "tickets") val tickets: List<Ticket>
)

data class Ticket(
    @Json(name = "id") val id: Int,
    @Json(name = "badge") val badge: String?,
    @Json(name = "price") val price: Price,
    @Json(name = "provider_name") val providerName: String,
    @Json(name = "company") val company: String,
    @Json(name = "departure") val departure: Departure,
    @Json(name = "arrival") val arrival: Arrival,
    @Json(name = "has_transfer") val hasTransfer: Boolean,
    @Json(name = "has_visa_transfer") val hasVisaTransfer: Boolean,
    @Json(name = "luggage") val luggage: Luggage,
    @Json(name = "hand_luggage") val handLuggage: HandLuggage,
    @Json(name = "is_returnable") val isReturnable: Boolean,
    @Json(name = "is_exchangable") val isExchangable: Boolean
)

data class Departure(
    @Json(name = "town") val town: String,
    @Json(name = "date") val date: String,
    @Json(name = "airport") val airport: String
)

data class Arrival(
    @Json(name = "town") val town: String,
    @Json(name = "date") val date: String,
    @Json(name = "airport") val airport: String
)

data class Luggage(
    @Json(name = "has_luggage") val hasLuggage: Boolean,
    @Json(name = "price") val price: Price?
)

data class HandLuggage(
    @Json(name = "has_hand_luggage") val hasHandLuggage: Boolean,
    @Json(name = "size") val size: String?
)

data class TicketOffersResponse(
    @Json(name = "tickets_offers") val ticketOffers: List<TicketOffer>
)

data class TicketOffer(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "time_range") val timeRange: List<String>,
    @Json(name = "price") val price: Price
)
