package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {
    //Objeto que apunta a la URL base para hacer el llamado


    fun getJikanConnection(): Retrofit{
        var retrofit= Retrofit.
        Builder().
        baseUrl("https://api.jikan.moe/v4/").addConverterFactory(GsonConverterFactory.create()).
        build()
        return retrofit
    }
}