package com.example.poc_carrentalapp.data

import java.util.Date

data class User(
    val name: String,
    val email: String,
    val dob: Date,
    val fcmToken: String
)