package com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel

data class Series(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)