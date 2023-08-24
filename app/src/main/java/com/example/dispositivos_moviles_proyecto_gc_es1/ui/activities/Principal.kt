package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityPrincipalBinding

class Principal : AppCompatActivity() {
    private lateinit var imagen:String
    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var url="https://res.cloudinary.com/zenbusiness/image/upload/v1670445040/logaster/logaster-2020-08-new-android-logo-evolution-1.gif"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gift1)
        binding.button.setOnClickListener{
            startActivity(Intent(this,Herramientas::class.java))
        }
    }
}