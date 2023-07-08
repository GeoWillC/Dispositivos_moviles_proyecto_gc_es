package com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic

import android.util.Log
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.ApiConnection
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints.MarvelEndPoints
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.getMarvelChar

class MarvelLogic {

    suspend fun getMarvelCharacters(name:String, limit:Int): ArrayList<Heroes> {
        var call =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndPoints::class.java)
        //val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        val itemList = arrayListOf<Heroes>()

        if (call != null) {
            val response = call.getCharsStartWith(name, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    val m=it.getMarvelChar()
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }

        }
        return itemList


    }
    suspend fun getAllMarvelCharacters(offset:Int, limit:Int): ArrayList<Heroes> {
        var call =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndPoints::class.java)
        //val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        val itemList = arrayListOf<Heroes>()

        if (call != null) {
            val response = call.getAllMarvelChar(offset, limit)

            if (response.isSuccessful) {
                response.body()!!.data.results.forEach() {
                    val m=it.getMarvelChar()
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }

        }
        return itemList


    }

}