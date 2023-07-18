package com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

//En todo momento el datastore de tipo preference para crear automaticamente el contenido que debe
//tener la variable
//Mini base de datos clave valor
val Context.dataStore: DataStore<Preferences> by
preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflated")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                    binding.btnLogin,
                    "Usuario o contraseña incorrectos",
                    Snackbar.LENGTH_SHORT
                ).setBackgroundTint(Color.BLACK).show()
            }

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

            //contrato y clausulas
            when (resultActivity.resultCode) {
                RESULT_OK -> {

                    Snackbar.make(binding.textView2, "Resultado exitoso", Snackbar.LENGTH_LONG).show()
                  //  Log.d("UCE4", "Resultado exitoso")

                }

                RESULT_CANCELED -> {
                    Snackbar.make(binding.textView2, "Resultado fallido", Snackbar.LENGTH_LONG).show()

                   // Log.d("UCE4", "Resultado fallido")
                }

                else -> {
                    Snackbar.make(binding.textView2, "Lo suponia", Snackbar.LENGTH_LONG).show()

                    //Log.d("UCE4", "Resultado dudoso")
                }
            }


        }

        //Diferencia con la primera es que las 2 se van a comunicar y cuando lo hagan se va alanzar este contrato
        val resIntent = Intent(this, ResultActivity::class.java)
        binding.btnResult.setOnClickListener { appResultLocal.launch(resIntent) }
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

