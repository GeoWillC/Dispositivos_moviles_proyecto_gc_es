package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.media.audiofx.Equalizer.Settings
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.provider.Settings.ACTION_MANAGE_SUPERVISOR_RESTRICTED_SETTING
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.data.connection.dao.marvel.MarvelCharsDAO
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityMainBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.Dispositivos_moviles_proyecto_gc_es1
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.LocationManager
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.Login
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
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
import com.google.firebase.auth.GoogleAuthProvider
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


    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var llamado: CallbackManager

    private lateinit var auth: FirebaseAuth
    private val TAG= "UCE"


    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    //En esta parte no tiene contexto this no funciona
    private lateinit var client:SettingsClient
    //Pedimos al servicio de localizacion que active la solicitud de alta precision
    private lateinit var locationSettingsRequest:LocationSettingsRequest
    private var curretnLocation : Location?= null

    private val speechToText =
        registerForActivityResult(StartActivityForResult()) { activityResult ->
            val sn = Snackbar.make(
                binding.textView2,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = ""
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val msg =
                        //Arreglo de textos, get(0) obtener el primer intento
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.get(0)
                            .toString()
                    if (msg.isNotEmpty()) {
                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)
                    }
                }

                RESULT_CANCELED -> {
                    message = "Proceso Cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.orange))
                }

                else -> {
                    message = "Resultado Erroneo"
                    sn.setBackgroundTint(resources.getColor(R.color.rojito))
                }
            }
            sn.setText(message)
            sn.show()
        }

    @SuppressLint("MissingPermission")
    private val location = registerForActivityResult(RequestPermission()) {
        //Enviamos al permiso que necesitamos que se ejecute
            isGranted: Boolean ->
        when (isGranted) {
            //Conceder location contract
            true -> {
                //Comprueba que el GPS este funcionando
                client.checkLocationSettings(locationSettingsRequest).apply {
                    addOnSuccessListener {
                        val task = fusedLocationProviderClient.lastLocation
                        task.addOnSuccessListener { location ->
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallBack,
                                Looper.getMainLooper()
                            )
                        }
                    }
                    addOnFailureListener{ex->
                        if(ex is ResolvableApiException){

                            //Lanza el alert dialog con todo
                            // listo para que se habilite el GPS
                            //LA API MISMO SOLUCIONA
                            ex.startResolutionForResult(
                                this@MainActivity,
                                LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                            )
                        }

                    }

                }
            }

            //Dialogo de porque necesita el permiso, lo solicita 2 veces
            shouldShowRequestPermissionRationale(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Snackbar.make(binding.imageView, "Cruza el permiso oe", Snackbar.LENGTH_LONG).show()
            }
            //Denegar
            false -> {
                Snackbar.make(binding.imageView, "Permiso denegado", Snackbar.LENGTH_LONG).show()
            }
            //Tarea ya que contiene varios elementos
            //El usuario debe saber para que necesita ese permiso
            //Tengo el permiso conseguido
        }
    }

    //Anotacion que nos permite
    @SuppressLint("MissingInflated")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Aqui si hay context
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        //Variable de clase inicializada cuando se ejecute onCreate
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            2000
        ).setMaxUpdates(10).build()

        locationCallBack =object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if(locationResult!=null){
                    locationResult.locations.forEach{locations->
                        curretnLocation=locations
                        Log.d("UCEF", "Ubicacion: ${locations.latitude}," +
                                ""+"${locations.longitude}")
                    }
                }
            }
        }
        client=LocationServices.getSettingsClient(this)
        locationSettingsRequest=LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()
        super.onCreate(savedInstanceState)

        binding.btnLogin.setOnClickListener {
            singInWhitEmailAndPassword(binding.txtUser.text.toString(),
                binding.txtPassword.text.toString())
        }

        binding.btnRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnFacebook.setOnClickListener {
            singInWithFacebook()

        }


    }


    //Login firebase
    //Grabando el usuario


    //Iniciando sesion

    private fun singInWithFacebook(){
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

        //facebook
        llamado= CallbackManager.Factory.create()
        val facebookCallback = object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                loginResult?.let {
                    val llave= it.accessToken
                    val credentials = FacebookAuthProvider.getCredential(llave.token)
                    FirebaseAuth.getInstance().signInWithCredential(credentials).addOnCompleteListener {
                        if (it.isSuccessful){
                            startActivity(Intent(this@MainActivity, ActivityWithBinding::class.java))
                            Toast.makeText(
                                baseContext,
                                "Facebook Authentication success.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }else{
                            Toast.makeText(
                                baseContext,
                                "Facebook Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                }

                //startActivity(Intent(this@MainActivity, ActivityWithBinding::class.java))
            }

            override fun onCancel() {
                // Maneja el inicio de sesión cancelado aquí
                Toast.makeText(
                    baseContext,
                    "Facebook Authentication cancelada.",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(
                    baseContext,
                    "Facebook Authentication error.",
                    Toast.LENGTH_SHORT,
                ).show()
                // Maneja errores aquí
            }
        }
        LoginManager.getInstance().registerCallback(llamado, facebookCallback)
    }

    private  fun singInWhitEmailAndPassword(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                   // updateUI(user)
                    startActivity(Intent(this, BiometricActivity::class.java))
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

    private fun recoveryPasswordWithEmail(email: String){
        auth.sendPasswordResetEmail(email).addOnCompleteListener{task->
            //Es una tarea
            if (task.isSuccessful){
                Toast.makeText(
                    this,
                    "Correo de recuperacion enviado correctamente",
                    Toast.LENGTH_SHORT,
                ).show()
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Alert")
                    setMessage("Correo de recuperacion enviado correctamente")
                    setCancelable(true)
                }

            }else{

            }

        }
    }


    // Para iniciar la funcion y evitar problemas con la inicializacion del Binding
    // Se ejecuta inmediatamente despues del onCreate
    override fun onStart() {
        super.onStart()
        //initClass()
        /*val db=Dispositivos_moviles_proyecto_gc_es1.getDbIntance()
        db.marvelDao()*/
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }

    // Podemos crear nuestras funcionalidades en una funcion apartada e invocarla en el onCreate

    fun initClass() {
        binding.btnLogin.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.txtUser.text.toString(),
                binding.txtPassword.text.toString()
            )
            if (check) {
                //No voy a modificar nada por eso solo necesito IO
                lifecycleScope.launch(Dispatchers.IO) {
                    saveDataStore(binding.txtUser.text.toString())
                }
                var intent = Intent(
                    this,
                    ActivityWithBinding::class.java
                )

                intent.putExtra("var1", binding.txtUser.text.toString())

                startActivity(intent)
            } else {
                Snackbar.make(
                    binding.root,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.BLACK).show()
            }

        }



        binding.btnRecuperarContra.setOnClickListener {
            location.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        binding.btnRegistro.setOnClickListener {

            //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
            //con geo: se puede mandar la latitud y longitud de una pos del mapa
            /* val intent = Intent(Intent.ACTION_VIEW,
                 //Uri.parse("geo:-0.2006288,-78.5049638")
                 Uri.parse("tel:0958615079")
             )*/
            val web = Intent(Intent.ACTION_WEB_SEARCH)
            //Los parametros para abrir una aplicacion especifica
            web.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            web.putExtra(SearchManager.QUERY, "QUERY")
            startActivity(web)
        }
        val appResultLocal = registerForActivityResult(StartActivityForResult()) { resultActivity ->

            val sn = Snackbar.make(
                binding.btnRegistro,
                "",
                Snackbar.LENGTH_LONG
            )

            var message = when (resultActivity.resultCode) {
                RESULT_OK -> {
                    sn.setBackgroundTint(resources.getColor(R.color.azul))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                RESULT_CANCELED -> {
                    sn.setBackgroundTint(resources.getColor(R.color.rojito))
                    resultActivity.data?.getStringExtra("result").orEmpty()
                }

                else -> {
                    "Resultado Erroneo"
                }
//
            }
            sn.setText(message)
            sn.show()

        }


        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato
        binding.btnFacebook.setOnClickListener {
            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }
        binding.btnTwitter.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
            speechToText.launch(intentSpeech)
        }
    }

    private suspend fun saveDataStore(stringData: String) {
        // it implicito cambiamos por prefs
        dataStore.edit { prefs ->
            prefs[stringPreferencesKey("usuario")] = stringData
            //universal unic identifier
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dismov@uce.edu.ec"
        }
    }

    //Forma uno de inicializar variables
    private fun test(){
      //  var location=LocationManager(this)

        //Envio un contexto para que la otra clase pueda inicializarse
        var location=LocationManager()
        location.context=this
        location.getUserLocation()
    }
}

