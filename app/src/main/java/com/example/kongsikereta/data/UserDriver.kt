package com.example.kongsikereta.data

data class UserDriver(
    val ic: String = "",
    val password: String = "",
    val gender: Boolean = false,
    val phoneNumber: String = "",
    val email: String = "",
    val address: String = "",
    val vehicle: Vehicle = Vehicle()
)

data class Vehicle(
    val model: String = "",
    val capacity: Int = 0,
    val notes: String = "",
)

data class UserDriverInfo(
    val ic: String = "",
    val password: String = "",
    val profilePicUrl: String = "",
    val userDataUrl: String = "",
)