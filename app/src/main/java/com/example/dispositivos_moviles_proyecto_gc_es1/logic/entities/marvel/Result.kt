package com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel

import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun Result.getMarvelChar():Heroes{
    var comic: String = ""
    //se busca si esta mayor de 0 porque la lista esta vacia el campo de comic
    if (comics.items.isNotEmpty()) {
        comic = comics.items[0].name
    }
    val m = Heroes(
        id,
        name, comic,
        thumbnail.path + "." + thumbnail.extension
    )
    return m
}