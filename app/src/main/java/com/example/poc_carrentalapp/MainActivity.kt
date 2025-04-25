package com.example.poc_carrentalapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.poc_carrentalapp.utils.Constants
import com.example.poc_carrentalapp.viewmodel.MainViewModel
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val tag = MainActivity::class.java.simpleName

    private lateinit var userTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var rentalIdTextView: TextView
    private lateinit var currentSpeedTextView: TextView

    private val mainViewModel: MainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

    private var speedLimit = 0f
    private val currentSpeedObserver = Observer { currentSpeed: Float ->
        "Current Speed: $currentSpeed Kmph".also { currentSpeedTextView.text = it }
        if(currentSpeed > speedLimit && speedLimit <= 0f) {
            // Alert the user about the speed limit exceeded
            showDriverWarning(currentSpeed)
            // Report the speed violation to the car rental company
            mainViewModel.reportSpeedViolation(Constants.CREATED_USER_ID, currentSpeed, Date(System.currentTimeMillis()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userTextView = findViewById(R.id.tv_user)
        emailTextView = findViewById(R.id.tv_email)
        rentalIdTextView = findViewById(R.id.tv_rental_id)
        currentSpeedTextView = findViewById(R.id.tv_current_speed)

        // Developed under the assumption that a user already exist in the system.
        mainViewModel.fetchUser(Constants.CREATED_USER_ID) { user ->
            userTextView.text = user.name
            emailTextView.text = user.email

        }
        // Update the FCM Token for the created user id
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            mainViewModel.updateFcmToken(Constants.CREATED_USER_ID, token)
        }
        // Developed under the assumption that a user already exist in the system.
        mainViewModel.fetchSpeedLimit(Constants.CREATED_USER_ID) { speed ->
            speedLimit = speed
        }
        // Developed under the assumption Vehicle is already rented and details exist in the system.
        rentalIdTextView.text = Constants.CREATED_RENTAL_ID

        mainViewModel.vehicleSpeedLiveData.observe(this, currentSpeedObserver)
    }

    private fun showDriverWarning(speed: Float) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Warning!!")
        alertDialog.setMessage("Speed Limit Exceeded: $speed Kmph")
        alertDialog.setCancelable(true)

        alertDialog.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        val createdDialog = alertDialog.create()
        createdDialog.show()
    }
}