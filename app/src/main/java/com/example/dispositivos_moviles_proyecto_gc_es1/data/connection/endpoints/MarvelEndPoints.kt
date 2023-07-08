package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints

import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.jikan.JikanAnimeEntity
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.MarvelEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.ZoneOffset

interface MarvelEndPoints {
    @GET("characters")
    //Norequiere variable por eso pasa vacia
    suspend fun getCharsStartWith(
        @Query("nameStartsWith") name: String,
        @Query("limit") limit: Int,
        @Query("ts") ts: String = "uce1",
        @Query("apikey") apikey: String = "48ed26ff242038147ce24450236a7ec2",
        @Query("hash") hash: String = "e39fb11ad271b98d8cac028063ce639b"

    ): Response<MarvelEntity>

    @GET("characters")
    suspend fun getAllMarvelChar(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("ts") ts: String = "uce1",
        @Query("apikey") apikey: String = "48ed26ff242038147ce24450236a7ec2",
        @Query("hash") hash: String = "e39fb11ad271b98d8cac028063ce639b"

    ): Response<MarvelEntity>
}