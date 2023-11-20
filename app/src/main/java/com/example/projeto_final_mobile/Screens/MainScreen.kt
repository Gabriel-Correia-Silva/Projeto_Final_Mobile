package com.projeto_final_mobile.Screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projeto_final_mobile.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.utils.DataSource
import com.projeto_final_mobile.utils.MapFragment
import com.projeto_final_mobile.utils.RecyclerViewFragment
import com.projeto_final_mobile.adapter.MarmitariaAdapter


class MainScreen : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var marmitariaAdapter: MarmitariaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // Configura o botão de alteração de dados do usuário
        val userDao = AppDatabase.getDatabase(this).userDao()
        val sharedPreferences = getSharedPreferences("com.example.projeto_final_mobile", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email", "")
        val userLiveData = userDao.getUserByEmail(userEmail!!)
        val imageButtonAlterarDados: ShapeableImageView = findViewById(R.id.ImageButtonAlterarDados)
        userLiveData.observe(this) { user ->
            val userPhotoPath = user?.foto
            if (userPhotoPath != null) {
                imageButtonAlterarDados.setImageURI(Uri.parse(userPhotoPath))
                Glide.with(this)
                    .load(userPhotoPath)
                    .into(imageButtonAlterarDados)
            }
        }
        imageButtonAlterarDados.setOnClickListener {
            val intent = Intent(this, com.projeto_final_mobile.Screens.ModifyDataActivity::class.java)
            startActivity(intent)
        }

        // Configura os botões de seleção de tela
        val botaoMapa = findViewById<Button>(R.id.showMap)
        val botaoLista = findViewById<Button>(R.id.showRecyclerView)
        botaoLista.isSelected = true

        botaoMapa.setOnClickListener {
            botaoMapa.isSelected = true
            botaoLista.isSelected = false
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewScreen, MapFragment())
                .commit()
        }

        botaoLista.setOnClickListener {
            botaoLista.isSelected = true
            botaoMapa.isSelected = false
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewScreen, RecyclerViewFragment())
                .commit()
        }

        // Chama as funções de inicialização do RecyclerView e adição de dados
        initRecyclerView()
        addDataSource()
    }

    // Função para adicionar os dados ao DataSource
    private fun addDataSource() {
        val dataSource = DataSource.createDataSet()
    }

    // Função para inicializar o RecyclerView
    private fun initRecyclerView() {
        marmitariaAdapter = MarmitariaAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewListfragment)
        recyclerView.layoutManager = LinearLayoutManager(this@MainScreen)
        recyclerView.adapter = marmitariaAdapter
    }
}
