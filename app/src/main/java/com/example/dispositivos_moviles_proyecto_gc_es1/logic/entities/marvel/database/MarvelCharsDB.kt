package com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class MarvelCharsDB(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var heroe: String,
    var comic: String,
    var img: String,
) :Parcelable

fun MarvelCharsDB.getMarvelChars(): MarvelCharsDB{
    return MarvelCharsDB(
        id,
    heroe,
    comic,
    img
            )
}