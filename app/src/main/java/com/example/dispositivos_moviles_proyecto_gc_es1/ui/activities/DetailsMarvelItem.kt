package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityDetailsMarvelItemBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityWithBindingBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.Heroes
import com.squareup.picasso.Picasso

class DetailsMarvelItem : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMarvelItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        /*var name:String?=""

        intent.extras?.let { name=it.getString("name") }

        if(!name.isNullOrEmpty()){
            binding.txtName.text=name
        }*/
        val item=intent.getParcelableExtra<Heroes>("heroe")


        if(item != null){
            binding.txtName.text=item.heroe
            binding.txtComic.text=item.comic

            Picasso.get().load(item.img).into(binding.imgMarvel)
        }


    }

}