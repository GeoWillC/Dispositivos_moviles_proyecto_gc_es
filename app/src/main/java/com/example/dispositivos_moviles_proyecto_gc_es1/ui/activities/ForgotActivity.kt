package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityBiometricBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityForgotBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class ForgotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendEmailBtn.setOnClickListener {
            validate()
        }

    }

    private fun validate(){
        var email= binding.email.text.toString().trim()

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.email.setError("Correo Invalido")
            return
        }else{
            sendEmail(email)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun sendEmail(email:String){
        auth= FirebaseAuth.getInstance()
        var emailAddress:String=email

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener {
            if (it.isSuccessful){
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("Correo de recuperacion enviado correctamente")
                    setCancelable(true)
                }
                startActivity(Intent(this, MainActivity::class.java))

            }else{
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("El correo fue invalido")
                    setCancelable(true)
                }
            }
        }
    }
}