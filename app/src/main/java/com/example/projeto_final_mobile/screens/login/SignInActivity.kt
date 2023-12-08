package com.example.projeto_final_mobile.screens.login

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projeto_final_mobile.R
import com.example.projeto_final_mobile.database.AuthRepository
import com.example.projeto_final_mobile.database.viewmodel.MarmitaViewModel
import com.example.projeto_final_mobile.database.viewmodel.MarmitariaViewModel
import com.example.projeto_final_mobile.database.viewmodel.UserViewModel
import com.example.projeto_final_mobile.screens.drawer.NavegationDrawerActivity
import com.example.projeto_final_mobile.screens.cadastro.SingUp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.models.Marmita
import com.projeto_final_mobile.database.models.Marmitaria
import com.projeto_final_mobile.database.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var authRepository: AuthRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)

        val sharedPreferences = this.getSharedPreferences("com.example.projeto_final_mobile", Context.MODE_PRIVATE)
        val isDataInsertedMarmitaria = sharedPreferences.getBoolean("isDataInsertedMarmitaria", false)
        val isDataInsertedMarmita = sharedPreferences.getBoolean("isDataInsertedMarmita", false)
        val marmitariaViewModel = ViewModelProvider(this).get(MarmitariaViewModel::class.java)
        val marmitaViewModel = ViewModelProvider(this).get(MarmitaViewModel::class.java)
        val firestore = FirebaseFirestore.getInstance()
        val marmitasCollection = firestore.collection("marmitas")
        val marmitariasCollection = firestore.collection("marmitarias")

            //___________________________________________________________//
            // inserir dados no banco de dados firebase marmitarias



        marmitariasCollection
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            for (doc in snapshots!!) {
                val marmitaria = doc.toObject(Marmitaria::class.java)
                marmitariaViewModel.insert(marmitaria);

            }
        }





    //___________________________________________________________//
            // inserir dados no banco de dados firebase marmitas
        Log.d("isDataInsertedMarmita", "antes de if")



        Log.d("isDataInsertedMarmita", "dentro de if")
        marmitasCollection
        .addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w(ContentValues.TAG, "listen:error", e)
                return@addSnapshotListener
            }

            for (doc in snapshots!!) {
                val marmita = doc.toObject(Marmita::class.java)
                marmitaViewModel.viewModelScope.launch {
                    marmitaViewModel.insertMarmita(marmita = marmita)
                }
            }
        }


        Log.d("isDataInsertedMarmita", "depois de if")
        //_____________________________________________________________


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        val botaologingoogle = findViewById<Button>(R.id.Login_google)

        if (sharedPreferences.getBoolean("user_logged_in", false)) {
            val userEmail = sharedPreferences.getString("email", "")
            Log.d("UserStatus", "Usuário logado: $userEmail")
            val intent = Intent(this, NavegationDrawerActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userDao = AppDatabase.getInstance(applicationContext).userDao()
        authRepository = AuthRepository(userDao)

        val botaoLogar = findViewById<Button>(R.id.botao_login)
        val botaoCadastro = findViewById<Button>(R.id.botao_cadastro)

        botaoLogar.setOnClickListener {
            val emailUser = findViewById<EditText>(R.id.email_login).text.toString()
            val passwordUser = findViewById<EditText>(R.id.senha_login).text.toString()
        
            if (emailUser.isBlank() || passwordUser.isBlank()) {
                Toast.makeText(this@SignInActivity, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    authRepository.loginUser(emailUser, passwordUser) { success ->
                        if (success) {
                            Toast.makeText(this@SignInActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                
                            // Recupere os dados do Firebase
                            val firebaseUser = FirebaseAuth.getInstance().currentUser
                
                            userDao.getUserByEmail(emailUser).observe(this@SignInActivity, Observer { user ->
                                var newUser: User? = null
                                if (user == null || user.email != firebaseUser?.email || user.nome != firebaseUser?.displayName) {
                                    // Se os dados forem diferentes, atualize o banco de dados local com os novos dados
                                    newUser = User(
                                        email = firebaseUser?.email ?: "",
                                        nome = firebaseUser?.displayName ?: "",
                                        sobrenome = "",
                                        senha = "",
                                        endereco = "",
                                        foto = ""
                                        // preencha os outros campos conforme necessário
                                    )
                            
                                    // Salve o usuário no banco de dados
                                    CoroutineScope(Dispatchers.IO).launch {
                                        userDao.insert(newUser)
                                    }
                                }
                            
                                userViewModel.setAuthenticatedUser(user ?: newUser!!)
                            
                                val editor = sharedPreferences.edit()
                                editor.putBoolean("user_logged_in", true)
                                editor.putString("nome", user?.nome ?: "")
                                editor.putString("email", user?.email ?: "")
                                editor.apply()
                            
                                val intent = Intent(this@SignInActivity, NavegationDrawerActivity::class.java)
                                startActivity(intent)
                                finish()
                            })
                            
                        } else {
                            Toast.makeText(this@SignInActivity, "Credenciais incorretas. Tente novamente.", Toast.LENGTH_SHORT).show()
                        }
                    }}
                }
        }
        botaoCadastro.setOnClickListener {
            val intent = Intent(this, SingUp::class.java)
            startActivity(intent)
        }
    }
}