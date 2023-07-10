package com.example.dispositivos_moviles_proyecto_gc_es1

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
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFirstBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.databinding.FragmentFourthBinding
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.data.Heroes
import com.example.dispositivos_moviles_proyecto_gc_es1.logic.marvelLogic.MarvelLogic
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.activities.DetailsMarvelItem
import com.example.dispositivos_moviles_proyecto_gc_es1.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

