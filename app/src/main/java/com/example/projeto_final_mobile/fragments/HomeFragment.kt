package com.example.projeto_final_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.projeto_final_mobile.MapsFragment

import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.databinding.HomeFragmentBinding
import com.example.projeto_final_mobile.screens.home.ListaMarmitariasFragment
import com.example.projeto_final_mobile.screens.home.MapaMarmitariasFragment

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.create(R.navigation.navegacao_mamitaria)
        childFragmentManager.beginTransaction()
            .replace(R.id.container_home_content, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commit()

        binding.bottomNavigationViewView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lista_view -> {
                    childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    navHostFragment.navController.navigate(R.id.listaMarmitariaFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.mapa_view -> {
                    val mapaFragment = MapsFragment()
                    childFragmentManager.beginTransaction()
                        .replace(R.id.container_home_content, mapaFragment)
                        .addToBackStack(null) // Adiciona o fragmento à pilha de backstack
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
