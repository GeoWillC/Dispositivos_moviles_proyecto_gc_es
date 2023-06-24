package com.example.dispositivos_moviles_proyecto_gc_es1.logic.jikanLogic

import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.ApiConnection
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints.JikanEndpoint
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.Heroes

class JikanAnimeLogic {

    suspend fun getAllAnimes(): List<Heroes> {
        var call = ApiConnection.getJikanConnection()
        val response = call.create(JikanEndpoint::class.java).getAllAnimes()
        val itemList = arrayListOf<Heroes>()

        if (response.isSuccessful) {
            response.body()!!.data.forEach {
                val m = Heroes(
                    it.mal_id,
                    it.title,
                    it.titles[0].title,
                    //Lista de lista de lista
                    it.images.jpg.image_url
                )
                itemList.add(m)
            }
        }
        return itemList
    }
}