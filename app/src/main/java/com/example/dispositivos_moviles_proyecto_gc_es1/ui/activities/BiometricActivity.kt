package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.content.Intent
import  androidx.biometric.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_BIOMETRIC_ENROLL
import android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityBiometricBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.viewmodels.BiometricViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class BiometricActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiometricBinding
    private val biometricViewModel by viewModels<BiometricViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBiometricBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAutentication.setOnClickListener {
            autenticaBiometric()
        }
        biometricViewModel.isLoading.observe(this){
            isLoading->
            if(isLoading){
                binding.main1.visibility= View.GONE
                binding.mainCopia.visibility= View.VISIBLE

            }
            else{
                binding.main1.visibility= View.VISIBLE
                binding.mainCopia.visibility= View.GONE
            }
        }
        lifecycleScope.launch {
            biometricViewModel.chargingData()
        }

    }

    private fun autenticaBiometric() {
        if(checkBiometric()){
        //Ejecutor hacer que los contectos sean locales
        val executor = ContextCompat.getMainExecutor(this)

        val biometricPromp = BiometricPrompt.PromptInfo.Builder()
            //Alert que habilita la validacion mediante huella dactilar
            .setTitle("Titulo del Dialogo").setSubtitle("Subtitulo del dialogo")
            .setDescription("Ponga el dedo")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .setNegativeButtonText("").build()
        val biometricManager = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                //ctrl + o para crear los metodos
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            })
        biometricManager.authenticate(biometricPromp)
    }else{
        Snackbar.make(binding.btnAutentication,"Noexistenrequisitos",Snackbar.LENGTH_LONG).show()
    }


    }
    private fun checkBiometric():Boolean{
        //Control de acceso al dispositivo biometrico

        val biometricManager= BiometricManager.from(this)
        var returnValid=false
        Log.d("Error2","error")
        when(biometricManager.canAuthenticate(
            BIOMETRIC_STRONG or DEVICE_CREDENTIAL
        )){
            BiometricManager.BIOMETRIC_SUCCESS ->{
                returnValid= true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                Log.d("Error1","error")
                returnValid= false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                Log.d("Error2","error")
                returnValid= false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                Log.d("Error3","error")
                //Intent implicito
                val intentPromp= Intent(ACTION_BIOMETRIC_ENROLL)
                intentPromp.putExtra(EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    //Formas de acceso / Niveles de segurida revisar
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                startActivity(intentPromp)
                returnValid=false
            }
        }
        return returnValid
    }
}