package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
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
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback

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
            //Conceder
            true -> {
                val task = fusedLocationProviderClient.lastLocation

                task.addOnSuccessListener { location ->
                    val alert= AlertDialog.Builder(this)
                    alert.apply { setTitle("Alerta")
                        setMessage("Existe un problema de posicionamiento golbal en el sistema")
                        setPositiveButton("OK"){
                            //dialg es el objeto completo
                                dialog, id-> dialog.dismiss()
                        }
                        setNegativeButton("Cancelar"){
                            dialog, id -> dialog.dismiss()
                        }
                         setCancelable(false)
                    }.create()
                    alert.show()
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallBack,
                        Looper.getMainLooper()
                    )

//                        location.longitude
//                        location.latitude
//                        val a = Geocoder(this)
//                        a.getFromLocation(location.latitude, location.longitude, 1)
                   // val intent = Intent(Intent.ACTION_VIEW,
                     //   Uri.parse("geo:${location.latitude},${location.longitude}"))
                    //startActivity(intent)
                }
                task.addOnFailureListener{

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

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                        Log.d("UCEF", "Ubicacion: ${locations.latitude},"+"${locations.longitude}")
                    }
                }
            }
        }

    }

    // Para iniciar la funcion y evitar problemas con la inicializacion del Binding
    // Se ejecuta inmediatamente despues del onCreate
    override fun onStart() {
        super.onStart()
        initClass()
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

        binding.btnSearch.setOnClickListener {

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
                binding.btnSearch,
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
        binding.btnResult.setOnClickListener {
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
}

