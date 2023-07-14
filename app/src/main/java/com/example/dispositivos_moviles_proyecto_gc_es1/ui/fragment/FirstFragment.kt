package com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFirstBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic.MarvelLogic
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities.DetailsMarvelItem
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters.MarvelAdapter
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities.Dispositivos_moviles_proyecto_gc_es1
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment(value: Boolean) : Fragment() {

    private var confirm = value
    private lateinit var binding: FragmentFirstBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var gManager: GridLayoutManager
    private val limit = 25
    private var offset = 0

    //Ppara asignar luego nuevos valores
    private var marvelCharItems: MutableList<Heroes> = mutableListOf<Heroes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        //Manejo de disposicion de los elementos y tiene la informacion de
        //elementos cargados
       // gManager = GridLayoutManager(requireActivity(), 2)
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        gManager = GridLayoutManager(requireActivity(), 2)

        return binding.root
    }

    override fun onStart() {

        super.onStart()
        //val list = arrayListOf<String>("Carlos", "Xavier", "Pepe", "Andres", "Mariano")
        //val adapter = ArrayAdapter<String>(requireActivity(), R.layout.simple_layout, list)
        //binding.spinner.adapter = adapter
        //binding.listView.adapter=adapter
        //chargeDataRv("cap")
        chargeDataRVInit(offset, limit)

        //Cuando se hace swipe es para hacer la carga de de datos
        binding.rvSwipe.setOnRefreshListener {
            //chargeDataRv("cap")
            chargeDataRv(offset, limit)
            binding.rvSwipe.isRefreshing = false
        }
        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //Cuantos han pasado
                    //Infinite scroll
                    if (dy > 0) {
                        val v = lmanager.childCount
                        //Posicion en la que esta actualmente
                        val p = lmanager.findFirstVisibleItemPosition()
                        //Cantidad de items cargados
                        val t = lmanager.itemCount

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.IO)) {
                                val newItems = MarvelLogic().getAllMarvelCharacters(offset, limit)
//                                val newItems= MarvelLogic().getMarvelCharacters(
//                                    "spider",
//                                    18)
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(newItems)
                                    //this@FirstFragment.offset+= limit
                                }
                            }
                            offset += limit
                        }
                    }
                }
            }
        )
        //filto de datos y normalizacion de datos to lowercase

        binding.txtFilter.addTextChangedListener { filteredText ->
            var newItems = marvelCharItems.filter { items ->
                items.heroe.lowercase().contains(filteredText.toString().lowercase())
            }
            rvAdapter.updateListItemsAdapter(newItems)
        }
    }

    fun sendMarvelItem(item: Heroes) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("heroe", item)
        //proceso de pasar de un objeto a un string para poderlo transmitir por la web
        startActivity(i)
    }

    fun chargeDataRv(offset: Int, limit: Int) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            //relleno la listaa en otro hilo y retorno
            marvelCharItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelCharacters(offset, limit)
            }
            rvAdapter = MarvelAdapter(marvelCharItems) { sendMarvelItem(it) }
            //Se detiene en la linea 83 debe haber un dato que no soparta
//                MarvelLogic().getMarvelCharacters(search, 18)
            //Si hay IO dento de main no hace falta el with context
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            //this@FirstFragment.offset = offset + limit
            //false es el orden default true es inverso
        }
        this.offset+=this.limit
    }

    fun chargeDataRVInit(offset: Int, limit: Int) {
        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                //relleno la listaa en otro hilo y retorno
                marvelCharItems = withContext(Dispatchers.IO) {
                    MarvelLogic().getInitChars(offset, limit)
                    //Lo que estaba aqui se debe poner en el LOGIC, Saludos
                }
                rvAdapter = MarvelAdapter(marvelCharItems) { sendMarvelItem(it) }
                //Se detiene en la linea 83 debe haber un dato que no soparta
//                MarvelLogic().getMarvelCharacters(search, 18)
                //Si hay IO dento de main no hace falta el with context
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                   // gManager.scrollToPositionWithOffset(offset, limit)
                }
                //false es el orden default true es inverso
            }
            this.offset += this.limit

        } else {
            Snackbar.make(binding.cardView, "No hay conexion", Snackbar.LENGTH_LONG).show()
        }
        //hilo principal

        // page++
    }
}
