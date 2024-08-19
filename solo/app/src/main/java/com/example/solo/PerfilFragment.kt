package com.example.solo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val imgBack: ImageView = view.findViewById(R.id.imgBack)
        imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_loginFragment)
        }
        return view
    }
}