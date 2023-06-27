package com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)