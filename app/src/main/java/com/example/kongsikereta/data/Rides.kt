package com.example.kongsikereta.data

data class Rides(
    val date: Long = 0,
    val time: String = "",
    val origin: String = "",
    val destination: String = "",
    val fare: Float = 0f,
    val userId: String = "",
)
