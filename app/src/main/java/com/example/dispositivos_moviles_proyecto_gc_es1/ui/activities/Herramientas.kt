package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Intent
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.ActivityHerramientasBinding
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
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import java.util.concurrent.Callable

class Herramientas : AppCompatActivity() {

    private lateinit var binding: ActivityHerramientasBinding

    //Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var client: SettingsClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback
    private lateinit var locationSettingsRequest: LocationSettingsRequest
    private var curretnLocation : Location?= null
    //Localizacion
    @SuppressLint("MissingPermission")
    private val location = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
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
                                this@Herramientas,
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
                Snackbar.make(binding.gif2, "Admite el permiso", Snackbar.LENGTH_LONG).show()
            }
            //Denegar
            false -> {
                Snackbar.make(binding.gif2, "Permiso denegado", Snackbar.LENGTH_LONG).show()
            }
            //Tarea ya que contiene varios elementos
            //El usuario debe saber para que necesita ese permiso
            //Tengo el permiso conseguido
        }
    }


    //Busqueda voz
    private val speechToText =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
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

                        intent.putExtra(SearchManager.QUERY,msg)
                        Log.d("VoiceRecognition", "Recognized text: $msg")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHerramientasBinding.inflate(layoutInflater)

        //Gif
        var url="https://media0.giphy.com/media/LESpNIDaNBUcRIPzng/giphy.gif?cid=ecf05e47jejfbv75nnvhp8j7jrqzgowpnd9chlmjelduvmso&ep=v1_gifs_search&rid=giphy.gif&ct=g"
        var parse: Uri = Uri.parse(url)
        Glide.with(this).load(parse).into(binding.gif2)

        //Location
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
                        val intent = Intent(Intent.ACTION_VIEW,
                     Uri.parse("geo:${locations.latitude},${locations.longitude}")
                         //   Uri.parse("tel:0958615079")
                 )
                startActivity(intent)
                    }
                }
            }
        }
        client= LocationServices.getSettingsClient(this)
        locationSettingsRequest=LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest).build()

        //Funcionalidades


        setContentView(binding.root)

        //DONE
        binding.botonMarvelAPI.setOnClickListener {
            startActivity(Intent(this,ActivityWithBinding::class.java))
        }
        //DONE
        binding.busqueda.setOnClickListener {

            //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
            //con geo: se puede mandar la latitud y longitud de una pos del mapa
            /* val intent = Intent(Intent.ACTION_VIEW,
                 //Uri.parse("geo:-0.2006288,-78.5049638")
                 Uri.parse("tel:0958615079")
             )*/
            val web = Intent(Intent.ACTION_WEB_SEARCH)
            // Los parámetros para abrir la aplicación de búsqueda de Google
            web.setClassName(
                "com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity"
            )
            val busqueda:String = "Dispositivos moviles"
            // Utiliza el contenido de la variable msg como la consulta de búsqueda

            web.putExtra(SearchManager.QUERY, busqueda)
            startActivity(web)
        }

        //DONE
        binding.busquedaVoz.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo")
            speechToText.launch(intentSpeech)
        }
        //DONE
        binding.mapa.setOnClickListener {
                //Abre una url con un boton, este intent tiene un punto de partida pero no de llegada
                //con geo: se puede mandar la latitud y longitud de una pos del mapa
//                 val intent = Intent(Intent.ACTION_VIEW,
//                     Uri.parse("geo:-0.2006288,-78.5049638")
//                         //   Uri.parse("tel:0958615079")
//                 )
//                startActivity(intent)
            location.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        //DONE
        binding.alarma.setOnClickListener {
            startActivity(Intent(this,NotificationActivity::class.java))
        }
        //DONE
        binding.camara.setOnClickListener {
            startActivity(Intent(this,CameraActivity::class.java))
        }

    }
    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack)
    }
}