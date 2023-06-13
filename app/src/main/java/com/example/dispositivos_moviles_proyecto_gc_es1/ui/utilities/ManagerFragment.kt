package com.example.dispositivos_moviles_proyecto_gc_es1.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentManager

class ManagerFragment {

    fun replaceFragment(manager: FragmentManager, container: Int, fragment:Fragment){
        with(manager.beginTransaction()){
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }

    }

    fun addFragment(manager: FragmentManager, container: Int, fragment:Fragment){
        with(manager.beginTransaction()){
            add(container, fragment)
            addToBackStack(null)
            commit()
        }

    }
}