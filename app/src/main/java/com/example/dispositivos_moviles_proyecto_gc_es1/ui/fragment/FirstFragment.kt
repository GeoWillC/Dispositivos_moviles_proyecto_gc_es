package com.example.dispositivos_moviles_proyecto_gc_es1.ui.fragment

import android.app.LauncherActivity.ListItem
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispositivos_moviles_proyecto_gc_es1.R
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFirstBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.list.ListItems
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters.MarvelAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentFirstBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onStart() {

        super.onStart()
        val list=arrayListOf<String>("Carlos", "Xavier", "Pepe", "Andres", "Mariano")
        val adapter= ArrayAdapter<String>(requireActivity(), R.layout.simple_layout , list)
        binding.spinner.adapter=adapter
        //binding.listView.adapter=adapter


        var rvAdapter=MarvelAdapter(ListItems().retornarHeroes())
        val rvMarvel=binding.rvMarvelChars
        rvMarvel.adapter= rvAdapter
        rvMarvel.layoutManager=LinearLayoutManager(requireActivity(),
            LinearLayoutManager.VERTICAL,false)
        //false es el orden default true es inverso


    }
}