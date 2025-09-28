# 📺 Sistema de Streaming de Vídeos

Projeto desenvolvido em **Java 17 + Spring Boot + Spring Data JPA + MySQL**, simulando um sistema de streaming de vídeos com usuários, perfis, categorias, vídeos, visualizações e avaliações.

---

## 📌 Funcionalidades
- Cadastro de **Usuários**, **Perfis**, **Categorias** e **Vídeos**.  
- Controle de **Visualizações** e **Avaliações**.  
- Queries personalizadas via **Spring Data JPA**:  
  - Buscar vídeos pelo título com ordenação.  
  - Listar todos os vídeos de uma categoria ordenados por título.  
  - Top 10 vídeos mais bem avaliados.  
  - Top 10 vídeos mais assistidos.  
  - Usuário que mais assistiu vídeos.  

---

## 🏗️ Tecnologias
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Hibernate**
- **Maven**

---

## 🗄️ Modelagem
O sistema foi modelado de acordo com o diagrama MER, resultando nas seguintes entidades:

- `Usuario`
- `Perfil`
- `Categoria`
- `Video`
- `Visualizacao`
- `Avaliacao`

📌 O **diagrama UML de classes** está disponível na pasta `docs/` como **PDF** e **JPG**.

---

## ⚙️ Configuração do Projeto

### Pré-requisitos
- Java 17+
- Maven 3.9+
- MySQL 8+

### Configuração do banco
Crie o banco no MySQL:

```sql
CREATE DATABASE streaming;
USE streaming;

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE perfil (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_perfil VARCHAR(255) NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_perfil_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE video (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT,
    duracao INT NOT NULL, -- em segundos
    categoria_id BIGINT NOT NULL,
    CONSTRAINT fk_video_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE
);

CREATE TABLE visualizacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    perfil_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    progresso INT NOT NULL, -- percentual ou minutos assistidos
    CONSTRAINT fk_visu_perfil FOREIGN KEY (perfil_id) REFERENCES perfil(id) ON DELETE CASCADE,
    CONSTRAINT fk_visu_video FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
);

CREATE TABLE avaliacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    perfil_id BIGINT NOT NULL,
    video_id BIGINT NOT NULL,
    nota INT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario TEXT,
    CONSTRAINT fk_avaliacao_perfil FOREIGN KEY (perfil_id) REFERENCES perfil(id) ON DELETE CASCADE,
    CONSTRAINT fk_avaliacao_video FOREIGN KEY (video_id) REFERENCES video(id) ON DELETE CASCADE
);