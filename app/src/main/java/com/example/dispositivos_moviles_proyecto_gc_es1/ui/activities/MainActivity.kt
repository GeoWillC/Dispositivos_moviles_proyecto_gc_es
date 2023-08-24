package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityMainBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.LocationManager
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

//En todo momento el datastore de tipo preference para crear automaticamente el contenido que debe
//tener la variable
//Mini base de datos clave valor
val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private val GOOGLE_SIGNIN= 100
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val TAG = "UCE"

    //Firebase
    private val callbackManager = CallbackManager.Factory.create()


    //Anotacion que nos permite
    @SuppressLint("MissingInflated")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Aqui si hay context
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.btnLogin.setOnClickListener {
            singInWhitEmailAndPassword(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
        }

        //Facebook zone
        binding.btnFacebook.setOnClickListener {
            signIWithFacebook()
        }

        binding.btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnTwitter.setOnClickListener {
            val googleConf= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleClient: GoogleSignInClient = GoogleSignIn.getClient(this,googleConf)

            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGNIN)

        }

        binding.btnRecuperarContra.setOnClickListener{
            startActivity(Intent(this, ForgotActivity::class.java))
        }

        super.onCreate(savedInstanceState)
    }

    //Inicio sesion Facebook
    private fun signIWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(
                        baseContext,
                        "Authentication cancel",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        baseContext,
                        "Authentication error",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

                override fun onSuccess(result: LoginResult) {
                    result?.let {
                        val token = it.accessToken
                        val credentials = FacebookAuthProvider.getCredential(token.token)
                        FirebaseAuth.getInstance().signInWithCredential(credentials)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            ActivityWithBinding::class.java
                                        )
                                    )
                                } else {
                                    Toast.makeText(
                                        baseContext,
                                        "Authentication nein",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }
                    }
                    Toast.makeText(
                        baseContext,
                        "Authentication success",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }

        )
    }

    //Iniciando sesion firebase
    private fun singInWhitEmailAndPassword(email: String, password: String) {
        if (binding.txtUser.text.toString().trim().isEmpty() || binding.txtPassword.text.toString()
                .trim().isEmpty()
        ) {
            Toast.makeText(
                baseContext,
                "Ingrese los datos por favor.",
                Toast.LENGTH_SHORT,
            ).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        user?.email?.let { userEmail ->
                            val intent = Intent(this, BiometricActivity::class.java)
                            intent.putExtra("user_email", userEmail)
                            startActivity(intent)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
