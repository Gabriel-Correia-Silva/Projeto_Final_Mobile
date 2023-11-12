-- Tabela "Usuarios"
CREATE TABLE Usuarios (
    id INTEGER PRIMARY KEY,
    nameUser TEXT,
    sobrenomeUser TEXT,
    enderecoUser TEXT,
    emailUser TEXT,
    passwordUser TEXT,
    imagem BLOB
);

-- Tabela "Sugestoes" (tabela filha da tabela "Usuarios")
CREATE TABLE Sugestoes (
    id_sugestao INTEGER PRIMARY KEY,
    nome TEXT,
    endereco TEXT,
    texto TEXT,
    id_usuario INTEGER,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
);

-- Tabela "Marmitarias"
CREATE TABLE Marmitaria
s (
    id INTEGER PRIMARY KEY,
    endereco TEXT,
    precoMedio REAL
);

-- Tabela "Pratos" com relação a "Marmitarias"
CREATE TABLE Pratos (
    id INTEGER PRIMARY KEY,
    nome TEXT,
    ingredientes TEXT,
    imagem BLOB,
    id_Marmitaria
     INTEGER,
    FOREIGN KEY (id_Marmitaria
    ) REFERENCES Marmitaria
    s(id)
);

-- Tabela "Comentarios" (tabela filha da tabela "Usuarios")
CREATE TABLE Comentarios (
    id_comentario INTEGER PRIMARY KEY,
    texto TEXT,
    id_usuario INTEGER,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id)
);

-- Tabela "ComentariosPratos" para associar comentários aos pratos
CREATE TABLE ComentariosPratos (
    id_comentario_prato INTEGER PRIMARY KEY,
    id_prato INTEGER,
    id_comentario INTEGER,
    FOREIGN KEY (id_prato) REFERENCES Pratos(id),
    FOREIGN KEY (id_comentario) REFERENCES Comentarios(id_comentario)
);
