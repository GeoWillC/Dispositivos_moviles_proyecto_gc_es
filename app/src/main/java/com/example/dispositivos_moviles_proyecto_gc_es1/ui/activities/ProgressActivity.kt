package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityProgressBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.viewmodels.ProgressViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProgressBinding
    private val progressViewModel by viewModels<ProgressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Live data botones, clis ejecugamos pricesos, los dos estan aqui logic here
        progressViewModel.progressState.observe(this, Observer {
            binding.progressBar.visibility = it
        })

        progressViewModel.items.observe(this, Observer {

            Toast.makeText(this,it[0].heroe, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,NotificationActivity::class.java))
        })
//Listener

        binding.btnProgreso.setOnClickListener {
            //Espacio y visibilidad
            progressViewModel.processBackgroud(3000)
            //

        }

        binding.btnProgreso2.setOnClickListener {
            //Espacio y visibilidad
            lifecycleScope.launch(Dispatchers.IO) { progressViewModel.getMarvelChars(0, 90) }

            //

        }
    }
}