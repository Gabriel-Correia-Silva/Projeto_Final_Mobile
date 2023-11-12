package com.example.projeto_final_mobile.activities

import com.example.projeto_final_mobile.fragments.RecyclerViewFragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_final_mobile.Dao.MarmitariaDAO
import com.example.projeto_final_mobile.adapter.MarmitariaAdapterAdapter
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.R.id.drawer_layout
import com.example.projeto_final_mobile.R.id.nav_view
import com.example.projeto_final_mobile.fragments.MapFragment
import com.example.projeto_final_mobile.utils.DBHelper
import com.google.android.material.navigation.NavigationView



class MainScreen : AppCompatActivity() {

    private lateinit var adapter: MarmitariaAdapterAdapter

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_gaveta)

        drawerLayout = findViewById(drawer_layout)
        navView = findViewById(nav_view)

        // Configura o botão de abertura do menu lateral
        val buttonDrawer = findViewById<Button>(R.id.buttonDrawer)
        buttonDrawer?.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Configura o menu lateral
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Verifica se já está na tela inicial
                    if (getCallingActivity()?.className != MainScreen::class.java.name && this::class.java.name != MainScreen::class.java.name) {
                        val intent = Intent(this, MainScreen::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Você já está na tela inicial", Toast.LENGTH_SHORT).show()
                    }
                }
                R.id.nav_new_item -> {
                    // TODO: Implementar a página de sugestão
                }
            }
            true
        }

        // Configura o botão de alteração de dados do usuário
        val myImageButton = findViewById<ImageButton>(R.id.ImageButtonAlterarDados)
        myImageButton.setOnClickListener {
            val intent = Intent(this, AlterarDados::class.java)
            startActivity(intent)
        }

        // Configura o RecyclerView
        val dbHelper = DBHelper(this)
        val marmitariaDAO = MarmitariaDAO(dbHelper)
        val marmitarias = marmitariaDAO.obterMarmitarias()

        val marmitariaAdapterAdapter = MarmitariaAdapterAdapter(marmitarias)
        val meuRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewListfragment)
        meuRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainScreen)
            adapter = marmitariaAdapterAdapter
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
        

    }
}
