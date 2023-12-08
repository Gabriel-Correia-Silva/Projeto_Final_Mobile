package com.example.projeto_final_mobile.screens.home
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.projeto_final_mobile.databinding.ActivityMapaMarmitariasFragmentBinding
import com.example.projeto_final_mobile.screens.login.SignInActivity

class MapaMarmitariasFragment : Fragment() {
    private var binding: ActivityMapaMarmitariasFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMapaMarmitariasFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.textViewMapa?.text = "Mapas"

        // Verifica o status de login do usuário
        val sharedPreferences = requireActivity().getSharedPreferences(
            "com.example.projeto_final_mobile",
            Context.MODE_PRIVATE
        )
        val userLoggedIn = sharedPreferences.getBoolean("user_logged_in", false)

        if (userLoggedIn) {
            // O usuário está logado
            val userEmail = sharedPreferences.getString("email", "")
            // Agora você pode usar userEmail para obter as informações do usuário do banco de dados
            Log.d("UserStatus", "Usuário logado: $userEmail")
        } else {
            // O usuário não está logado
            // Redireciona para a tela de login
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            Log.d("UserStatus", "Nenhum usuário logado")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}