package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
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

        var url = "https://image.jimcdn.com/app/cms/image/transf/none/path/s55ed47775ac1d6ad/image/i2ca314b685988370/version/1559025202/image.gif"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gif)

        binding.btnRegister.setOnClickListener {
            autWithFirebaseEmail(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.btnLoginMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //Login firebase
    //Grabando el usuario
    private fun autWithFirebaseEmail(email: String, password: String) {
        if (binding.email.text.toString().trim().isEmpty() || binding.password.text.toString()
                .trim().isEmpty()
        ) {
            Toast.makeText(
                baseContext,
                "Por favor agregue datos validos.",
                Toast.LENGTH_SHORT,
            ).show()
        } else {
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
                        startActivity(Intent(this, MainActivity::class.java))
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
}