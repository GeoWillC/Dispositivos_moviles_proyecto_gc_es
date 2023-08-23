package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityNotificationBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        var url="https://i.pinimg.com/originals/e3/bd/80/e3bd8040d980928d459eb79705afbce6.gif"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gif)

        binding.btnRegister.setOnClickListener {
            autWithFirebaseEmail(binding.email.text.toString(),
                binding.password.text.toString())
        }

        binding.btnLoginMain.setOnClickListener {
           startActivity(Intent(this, MainActivity::class.java))
        }



    }

    //Login firebase
    //Grabando el usuario
    private fun autWithFirebaseEmail(email:String , password:String){
        auth.createUserWithEmailAndPassword(email, password)
            //corrutinas con invocadores
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Contants.TAG, "createUserWithEmail:success")
                    //Se puede usar varias opciones como recargar el usuario o verificar
                    val user = auth.currentUser
                    Toast.makeText(
                        baseContext,
                        "Authentication success.",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Contants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }
}