package com.example.projeto_final_mobile.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "DataBaseMarmitaria.db"
        const val DATABASE_VERSION = 1
    }

    // Tabela Usuarios
    private val CREATE_TABLE_USUARIOS = """
        CREATE TABLE Usuarios (
            id INTEGER PRIMARY KEY,
            nameUser TEXT,
            sobrenomeUser TEXT,
            enderecoUser TEXT,
            emailUser TEXT,
            passwordUser TEXT,
            imagem BLOB
        );
    """.trimIndent()

    // Tabela Sugestoes
    private val CREATE_TABLE_SUGESTOES = """
        CREATE TABLE Sugestoes (
            id_sugestao INTEGER PRIMARY KEY,
            nome TEXT,
            endereco TEXT,
            texto TEXT,
            id_usuario INTEGER,
            FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
        );
    """.trimIndent()

    // Tabela Marmitarias
    private val CREATE_TABLE_MARMITARIAS = """
        CREATE TABLE Marmitarias (
            id INTEGER PRIMARY KEY,
            endereco TEXT,
            precoMedio REAL
        );
    """.trimIndent()

    // Tabela Pratos
    private val CREATE_TABLE_PRATOS = """
        CREATE TABLE Pratos (
            id INTEGER PRIMARY KEY,
            nome TEXT,
            ingredientes TEXT,
            imagem BLOB,
            id_Marmitaria INTEGER,
            FOREIGN KEY (id_Marmitaria) REFERENCES Marmitarias(id)
        );
    """.trimIndent()

    // Tabela Comentarios
    private val CREATE_TABLE_COMENTARIOS = """
        CREATE TABLE Comentarios (
            id_comentario INTEGER PRIMARY KEY,
            texto TEXT,
            id_usuario INTEGER,
            FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
        );
    """.trimIndent()

    // Tabela ComentariosPratos
    private val CREATE_TABLE_COMENTARIOS_PRATOS = """
        CREATE TABLE ComentariosPratos (
            id_comentario_prato INTEGER PRIMARY KEY,
            id_prato INTEGER,
            id_comentario INTEGER,
            FOREIGN KEY (id_prato) REFERENCES Pratos(id),
            FOREIGN KEY (id_comentario) REFERENCES Comentarios(id_comentario)
        );
    """.trimIndent()

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USUARIOS)
        db?.execSQL(CREATE_TABLE_SUGESTOES)
        db?.execSQL(CREATE_TABLE_MARMITARIAS)
        db?.execSQL(CREATE_TABLE_PRATOS)
        db?.execSQL(CREATE_TABLE_COMENTARIOS)
        db?.execSQL(CREATE_TABLE_COMENTARIOS_PRATOS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Caso você precise atualizar o esquema do banco de dados
    }
}
