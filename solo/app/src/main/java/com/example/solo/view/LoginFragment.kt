package com.example.solo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.solo.R
import com.example.solo.model.api.ApiService
import com.example.solo.model.api.RetrofitClient
import com.example.solo.model.data.LoginRequest
import com.example.solo.model.data.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var emailInput: EditText
    private lateinit var pwdInput: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRecoverPwd: Button
    private lateinit var btnCreateAccount: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        emailInput = view.findViewById(R.id.email_input)
        pwdInput = view.findViewById(R.id.pwd_input)
        btnLogin = view.findViewById(R.id.btnlogin)
        btnRecoverPwd = view.findViewById(R.id.btnRecoverPwd)
        btnCreateAccount = view.findViewById(R.id.btnCreateAccount)

        btnLogin.setOnClickListener {
            performLogin()
        }

        btnRecoverPwd.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverPwdFragment)
        }

        return view
    }

    private fun performLogin() {
        val email = emailInput.text.toString().trim()
        val password = pwdInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        val apiService = RetrofitClient.getClient().create(ApiService::class.java)
        val loginRequest = LoginRequest(nickname = email, password = password)

        apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {

                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Erro na comunicação com o servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Falha na conexão com o servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
