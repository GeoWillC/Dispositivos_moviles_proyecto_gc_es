package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.captura.setOnClickListener {
        //Usamos las APIS de camera
            val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraResult.launch(intent)
        }
        binding.imagen.setOnClickListener {
            val shareIntent=Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Vamos a la casa")
            shareIntent.setType("text/plain")
            startActivity(Intent.createChooser(shareIntent,"Compartir"))
        }

    }
    private val cameraResult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        when(result.resultCode){
            //DEBEMOS GUARDAR EN DISCO LA IMAGEN PARA QUE NO SE PIXELE
            Activity.RESULT_OK->{
                val image=result.data?.extras?.get("data") as Bitmap
                binding.imagen.setImageBitmap(image)
            }
            Activity.RESULT_CANCELED->{}
        }


    }

    override fun onStart() {
        super.onStart()

    }
}