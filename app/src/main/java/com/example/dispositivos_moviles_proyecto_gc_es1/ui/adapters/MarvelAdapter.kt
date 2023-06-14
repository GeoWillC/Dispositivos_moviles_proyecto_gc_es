package com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.MarvelCharactersBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.Heroes


class MarvelAdapter(private val items: List<Heroes>) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)
        fun render(item: Heroes) {
            println("Recibiendo a ${item.heroe}")
            binding.textView.text = item.heroe
            binding.textView2.text = item.comic


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters, parent, false))


    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size


}