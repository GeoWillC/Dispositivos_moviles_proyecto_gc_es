package com.example.dispositivos_moviles_proyecto_gc_es1.ui.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic.MarvelLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel: ViewModel() {

   val progressState = MutableLiveData<Int>()
    val items = MutableLiveData<MutableList<Heroes>>()

    fun processBackgroud(value: Long){
        viewModelScope.launch(Dispatchers.Main) {
            val state=View.VISIBLE
            progressState.postValue(state)
            delay(value)
            val state1= View.GONE
            progressState.postValue(state1)

        }
    }



    fun sumInBackground(cant: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = View.VISIBLE
            progressState.postValue(state)

            var total = 0
            for (i in 1 .. cant){
                total += i
            }

            val state1 = View.GONE
            progressState.postValue(state1)
        }
    }

    suspend fun getMarvelChars(offset: Int, limit: Int){
        val newItems=MarvelLogic().getAllMarvelCharacters(offset, limit)
        items.postValue(newItems)
        progressState.postValue((View.GONE))
    }
}