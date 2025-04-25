package com.example.poc_carrentalapp.utils

object UtilityHelper {
    fun convertSpeedMpsToKmph(value: Float): Float {
        return (value * (18 / 5))
    }
}