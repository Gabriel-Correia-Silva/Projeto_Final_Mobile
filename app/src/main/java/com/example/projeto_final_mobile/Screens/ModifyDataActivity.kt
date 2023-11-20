package com.projeto_final_mobile.Screens

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_final_mobile.R
import com.google.android.material.imageview.ShapeableImageView
import com.projeto_final_mobile.database.AppDatabase
import com.projeto_final_mobile.database.models.User
import com.projeto_final_mobile.utils.UserViewModel
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ModifyDataActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var imageButton: ImageButton
    private val PICK_IMAGE = 1
    private val TAKE_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_data)

        // Botão para retornar à tela principal
        val botaoRetornarMainScreen = findViewById<Button>(R.id.botao_retornar_main_screen)
        botaoRetornarMainScreen.setOnClickListener {
            val intent = Intent(this, com.projeto_final_mobile.Screens.MainScreen::class.java)
            startActivity(intent)
            finish()
        }

        // Botão para adicionar foto
        val imageButton: ShapeableImageView = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            val options = arrayOf<CharSequence>("Tirar foto", "Escolher da galeria", "Cancelar")

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Adicionar foto")

            builder.setItems(options) { dialog, item ->
                if (options[item] == "Tirar foto") {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, TAKE_PHOTO)
                } else if (options[item] == "Escolher da galeria") {
                    val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, PICK_IMAGE)
                } else if (options[item] == "Cancelar") {
                    dialog.dismiss()
                }
            }
            builder.show()
        }

        // Inicialização das views
        sharedPreferences = getSharedPreferences("com.example.projeto_final_mobile", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email", "")
        val nomeEditText = findViewById<EditText>(R.id.nome_alterar)
        val sobrenomeEditText = findViewById<EditText>(R.id.sobrenome_alterar)
        val enderecoEditText = findViewById<EditText>(R.id.endereco_alterar)
        val emailEditText = findViewById<EditText>(R.id.email_alterar)
        val senhaEditText = findViewById<EditText>(R.id.senha_alterar)

        // Inicialização do banco de dados
        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()

        // Observa as alterações do usuário
        val userLiveData = userDao.getUserByEmail(userEmail!!)
        userLiveData.observe(this, { user ->
            nomeEditText.setText(user?.nome)
            sobrenomeEditText.setText(user?.sobrenome)
            enderecoEditText.setText(user?.endereco)
            emailEditText.setText(user?.email)
            senhaEditText.setText(user?.senha)
            if (!user?.foto.isNullOrEmpty()) {
                imageButton.setImageURI(Uri.parse(user?.foto))
            }
        })

        // Botão para salvar as alterações
        val salvarButton = findViewById<Button>(R.id.botao_confimar_alteração)
        salvarButton.setOnClickListener {
            val username = nomeEditText.text.toString()
            val sobrenomeUser = sobrenomeEditText.text.toString()
            val enderecoUser = enderecoEditText.text.toString()
            val emailUser = emailEditText.text.toString()
            val passwordUser = senhaEditText.text.toString()
            val fotoUser = imageButton.tag?.toString() ?: ""

            val updatedUser = User(username, sobrenomeUser, emailUser, passwordUser, enderecoUser, fotoUser)

            // Atualiza o usuário no banco de dados
            GlobalScope.launch(Dispatchers.IO) {
                userDao.updateUser(updatedUser)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ModifyDataActivity, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ModifyDataActivity, com.projeto_final_mobile.Screens.MainScreen::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    // Salva a imagem no diretório de imagens
    private fun saveImage(bitmap: Bitmap): Uri {
        val filesDir = applicationContext.filesDir
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
        if (resultCode == RESULT_OK) {
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
        val destinationUri = Uri.fromFile(File(cacheDir, queryName(contentResolver, imageUri)))
        UCrop.of(imageUri!!, destinationUri)
            .withAspectRatio(1f, 1f)
            .start(this)
    }
}