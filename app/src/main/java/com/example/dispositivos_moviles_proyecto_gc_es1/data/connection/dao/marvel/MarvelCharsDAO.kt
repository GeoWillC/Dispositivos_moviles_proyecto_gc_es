package com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.entities.marvel.database.MarvelCharsDB

@Dao
interface MarvelCharsDAO {
    @Query("select * from MarvelCharsDB")
    fun getAllCharacters():List<MarvelCharsDB>

    @Query("select * from MarvelCharsDB where id=:id")
    fun getOneCharacter(id:Int):MarvelCharsDB

    @Insert
    fun insertMarvelChar(ch:List<MarvelCharsDB>)



}