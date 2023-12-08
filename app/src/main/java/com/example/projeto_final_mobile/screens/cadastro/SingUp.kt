package com.example.projeto_final_mobile.screens.cadastro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.database.AuthRepository
import com.example.projeto_final_mobile.database.viewmodel.UserViewModel
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingUp : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val botaoRetornarLogin = findViewById<Button>(R.id.botao_retornar)
        val botaoCadastrar = findViewById<Button>(R.id.botao_cadastrar)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        authRepository = AuthRepository(userDao)

        botaoCadastrar.setOnClickListener {
            val nameUser = findViewById<EditText>(R.id.nome_cadastro).text.toString()
            val sobrenomeUser = findViewById<EditText>(R.id.sobrenome_cadastro).text.toString()
            val enderecoUser = findViewById<EditText>(R.id.endereco_cadastro).text.toString()
            val emailUser = findViewById<EditText>(R.id.email_cadastro).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_cadastro).text.toString()

            if (nameUser.isBlank() || sobrenomeUser.isBlank() || enderecoUser.isBlank() || emailUser.isBlank() || passwordUser.isBlank()) {
                Toast.makeText(this@SingUp, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    authRepository.createUser(emailUser, passwordUser) { success ->
                        if (success) {
                            val user = userDao.getUserByEmail(emailUser).value
                            if (user != null) {
                                userViewModel.setAuthenticatedUser(user)
                            }
                            Toast.makeText(this@SingUp, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this@SingUp, "Falha ao cadastrar o usuário. Tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        botaoRetornarLogin.setOnClickListener {
            finish()
        }
    }
}