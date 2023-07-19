package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnResultOk.setOnClickListener {
            val i = Intent()
            i.putExtra("result", "Resultado Exitoso")
            setResult(RESULT_OK, i)
            finish()
        }

        binding.btnResultFalse.setOnClickListener {
            val i = Intent()
            i.putExtra("result", "Resultado Fallido")
            setResult(RESULT_CANCELED, i)
            finish()
        }
    }
}