package com.example.solo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class CadastroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro, container, false)

        val btnCadastro: Button = view.findViewById(R.id.btnCadastro)
        btnCadastro.setOnClickListener {
            findNavController().navigate(R.id.action_cadastroFragment_to_habitosFragment)
        }

        return view
    }

}