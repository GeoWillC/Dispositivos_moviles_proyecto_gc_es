package com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic

import android.util.Log
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.ApiConnection
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints.JikanEndpoint
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints.MarvelEndPoints
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.Heroes

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
                    var comic: String = "No available"
                    //se busca si esta mayor de 0 porque la lista esta vacia el campo de comic
                    if (it.comics.items.size > 0) {
                        comic = it.comics.items[0].name
                    }
                    val m = Heroes(

                        it.id,
                        it.name, comic,
                        it.thumbnail.path + "." + it.thumbnail.extension

                    )
                    itemList.add(m)
                }
            } else {
                Log.d("UCE", response.toString())
            }

        }
        return itemList

    }
}