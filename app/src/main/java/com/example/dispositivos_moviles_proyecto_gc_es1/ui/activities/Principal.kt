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

        var url="https://media1.giphy.com/media/llarwdtFqG63IlqUR1/giphy.gif?cid=ecf05e478dd219fg4uhmywacrw13popo87e16m2ia70rmp3c&ep=v1_gifs_search&rid=giphy.gif&ct=g"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gift1)
        binding.button.setOnClickListener{
            startActivity(Intent(this,Herramientas::class.java))
        }
    }
}