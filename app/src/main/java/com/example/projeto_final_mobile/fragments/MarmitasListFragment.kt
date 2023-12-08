package com.example.projeto_final_mobile.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_final_mobile.database.adapters.MarmitaAdapter
import com.example.projeto_final_mobile.database.viewmodel.MarmitaViewModel
import com.example.projeto_final_mobile.database.viewmodel.MarmitariaViewModel
import kotlinx.coroutines.launch
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projeto_final_mobile.databinding.MarmitasListFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarmitasListFragment : Fragment() {
    private var binding: MarmitasListFragmentBinding? = null
    private lateinit var marmitaViewModel: MarmitaViewModel
    private lateinit var marmitariaViewModel: MarmitariaViewModel
    private lateinit var marmitaAdapter: MarmitaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MarmitasListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        marmitaViewModel = ViewModelProvider(this).get(MarmitaViewModel::class.java)
        marmitariaViewModel = ViewModelProvider(this).get(MarmitariaViewModel::class.java)

        val currentUser = getCurrentUser()
        if (currentUser != null) {
            Log.d("UserStatus", "Usuário logado: ${currentUser.second}")
        } else {
            Log.d("UserStatus", "Nenhum usuário logado")
        }

        val marmitariaEmail = arguments?.getString("marmitariaEmail")

        lifecycleScope.launch(Dispatchers.IO) {
            if (marmitariaViewModel.getMarmitariaByEmail(marmitariaEmail!!) != null){
                // Inicialize o MarmitaAdapter
                var marmitaAdapter = MarmitaAdapter(childFragmentManager) { marmita ->
                    if (marmita == null){
                        Log.d("Marmita Nula","$marmita")
                    }else {
                        val action =
                            MarmitasListFragmentDirections.actionMarmitasListFragmentToDescricaoFragment3(
                                marmitaId = marmita.marmitaId, 
                                marmitaNome = marmita.nome, 
                                marmitaDescricao = marmita.descricao
                            )
                        findNavController().navigate(action)
                    }
                }

                // Defina o MarmitaAdapter como o adaptador do RecyclerView
                binding?.listademarmitas?.apply {
                    adapter = marmitaAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }
                if(marmitariaEmail != null) {
                    val marmitas = marmitariaEmail?.let { marmitaViewModel.getMarmitasByMarmitaria(marmitariaEmail) }
                    // Atualize a UI com as marmitas
                    withContext(Dispatchers.Main) {
                        if (marmitas != null) {
                            marmitaAdapter.setDataSet(marmitas)
                        }
                    }
                }
            }
        }
    }

    private fun getCurrentUser(): Pair<String, String>? {
        val sharedPreferences = requireActivity().getSharedPreferences("com.example.projeto_final_mobile", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val nome = sharedPreferences.getString("nome", null)

        // Logs para verificar se os dados de login estão chegando
        Log.d("LoginDataCheck", "Email: $email")
        Log.d("LoginDataCheck", "Nome: $nome")

        return if (email != null && nome != null) {
            Pair(nome, email)
        } else {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}