package com.example.projeto_final_mobile.Screens

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_final_mobile.R



class ModifyDataActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_data)

        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val nomeUsuario = sharedPreferences.getString("username", "")
        val sobrenomeUsuario = sharedPreferences.getString("sobrenomeUser", "")
        val enderecoUsuario = sharedPreferences.getString("enderecoUser", "")
        val emailUsuario = sharedPreferences.getString("emailUser", "")
        val senhaUsuario = sharedPreferences.getString("passwordUser", "")

        val nomeEditText = findViewById<EditText>(R.id.nome_alterar)
        val sobrenomeEditText = findViewById<EditText>(R.id.sobrenome_alterar)
        val enderecoEditText = findViewById<EditText>(R.id.endereco_alterar)
        val emailEditText = findViewById<EditText>(R.id.email_alterar)
        val senhaEditText = findViewById<EditText>(R.id.senha_alterar)

        nomeEditText.setText(nomeUsuario)
        sobrenomeEditText.setText(sobrenomeUsuario)
        enderecoEditText.setText(enderecoUsuario)
        emailEditText.setText(emailUsuario)
        senhaEditText.setText(senhaUsuario)

        val salvarButton = findViewById<Button>(R.id.botao_confimar_alteração)
        salvarButton.setOnClickListener {
            val username = nomeEditText.text.toString()
            val sobrenomeUser = sobrenomeEditText.text.toString()
            val enderecoUser = enderecoEditText.text.toString() 
            val emailUser = emailEditText.text.toString()
            val passwordUser = senhaEditText.text.toString()

            val editor = sharedPreferences.edit()
            editor.putString("username", username)
            editor.putString("sobrenomeUser", sobrenomeUser)
            editor.putString("enderecoUser", enderecoUser)
            editor.putString("emailUser", emailUser)
            editor.putString("passwordUser", passwordUser)
            editor.apply()

            Toast.makeText(this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}