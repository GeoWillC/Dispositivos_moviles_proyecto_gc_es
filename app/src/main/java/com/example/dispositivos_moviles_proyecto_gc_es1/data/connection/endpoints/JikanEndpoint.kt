package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints

import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.jikan.JikanAnimeEntity
import retrofit2.Response
import retrofit2.http.GET

interface JikanEndpoint {
    @GET("top/anime")
    //Norequiere variable por eso pasa vacia
    suspend fun getAllAnimes(): Response<JikanAnimeEntity>

    //Agregar mas llamados
}