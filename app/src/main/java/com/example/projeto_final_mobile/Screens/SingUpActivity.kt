package com.projeto_final_mobile.Screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_final_mobile.R
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.models.User

class SingUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Botão para retornar à tela de login
        val botaoRetornarLogin = findViewById<Button>(R.id.botao_retornar)

        // Botão para cadastrar o usuário
        val botaoCadastrar = findViewById<Button>(R.id.botao_cadastrar)

        botaoCadastrar.setOnClickListener {
            // Obter os dados do usuário dos campos de texto
            val nameUser = findViewById<EditText>(R.id.nome_cadastro).text.toString()
            val sobrenomeUser = findViewById<EditText>(R.id.sobrenome_cadastro).text.toString()
            val enderecoUser = findViewById<EditText>(R.id.endereco_cadastro).text.toString()
            val emailUser = findViewById<EditText>(R.id.email_cadastro).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_cadastro).text.toString()
            val passwordConfirmaUser = findViewById<EditText>(R.id.senha_confirma).text.toString()

            // Verificar se todos os campos estão preenchidos
            if (nameUser.isBlank() || nameUser.isEmpty() || sobrenomeUser.isBlank() || sobrenomeUser.isEmpty() || enderecoUser.isBlank() || enderecoUser.isEmpty() || emailUser.isBlank() || emailUser.isEmpty() || passwordUser.isBlank() || passwordUser.isEmpty() || passwordConfirmaUser.isBlank() || passwordConfirmaUser.isEmpty()) {
                Toast.makeText(this@SingUpActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (passwordUser != passwordConfirmaUser) {
                Toast.makeText(this@SingUpActivity, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            } else {
                // Criar um objeto User com os dados do usuário
                val user = User(nameUser, sobrenomeUser, emailUser, passwordUser, enderecoUser, "")

                // Inserir o usuário no banco de dados em uma thread separada
                Thread {
                    AppDatabase.getInstance(applicationContext).userDao().insert(user)

                    // Atualizar a UI na thread principal
                    runOnUiThread {
                        Toast.makeText(this@SingUpActivity, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            }
        }

        // Voltar para a tela de login ao clicar no botão
        botaoRetornarLogin.setOnClickListener {
            finish()
        }
    }
}
