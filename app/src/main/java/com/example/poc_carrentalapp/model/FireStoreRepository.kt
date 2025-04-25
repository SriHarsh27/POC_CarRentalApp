package com.example.poc_carrentalapp.model

import android.util.Log
import com.example.poc_carrentalapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date


class FireStoreRepository {
    private val tag = FireStoreRepository::class.java.simpleName

    private val db = FirebaseFirestore.getInstance()

    fun fetchSpeedLimit(userId: String, onSpeedLimitRetrieved: (Float) -> Unit) {
        db.collection("rentals")
            .whereEqualTo("userId", "/users/$userId")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val rentalDocument = querySnapshot.documents.lastOrNull()
                val speedLimit = rentalDocument?.getDouble("speed_limit") ?: 80.0
                onSpeedLimitRetrieved(speedLimit.toFloat())
            }
            .addOnFailureListener {
                Log.e(tag, "fetchSpeedLimit() userId: $userId - Failed to fetch speed limit: $it")
                onSpeedLimitRetrieved(80f)
            }
    }

    fun fetchUser(userId: String, onUserRetrieved: (User) -> Unit) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                val name = document.getString("name") ?: "null"
                val email = document.getString("email") ?: "null"
                val dob = document.getDate("dob") ?: Date()
                val fcmToken = document.getString("fcm_token") ?: "null"

                val user = User(name, email, dob, fcmToken)
                onUserRetrieved(user)
            }
            .addOnFailureListener {
                Log.e(tag, "getUser() userId: $userId - Failed to retrieve user details $it")
                val user = User("null", "null", Date(), "null")
                onUserRetrieved(user)
            }
    }

    fun updateFcmToken(userId: String, token: String) {
        db.collection("users").document(userId).update("fcm_token", token)
    }

    fun reportSpeedViolation(userId: String, speed: Float, timeStamp: Date) {
        val data = mapOf(
            "user_id" to "/users/$userId",
            "speed" to speed,
            "timestamp" to timeStamp
        )
        db.collection("violations").add(data)
    }

}