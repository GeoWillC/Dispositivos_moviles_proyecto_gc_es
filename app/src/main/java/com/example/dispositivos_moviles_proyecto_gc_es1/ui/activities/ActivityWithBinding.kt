package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityWithBindingBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment.FirstFragment
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment.FourthFragment
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment.SecondFragment
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment.ThirdFragment
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.ManagerFragment

class ActivityWithBinding : AppCompatActivity() {

    private lateinit var binding: ActivityWithBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        var name: String = ""
        // De esta forma accedemos a los contenidos enviados por otra Activity
        // intent.extras.let {
        // it? significa que este item/objeto puede ser nulo
        //   name = it?.getString("var1")!!
        //}
        Log.d("UCE", "Hola $name")
        binding.titulo.text = "Bienvenido $name!"

        /* // Se usa !! si estamos seguros de que siempre llegara informacion a nuestra activity
        intent.extras!!.let {
            // it? significa que este item/objeto puede ser nulo
            var name = it?.getString("var1")

        }
         */
        Log.d("UCE", "Entrando a Start")
        super.onStart()
        ManagerFragment().replaceFragment(
            supportFragmentManager,
            binding.frContainer.id,
            FirstFragment(false)
        )
        initClass()
        var url="https://pa1.aminoapps.com/6451/18eff24619d15485d69654dca52e846529a930cc_hq.gif"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gif3)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initClass() {
        binding.imageButton2.setOnClickListener {
            // Asi se define un intent y se menciona a que Activity se trasladara
            var intent = Intent(
                this,
                MainActivity::class.java
            )
            // Con esto iniciamos el otro Activity
            startActivity(intent)

        }
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    ManagerFragment().replaceFragment(
                        supportFragmentManager,
                        binding.frContainer.id,
                        FirstFragment(false)
                    )
                    true
                }

                R.id.search -> {
                    ManagerFragment().replaceFragment(
                        supportFragmentManager,
                        binding.frContainer.id,
                        FourthFragment()
                    )
                    true
                }

                else -> {false}
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

}