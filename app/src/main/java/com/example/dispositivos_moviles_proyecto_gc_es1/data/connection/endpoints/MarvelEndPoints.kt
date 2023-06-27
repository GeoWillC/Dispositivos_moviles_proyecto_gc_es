package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.endpoints

import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.jikan.JikanAnimeEntity
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.MarvelEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelEndPoints {
    @GET("characters")
    //Norequiere variable por eso pasa vacia
    suspend fun getCharsStartWith(
        @Query("nameStartsWith") name:String,
        @Query("limit") limit:Int,
        @Query("ts")ts:String="uce",
        @Query("apikey")apikey:String="48ed26ff242038147ce24450236a7ec2",
        @Query("hash")hash:String="f00af94ad24dd1d56b2ea26ae903030e"

    ): Response<MarvelEntity>


}