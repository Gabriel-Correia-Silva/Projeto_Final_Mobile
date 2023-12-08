package com.example.projeto_final_mobile.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projeto_final_mobile.databinding.SugestaoFragmentBinding
import com.example.projeto_final_mobile.screens.login.SignInActivity

class SugestaoFragment: Fragment() {
    private lateinit var _binding: SugestaoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SugestaoFragmentBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verifica o status de login do usuário
        val sharedPreferences = requireActivity().getSharedPreferences(
            "com.example.projeto_final_mobile",
            Context.MODE_PRIVATE
        )
        val userEmail = sharedPreferences.getString("email", "")
        Log.d("UserStatus", "Usuário logado: $userEmail")

    }
}