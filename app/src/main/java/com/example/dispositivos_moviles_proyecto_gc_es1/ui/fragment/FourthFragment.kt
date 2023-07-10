package com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFirstBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFourthBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic.MarvelLogic
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities.DetailsMarvelItem
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FourthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthFragment : Fragment() {


    private lateinit var binding: FragmentFourthBinding
    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter
    private lateinit var gManager: GridLayoutManager

    //Ppara asignar luego nuevos valores
    private var marvelCharItems: MutableList<Heroes> = mutableListOf<Heroes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFourthBinding.inflate(layoutInflater, container, false)
        //Manejo de disposicion de los elementos y tiene la informacion de
        //elementos cargados
        gManager = GridLayoutManager(requireActivity(), 2)
        lmanager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        gManager = GridLayoutManager(requireActivity(), 2)

        return binding.root
    }

    override fun onStart() {

        super.onStart()

        //binding.listView.adapter=adapter
        //Cuando se hace swipe es para hacer la carga de de datos
        binding.txtFilter.addTextChangedListener {
            chargeDataRv(binding.txtFilter.text.toString())
        }

        //filto de datos y normalizacion de datos to lowercase
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
                return@withContext MarvelLogic().getMarvelCharacters(search, 100)
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

    fun chargeDataSearch(search: String) {
        //hilo principal
        lifecycleScope.launch(Dispatchers.Main) {
            //relleno la listaa en otro hilo y retorno
            marvelCharItems = withContext(Dispatchers.IO) {
                return@withContext MarvelLogic().getMarvelCharacters(
                    binding.txtFilter.text.toString(), 10
                )
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
}
