package com.example.projeto_final_mobile.fragments

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.database.viewmodel.UserViewModel
import com.example.projeto_final_mobile.databinding.FragmentDeslogarModificarBinding
import com.google.android.material.imageview.ShapeableImageView
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.models.User
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class FragmentDeslogarModificar : Fragment() {
    private var _binding: FragmentDeslogarModificarBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageButton: ShapeableImageView
    private val PICK_IMAGE = 1
    private val TAKE_PHOTO = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeslogarModificarBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /// Verifica o status de login do usuário
        val sharedPreferences = requireActivity().getSharedPreferences(
            "com.example.projeto_final_mobile",
            Context.MODE_PRIVATE
        )
        val userEmail = sharedPreferences.getString("email", "")
        Log.d("UserStatus", "Usuário logado: $userEmail")

        // Inicialização do banco de dados
        val db = AppDatabase.getInstance(requireContext())
        val userDao = db.userDao()

        // Botão para adicionar foto
        val imageButton: ShapeableImageView = view.findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            // ... Código para escolher ou tirar foto
        }

        // Inicialização das views
        val nomeEditText = view.findViewById<EditText>(R.id.nome_alterar)
        val sobrenomeEditText = view.findViewById<EditText>(R.id.sobrenome_alterar)
        val enderecoEditText = view.findViewById<EditText>(R.id.endereco_alterar)
        val emailEditText = view.findViewById<EditText>(R.id.email_alterar)
        val senhaEditText = view.findViewById<EditText>(R.id.senha_alterar)

        // Observa as alterações do usuário
        val userLiveData = userDao.getUserByEmail(userEmail!!)
        userLiveData.observe(viewLifecycleOwner, { user ->
            nomeEditText.setText(user?.nome)
            sobrenomeEditText.setText(user?.sobrenome)
            enderecoEditText.setText(user?.endereco)
            emailEditText.setText(user?.email)
            senhaEditText.setText(user?.senha)
            if (!user?.foto.isNullOrEmpty()) {
                imageButton.setImageURI(Uri.parse(user?.foto))
            }
            // Log para verificar se o usuário está logado
            if (user != null) {
                Log.d("ModifydataFragment", "Usuário logado: ${user.email}")
            } else {
                Log.d("ModifydataFragment", "Nenhum usuário logado")
            }
        })

        // Botão para salvar as alterações
        val salvarButton = view.findViewById<Button>(R.id.botao_confimar_alteração)
        salvarButton.setOnClickListener {
            val username = nomeEditText.text.toString()
            val sobrenomeUser = sobrenomeEditText.text.toString()
            val enderecoUser = enderecoEditText.text.toString()
            val emailUser = emailEditText.text.toString()
            val passwordUser = senhaEditText.text.toString()
            val fotoUser = imageButton.tag?.toString() ?: ""

            val updatedUser =
                User(username, sobrenomeUser, emailUser, passwordUser, enderecoUser, fotoUser)

            GlobalScope.launch(Dispatchers.IO) {
                userDao.updateUser(updatedUser)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        requireContext(),
                        "Dados alterados com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    // Salva a imagem no diretório de imagens
    private fun saveImage(bitmap: Bitmap): Uri {
        val filesDir = requireContext().filesDir
        val imageFile = File(filesDir, "image.jpg")

        val fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        return Uri.fromFile(imageFile)
    }

    // Obtém o nome do arquivo a partir de uma Uri
    private fun queryName(resolver: ContentResolver, uri: Uri?): String {
        val cursor = resolver.query(uri!!, null, null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val name = cursor.getString(index)
            cursor.close()
            name
        } else {
            uri.path!!.substring(uri.path!!.lastIndexOf("/") + 1)
        }
    }

    // Trata o resultado da seleção de imagem ou captura de foto
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                val selectedImage = data?.data
                startCrop(selectedImage)
            } else if (requestCode == TAKE_PHOTO) {
                val photo = data?.extras?.get("data") as Bitmap
                val savedImageUri = saveImage(photo)
                startCrop(savedImageUri)
            } else if (requestCode == UCrop.REQUEST_CROP) {
                val resultUri = UCrop.getOutput(data!!)
                imageButton.setImageURI(resultUri)
                imageButton.tag = resultUri.toString()
            }
        }
    }

    // Inicia a atividade de recorte de imagem
    private fun startCrop(imageUri: Uri?) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, queryName(requireContext().contentResolver, imageUri)))
        UCrop.of(imageUri!!, destinationUri)
            .withAspectRatio(1f, 1f)
            .start(requireContext(), this)
    }





override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}