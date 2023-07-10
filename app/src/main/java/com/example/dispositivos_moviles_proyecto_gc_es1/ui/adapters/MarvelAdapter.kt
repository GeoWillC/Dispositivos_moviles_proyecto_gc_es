package com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.MarvelCharactersBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.squareup.picasso.Picasso

class MarvelAdapter(
    private var items: List<Heroes>,
    // En Java es Void, en Kotlin es Unit
    private var fnClic: (Heroes) -> Unit

) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {

    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var items: List<Heroes> = listOf()
        private var binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)
        fun render(item: Heroes, fnClic: (Heroes) -> Unit) {
            println("Recibiendo a ${item.heroe}")
            //Aqui realizo cambios
            //enlazo codigo con la vista
            binding.textView.bringToFront()
            binding.textView.text = item.heroe
            binding.textView2.text = item.comic
            Picasso.get().load(item.img).into(binding.imageView)
            itemView.setOnClickListener {

                //Aqui va la transicion

                //Snackbar.make(binding.imageView,
                //  item.heroe,Snackbar.LENGTH_SHORT).show()
                fnClic(item)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: MarvelAdapter.MarvelViewHolder,
        position: Int
    ) {
        holder.render(
            items[position],
            fnClic
        )
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<Heroes>) {

        this.items = this.items.plus(newItems)
        //Fuente de datos
        notifyDataSetChanged()
    }

    fun updateListItemsAdapter(newItems: List<Heroes>) {

        this.items = newItems
        //Fuente de datos
        notifyDataSetChanged()
    }
}