package com.example.poc_carrentalapp.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MessagingService: FirebaseMessagingService() {
    private val tag = MessagingService::class.java.simpleName

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(tag, "onNewToken(): $token")
    }
}