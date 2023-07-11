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


    //Ppara asignar luego nuevos valores
    private var marvelCharItems: MutableList<Heroes> = mutableListOf<Heroes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(layoutInflater, container, false)
        //Manejo de disposicion de los elementos y tiene la informacion de
        //elementos cargados
        gManager = GridLayoutManager(requireActivity(), 2)
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
        val list = arrayListOf<String>("Carlos", "Xavier", "Pepe", "Andres", "Mariano")
        val adapter = ArrayAdapter<String>(requireActivity(), R.layout.simple_layout, list)
        binding.spinner.adapter = adapter
        //binding.listView.adapter=adapter
       //chargeDataRv("cap")
        chargeDataRVDB(5)

        //Cuando se hace swipe es para hacer la carga de de datos
        binding.rvSwipe.setOnRefreshListener {
            //chargeDataRv("cap")
            chargeDataRVDB(5)
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
                                val newItems = MarvelLogic().getAllMarvelCharacters(0, 99)
//                                val newItems= MarvelLogic().getMarvelCharacters(
//                                    "spider",
//                                    18)
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItems(newItems)
                                }
                            }
                        }
                    }
                }
            }
        )
        //filto de datos y normalizacion de datos to lowercase
        /*
        binding.txtFilter.addTextChangedListener { filteredText->
           var newItems= marvelCharItems.filter { items->items.heroe.lowercase().contains(filteredText.toString().lowercase()) }
            rvAdapter.updateListItemsAdapter(newItems)
        }*/
    }

    fun sendMarvelItem(item: Heroes) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("heroe", item)
        //proceso de pasar de un objeto a un string para poderlo transmitir por la web
        startActivity(i)
    }

    /*
    fun corrutine(){
        lifecycleScope.launch(Dispatchers.Main){
            var name="Byron"

            //Realiza un cambio, crea otro hilo modifica procesa y se va
            name= withContext(Dispatchers.IO){
                name= "Jairo"
                return@withContext name
            }
            binding.cardView.radius
        }
    }
    */
    fun chargeDataRv(search: String) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            //relleno la listaa en otro hilo y retorno
            marvelCharItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getAllMarvelCharacters(0, 20)
            }
            rvAdapter = MarvelAdapter(marvelCharItems) { sendMarvelItem(it) }
            //Se detiene en la linea 83 debe haber un dato que no soparta
//                MarvelLogic().getMarvelCharacters(search, 18)
            //Si hay IO dento de main no hace falta el with context
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            //false es el orden default true es inverso
        }
    }

    fun chargeDataRVDB(pos: Int) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            //relleno la listaa en otro hilo y retorno
            marvelCharItems = withContext(Dispatchers.IO) {
               var marvelCharItems= MarvelLogic().getAllMarvelCharsDB().toMutableList()

                if(marvelCharItems.isEmpty()){
                    marvelCharItems =  (MarvelLogic().getAllMarvelCharacters(0, 50))
                    MarvelLogic().insertMarvelCharsToDB(marvelCharItems)
                }

            return@withContext marvelCharItems
            }


            rvAdapter = MarvelAdapter(marvelCharItems) { sendMarvelItem(it) }
            //Se detiene en la linea 83 debe haber un dato que no soparta
//                MarvelLogic().getMarvelCharacters(search, 18)
            //Si hay IO dento de main no hace falta el with context
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
                gManager.scrollToPositionWithOffset(pos, 10)
            }
            //false es el orden default true es inverso
        }
       // page++
    }
}
