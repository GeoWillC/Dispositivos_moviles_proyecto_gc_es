package com.example.dispositivos_moviles_proyecto_gc_es1.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class BiometricViewModel :ViewModel() {
    //Tipo de dato o enapsilador al dato que se necesita
    var isLoading= MutableLiveData<Boolean>()
    suspend fun chargingData(){
        isLoading.postValue((true))
        delay(5000)
        isLoading.postValue((false))




    }
}