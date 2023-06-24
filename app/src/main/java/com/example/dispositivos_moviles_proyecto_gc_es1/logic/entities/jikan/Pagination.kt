package com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.jikan

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)