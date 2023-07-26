package com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient

class LocationManager() {
//    private lateinit var client: SettingsClient
//    client= LocationServices.getSettingsClient(context!!)
    //Forma 1

    var context:Context?=null
    private lateinit var client: SettingsClient
    private fun initVar(){
        if(context!=null){
            client= LocationServices.getSettingsClient(context!!)
        }
    }

    fun getUserLocation(){
        initVar()
    }

}