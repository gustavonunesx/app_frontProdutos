# 📦 Sistema de Gerenciamento de Produtos e Categorias

**Desenvolvido por:** Gustavo Nunes

Sistema web completo para gerenciamento de produtos e categorias, desenvolvido com Java Spark Framework no backend e React no frontend.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Pré-requisitos](#pré-requisitos)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Executando o Backend](#executando-o-backend)
- [Executando o Frontend](#executando-o-frontend)
- [Funcionalidades](#funcionalidades)
- [Endpoints da API](#endpoints-da-api)

---

## 🎯 Sobre o Projeto

Sistema CRUD (Create, Read, Update, Delete) desenvolvido para gerenciar produtos e suas categorias. O projeto é dividido em duas partes:

- **Backend:** API RESTful desenvolvida com Java Spark Framework
- **Frontend:** Interface web desenvolvida com React

---

## 🚀 Tecnologias Utilizadas

### Backend
- Java 8+
- Spark Framework 2.9.4
- MySQL 8.0
- JDBC (MySQL Connector 9.4.0)
- Gson 2.13.1
- SLF4J 2.0.17

### Frontend
- React 18
- React Router DOM
- CSS3
- Fetch API

---

## ✅ Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- **Java JDK 8 ou superior** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **MySQL 8.0 ou superior** - [Download](https://dev.mysql.com/downloads/mysql/)
- **Node.js 14 ou superior** - [Download](https://nodejs.org/)
- **npm** (geralmente vem com o Node.js)
- **Git** - [Download](https://git-scm.com/)

### Verificando as instalações:

```bash
# Verificar Java
java -version

# Verificar MySQL
mysql --version

# Verificar Node.js
node --version

# Verificar npm
npm --version
```

---

## 📁 Estrutura do Projeto

```
app_frontProdutos/
│
├── backend/
│   ├── .idea/
│   ├── .vscode/
│   ├── bin/
│   ├── lib/                          # Bibliotecas JAR do projeto
│   │   ├── gson-2.13.1.jar
│   │   ├── mysql-connector-j-9.4.0.jar
│   │   ├── spark-core-2.9.4.jar
│   │   └── ... (outras dependências)
│   │
│   └── src/
│       ├── api/
│       │   └── ApiProduto.java      # Classe principal com rotas da API
│       ├── dao/
│       │   ├── CategoriaDAO.java    # Data Access Object de Categoria
│       │   └── ProdutoDAO.java      # Data Access Object de Produto
│       ├── model/
│       │   ├── Categoria.java       # Modelo de Categoria
│       │   └── Produto.java         # Modelo de Produto
│       ├── util/
│       │   └── ConnectionFactory.java  # Gerenciador de conexão com BD
│       │
│       ├── aulajdbc.sql             # Script de criação do banco
│       └── README.md
│
└── frontend/
    ├── public/
    ├── src/
    │   ├── components/
    │   │   ├── Header.js
    │   │   └── Sidebar.js
    │   ├── pages/
    │   │   ├── categorias/
    │   │   │   ├── CategoriaList.jsx
    │   │   │   └── CategoriaForm.jsx
    │   │   └── produtos/
    │   │       ├── ProdutoList.jsx
    │   │       └── ProdutoForm.jsx
    │   ├── services/
    │   │   └── api.js               # Configuração das requisições HTTP
    │   ├── App.js
    │   ├── App.css
    │   └── index.js
    │
    ├── package.json
    └── README.md
```

---

## 🗄️ Configuração do Banco de Dados

### Passo 1: Iniciar o MySQL

Certifique-se de que o serviço MySQL está rodando:

**Windows:**
```bash
# Verifique no Gerenciador de Tarefas ou inicie pelo MySQL Workbench
```

**Linux/Mac:**
```bash
sudo systemctl start mysql
# ou
sudo service mysql start
```

### Passo 2: Criar o Banco de Dados

1. Acesse o MySQL pelo terminal:

```bash
mysql -u root -p
```

2. Digite sua senha quando solicitado (padrão: `123456`)

3. Execute os seguintes comandos SQL:

```sql
CREATE DATABASE IF NOT EXISTS aulajdbc;
USE aulajdbc;

CREATE TABLE categoria (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE produtos (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    categoria_id INT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

INSERT INTO categoria (nome)
VALUES ('Eletrônicos'), ('Periféricos'), ('Informática');

INSERT INTO produtos (nome, preco, estoque, categoria_id)
VALUES ('SmartPhone', 1500.00, 25, 1);
```

**OU** execute o script SQL fornecido:

```bash
mysql -u root -p < backend/src/aulajdbc.sql
```

### Passo 3: Configurar Credenciais do Banco

Se suas credenciais do MySQL forem diferentes, edite o arquivo:

**`backend/src/util/ConnectionFactory.java`**

```java
private static final String URL = "jdbc:mysql://localhost:3306/aulajdbc";
private static final String USER = "root";           // ← Seu usuário MySQL
private static final String PASS = "123456";         // ← Sua senha MySQL
```

---

## ⚙️ Executando o Backend

### Passo 1: Navegar até a pasta do backend

```bash
cd app_frontProdutos/backend
```

### Passo 2: Compilar o projeto

**Opção A: Compilar todos os arquivos de uma vez**

```bash
javac -cp "lib/*" -d bin src/model/*.java src/util/*.java src/dao/*.java src/api/*.java
```

**Explicação do comando:**
- `-cp "lib/*"` → Adiciona todas as bibliotecas JAR ao classpath
- `-d bin` → Coloca os arquivos compilados (.class) na pasta `bin`
- `src/...` → Arquivos fonte a serem compilados

### Passo 3: Executar a aplicação

```bash
java -cp "bin;lib/*" api.ApiProduto
```

**No Linux/Mac, use `:` ao invés de `;`:**
```bash
java -cp "bin:lib/*" api.ApiProduto
```

### ✅ Verificar se está funcionando

Você verá no terminal:

```
🚀 Servidor rodando na porta 4567
📍 API disponível em http://localhost:4567
```

Teste acessando no navegador:
- http://localhost:4567/produtos
- http://localhost:4567/categorias

Você deve ver dados em formato JSON!

### 🛑 Para parar o servidor

Pressione `Ctrl + C` no terminal.

---

## 💻 Executando o Frontend

### Passo 1: Abrir um NOVO terminal

⚠️ **IMPORTANTE:** Deixe o terminal do backend rodando! Abra um novo terminal para o frontend.

### Passo 2: Navegar até a pasta do frontend

```bash
cd app_frontProdutos/frontend
```

### Passo 3: Instalar as dependências

**Na primeira vez que executar o projeto:**

```bash
npm install
```

Este comando vai:
- Ler o arquivo `package.json`
- Baixar todas as dependências necessárias
- Criar a pasta `node_modules`

### Passo 4: Iniciar a aplicação

```bash
npm start
```

### ✅ Acessar a aplicação

O navegador deve abrir automaticamente em:

**http://localhost:3000**

Se não abrir automaticamente, digite o endereço acima no navegador.

### 🛑 Para parar o servidor

Pressione `Ctrl + C` no terminal do frontend.

---

## 📱 Funcionalidades

### Categorias
- ✅ Listar todas as categorias
- ✅ Cadastrar nova categoria
- ✅ Editar categoria existente
- ✅ Excluir categoria

### Produtos
- ✅ Listar todos os produtos
- ✅ Cadastrar novo produto
- ✅ Editar produto existente
- ✅ Excluir produto
- ✅ Vincular produto a uma categoria

---

## 🔌 Endpoints da API

### Base URL
```
http://localhost:4567
```

### Produtos

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/produtos` | Lista todos os produtos |
| GET | `/produtos/:id` | Busca produto por ID |
| POST | `/produtos` | Cria novo produto |
| PUT | `/produtos/:id` | Atualiza produto |
| DELETE | `/produtos/:id` | Deleta produto |

### Categorias

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/categorias` | Lista todas as categorias |
| GET | `/categorias/:id` | Busca categoria por ID |
| POST | `/categorias` | Cria nova categoria |
| PUT | `/categorias/:id` | Atualiza categoria |
| DELETE | `/categorias/:id` | Deleta categoria |

### Exemplos de Requisição

**Criar Produto:**
```json
POST /produtos
Content-Type: application/json

{
  "nome": "Mouse Gamer",
  "preco": 150.00,
  "estoque": 50,
  "categoria_id": 2
}
```

**Criar Categoria:**
```json
POST /categorias
Content-Type: application/json

{
  "nome": "Acessórios"
}
```

---


## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

---

**Desenvolvido por Gustavo Nunes**