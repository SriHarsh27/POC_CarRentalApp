package com.example.poc_carrentalapp.di

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import com.example.poc_carrentalapp.model.FireStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppComponentModule {
    @Provides
    @Singleton
    fun provideCar(@ApplicationContext context: Context): Car {
        return Car.createCar(context)
    }

    @Provides
    @Singleton
    fun provideCarPropertyManager(car: Car): CarPropertyManager {
        return car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    @Provides
    @Singleton
    fun provideFireStoreRepository(): FireStoreRepository {
        return FireStoreRepository()
    }
}