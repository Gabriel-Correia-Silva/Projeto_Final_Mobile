package com.example.projeto_final_mobile.screens.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projeto_final_mobile.database.adapters.MarmitariaAdapter
import com.example.projeto_final_mobile.database.viewmodel.MarmitaViewModel
import com.example.projeto_final_mobile.database.viewmodel.MarmitariaViewModel
import com.example.projeto_final_mobile.databinding.ActivityListaMarmitariasFragmentBinding
import com.projeto_final_mobile.database.models.Marmitaria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaMarmitariasFragment : Fragment() {
    private var binding: ActivityListaMarmitariasFragmentBinding? = null
    private lateinit var marmitariaViewModel: MarmitariaViewModel
    private lateinit var marmitariaAdapter: MarmitariaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityListaMarmitariasFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val observer = Observer<List<Marmitaria>> {  marmitarias ->
        marmitarias.forEach{ marmitaria ->
            Log.d(
                "MarmitariaViewModel",
                "Nome: ${marmitaria.nome},Email: ${marmitaria.email}, Preço Médio: ${marmitaria.precomedio}, Imagem: ${marmitaria.imagelogo}"
            )
        }

        Log.d(
            "MarmitariaViewModel",
            "Dados atualizados: ${marmitarias.size} marmitarias carregadas."
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var marmitaViewModel = ViewModelProvider(this).get(MarmitaViewModel::class.java)

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
            "com.example.projeto_final_mobile",
            Context.MODE_PRIVATE
        )
        val userEmail = sharedPreferences.getString("email", null)

        if (userEmail != null) {
            Log.d("UserStatus", "Usuário logado: $userEmail")
        } else {
            Log.d("UserStatus", "Nenhum usuário logado")
        }

        // Inicialize o MarmitariaAdapter com o navcontroller
        marmitariaAdapter = MarmitariaAdapter { marmitaria ->
            lifecycleScope.launch(Dispatchers.IO) { // Use Dispatchers.IO to move the database access to a background thread
                val marmitas = marmitaViewModel.getMarmitasByMarmitaria(marmitaria.email)
                withContext(Dispatchers.Main) { // Switch back to the main thread to update the UI
                    if (marmitas.isEmpty()) {
                        Toast.makeText(context, "Marmitaria vazia", Toast.LENGTH_SHORT).show()
                    } else {
                        val action =
                            ListaMarmitariasFragmentDirections.actionListaMarmitariaFragmentToListaMarmitaFragment(
                                marmitaria.email
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }

        // Defina o MarmitariaAdapter como o adaptador do RecyclerView
        binding?.recyclerViewInicialMarmitarias?.apply {
            adapter = marmitariaAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // Inicialize o MarmitariaViewModel
        marmitariaViewModel = ViewModelProvider(this).get(MarmitariaViewModel::class.java)

        // Observe os dados do MarmitariaViewModel e atualize o conjunto de dados do adaptador quando os dados mudarem
        var listOfMarmitas : List<Marmitaria>
        marmitariaViewModel.viewModelScope.launch {
            listOfMarmitas = marmitariaViewModel.getAllMarmitarias()
            marmitariaAdapter.setDataSet(listOfMarmitas)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
