package com.example.poc_carrentalapp.viewmodel

import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.poc_carrentalapp.data.User
import com.example.poc_carrentalapp.model.FireStoreRepository
import com.example.poc_carrentalapp.utils.UtilityHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val carPropertyManager: CarPropertyManager, private val fireStoreRepository: FireStoreRepository): ViewModel() {
    private val tag = MainViewModel::class.java.simpleName

    private val _vehicleSpeedMutableLiveData: MutableLiveData<Float> = MutableLiveData(0f)
    val vehicleSpeedLiveData: LiveData<Float> = _vehicleSpeedMutableLiveData

    private val speedPropertyEventCallback = object: CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(p0: CarPropertyValue<*>?) {
            if(p0?.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY) {
                val speedMps = p0.value as Float
                val speedKmph = UtilityHelper.convertSpeedMpsToKmph(speedMps)
                // Post speed value change at the View level
                _vehicleSpeedMutableLiveData.postValue(speedKmph)
            }
        }

        override fun onErrorEvent(p0: Int, p1: Int) {
            Log.e(tag, "speedPropertyEventCallback onErrorEvent() propId: $p0 areaId: $p1")
        }

    }
    init {
        // Subscribe to receive the speed vehicle property value change
        carPropertyManager.subscribePropertyEvents(VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY, CarPropertyManager.SENSOR_RATE_ONCHANGE, speedPropertyEventCallback)
    }

    override fun onCleared() {
        super.onCleared()
        // Unsubscribe the speed vehicle property value change
        carPropertyManager.unsubscribePropertyEvents(VehiclePropertyIds.PERF_VEHICLE_SPEED_DISPLAY, speedPropertyEventCallback)
    }

    fun fetchSpeedLimit(userId: String, onResult: (Float) -> Unit) {
        fireStoreRepository.fetchSpeedLimit(userId, onResult)
    }

    fun fetchUser(userId: String, onUserRetrieved: (User) -> Unit) {
        fireStoreRepository.fetchUser(userId, onUserRetrieved)
    }

    fun updateFcmToken(userId: String, token: String) {
        fireStoreRepository.updateFcmToken(userId, token)
    }

    fun reportSpeedViolation(userId: String, speed: Float, timeStamp: Date) {
        fireStoreRepository.reportSpeedViolation(userId, speed, timeStamp)
    }
}