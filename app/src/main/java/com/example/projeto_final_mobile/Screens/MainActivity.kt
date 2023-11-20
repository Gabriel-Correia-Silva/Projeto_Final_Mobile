package com.example.projeto_final_mobile.Screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.Screens.SingUpActivity
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.utils.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        
        // Inicializa o ViewModel do usuário
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Obtém as referências dos botões
        val botaoLogar = findViewById<Button>(R.id.botao_login)
        val botaoEsqueceuSenha = findViewById<Button>(R.id.botao_esqueceu_senha)
        val botaoCadastro = findViewById<Button>(R.id.botao_cadastro)

        // Configura o listener do botão de login
        botaoLogar.setOnClickListener {
            // Obtém o email e a senha digitados pelo usuário
            val emailUser = findViewById<EditText>(R.id.email_login).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_login).text.toString()

            // Verifica se os campos estão vazios
            if (emailUser.isBlank() || emailUser.isEmpty() || passwordUser.isBlank() || passwordUser.isEmpty()) {
                Toast.makeText(this@MainActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                val userDao = AppDatabase.getInstance(applicationContext).userDao()

                // Verifica se o usuário existe no banco de dados
                val userLiveData = userDao.getUserByEmailAndSenha(emailUser, passwordUser)
                userLiveData.observe(this) { userList ->
                    if (userList.isNotEmpty()) {
                        // Login bem-sucedido
                        Toast.makeText(this@MainActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()

                        // Salva as informações de autenticação no ViewModel
                        userViewModel.setAuthenticatedUser(userList[0])

                        // Salva o email do usuário nas SharedPreferences
                        val sharedPreferences = getSharedPreferences("com.example.projeto_final_mobile", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("user_email", emailUser)
                        editor.apply()

                        // Redireciona para a próxima tela
                        val intent = Intent(this, com.projeto_final_mobile.Screens.MainScreen::class.java)
                        startActivity(intent)
                        finish() // Finaliza a atividade de login
                    } else {
                        // Credenciais incorretas
                        Toast.makeText(this@MainActivity, "Credenciais incorretas. Tente novamente.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Configura o listener do botão de esqueceu a senha
        botaoEsqueceuSenha.setOnClickListener {
            // Lógica para recuperação de senha
        }

        // Redireciona para a tela de cadastro
        botaoCadastro.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
    }
}
