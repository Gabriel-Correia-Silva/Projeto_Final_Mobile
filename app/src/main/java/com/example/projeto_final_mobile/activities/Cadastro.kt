package com.example.projeto_final_mobile.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.models.Usuario
import com.example.projeto_final_mobile.Dao.UsuarioDAO
import com.example.projeto_final_mobile.MainActivity
import com.example.projeto_final_mobile.utils.DBHelper

class Cadastro : AppCompatActivity() {

    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        usuarioDAO = UsuarioDAO(DBHelper(this))

        val botaoRetornarLogin = findViewById<Button>(R.id.botao_retornar)
        val botaoCadastrar = findViewById<Button>(R.id.botao_cadastrar)

        botaoCadastrar.setOnClickListener {

            val username = findViewById<EditText>(R.id.nome_cadastro).text.toString()
            val sobrenomeUser = findViewById<EditText>(R.id.sobrenome_cadastro).text.toString()
            val enderecoUser = findViewById<EditText>(R.id.endereco_cadastro).text.toString()
            val emailUser = findViewById<EditText>(R.id.email_cadastro).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_cadastro).text.toString()
            val passwordUserConfirma = findViewById<EditText>(R.id.senha_confirma).text.toString()

            if (passwordUser == passwordUserConfirma) {
                // Inserir usuário no banco de dados
                val usuario = Usuario(0, username, sobrenomeUser, enderecoUser, emailUser, passwordUser, "")
                val resultado = usuarioDAO.inserirUsuario(usuario)

                if (resultado != -1L) {
                    Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao inserir usuário", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            }
        }

        botaoRetornarLogin.setOnClickListener {
            finish()
        }
    }
}

class DatabaseHelperUsuario {

}
