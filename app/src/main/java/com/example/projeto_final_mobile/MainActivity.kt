package com.example.projeto_final_mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_final_mobile.Dao.UsuarioDAO
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.activities.Cadastro
import com.example.projeto_final_mobile.activities.MainScreen
import com.example.projeto_final_mobile.utils.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usuarioDAO = UsuarioDAO(DBHelper(this))

        val botaoEntrar = findViewById<Button>(R.id.botao_login)
        val botaoEsqueceuSenha = findViewById<Button>(R.id.botao_esqueceu_senha)
        val botaoCadastro = findViewById<Button>(R.id.botao_cadastro)
        val botaoLoginGoogle = findViewById<Button>(R.id.Login_google)

        botaoEntrar.setOnClickListener {
            val emailUser = findViewById<EditText>(R.id.email_login).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_login).text.toString()

            // Consulte o banco de dados para encontrar um usuário que corresponda ao email e senha fornecidos
            val usuario = usuarioDAO.getUsuarioByEmailAndSenha(emailUser, passwordUser)

            if (usuario != null) {
                // O usuário foi encontrado, permita o login

                // Salve o status de login no SharedPreferences
                val sharedPreferences = getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", true)
                editor.apply()

                // Inicie a nova atividade
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)

                // Destrua esta atividade
                finish()
            } else {
                // O usuário não foi encontrado, mostre uma mensagem de erro
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }

        botaoEsqueceuSenha.setOnClickListener {
            // lógica para recuperação de senha
        }

        // Redirecione para a tela de cadastro
        botaoCadastro.setOnClickListener {
            val intent = Intent(this, Cadastro::class.java)
            startActivity(intent)
        }

        botaoLoginGoogle.setOnClickListener {
            // lógica de login com o Google
        }
    }
}