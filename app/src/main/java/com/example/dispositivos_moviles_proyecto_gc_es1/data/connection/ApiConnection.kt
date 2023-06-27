package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConnection {
    //Objeto que apunta a la URL base para hacer el llamado

    enum class  typeApi{
        Jikan, Marvel, Pets
    }

    private val API_JIKAN="https://api.jikan.moe/v4/"
    private val API_MARVEL="https://gateway.marvel.com/v1/public/"
    private val API_PETS=""
   private fun getConnection(base:String): Retrofit{
        var retrofit= Retrofit.
        Builder().
        baseUrl(base).addConverterFactory(GsonConverterFactory.create()).
        build()
        return retrofit
    }

    suspend fun <T, E:Enum<E>> getService(api:E, service:Class<T>):T{
        var BASE=""
        when(api.name){
            typeApi.Jikan.name->{
                BASE= API_JIKAN
            }

            typeApi.Marvel.name->{
                BASE= API_MARVEL
            }

            typeApi.Marvel.name->{
                BASE= API_PETS
            }
        }
        return getConnection(BASE).create(service)
    }
}