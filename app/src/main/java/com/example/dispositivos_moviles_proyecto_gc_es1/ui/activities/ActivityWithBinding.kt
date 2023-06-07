package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityWithBindingBinding
import com.google.android.material.snackbar.Snackbar

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
        initClass()


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
        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.inicio -> {
                    // Respond to navigation item 1 click
                    var suma = 0
                    for (i in 1..10) {
                        suma += i
                    }

                    Snackbar.make(binding.cuadroMensaje, "La suma es ${suma}", Snackbar.LENGTH_LONG)
                        .show()

                    true
                }

                R.id.favorito -> {
                    // Respond to navigation item 2 click
                    var suma = 0
                    for (i in listOf<Int>(1, 2, 3)) {
                        suma += i
                    }

                    Snackbar.make(binding.cuadroMensaje, "La suma es ${suma}", Snackbar.LENGTH_LONG)
                        .show()


                    true
                }

                R.id.chat -> {
                    // Respond to navigation item 2 click
                    true
                }

                else -> false
            }

        }


    }
}
