# 🚗 Sistema de Aluguel de Carros 👨‍💻

> [!NOTE]
> Sistema web para gestão de aluguéis de automóveis: clientes criam e acompanham pedidos de aluguel pela Internet, e agentes (empresas e bancos) analisam e aprovam esses pedidos.

<table>
  <tr>
    <td width="800px">
      <div align="justify">
        O <b>Sistema de Aluguel de Carros</b> permite <i>efetuar</i>, <i>modificar</i>, <i>consultar</i> e <i>cancelar</i> pedidos de aluguel de automóveis pela Internet. Após serem introduzidos pelos clientes, os pedidos são <b>analisados do ponto de vista financeiro</b> pelos agentes (empresas e bancos) e, em caso de parecer positivo, seguem para a <b>execução do contrato</b>, que pode estar associado a um <i>contrato de crédito</i> concedido por um banco agente. O acesso é restrito a usuários previamente cadastrados e autenticados.
      </div>
    </td>
    <td>
      <div>
        <img src="docs/images/logo.svg" alt="Logo do Projeto" width="120px"/>
      </div>
    </td>
  </tr>
</table>

---

## 🚧 Status do Projeto

![Status](https://img.shields.io/badge/Status-Em_desenvolvimento-yellow?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-25-007ec6?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.x-007ec6?style=for-the-badge&logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-007ec6?style=for-the-badge&logo=apachemaven&logoColor=white)
![React](https://img.shields.io/badge/React-007ec6?style=for-the-badge&logo=react&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-007ec6?style=for-the-badge&logo=vite&logoColor=white)
![Supabase](https://img.shields.io/badge/Supabase-PostgreSQL-007ec6?style=for-the-badge&logo=supabase&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/FelipeFonseca2/DIAW-Lab02?style=for-the-badge&logo=clockify)

---

## 📚 Índice
- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura](#-arquitetura)
  - [Modelagem (UML)](#modelagem-uml)
- [Instalação e Execução](#-instalação-e-execução)
  - [Pré-requisitos](#pré-requisitos)
  - [Variáveis de Ambiente](#-variáveis-de-ambiente)
  - [Instalação de Dependências](#-instalação-de-dependências)
  - [Como Executar a Aplicação](#-como-executar-a-aplicação)
- [Estrutura de Pastas](#-estrutura-de-pastas)
- [Autores](#-autores)

---

## 📝 Sobre o Projeto

Projeto desenvolvido no **Laboratório de Desenvolvimento de Software (Lab02)** da PUC Minas, com o objetivo de construir um sistema de apoio à gestão de aluguéis de automóveis.

O sistema envolve dois tipos de usuários:

- **Clientes (usuários individuais):** introduzem, modificam, consultam e cancelam pedidos de aluguel.
- **Agentes (empresas e bancos):** modificam e avaliam pedidos, emitindo parecer financeiro. Pedidos com parecer positivo seguem para a execução do contrato.

Dependendo do tipo de contrato, os automóveis alugados podem ser registrados como propriedade dos **clientes**, **empresas** ou **bancos**, e o aluguel pode estar associado a um **contrato de crédito** concedido por um dos bancos agentes.

O projeto acompanha a modelagem UML (casos de uso, histórias de usuário, classes e pacotes), que é revisada ao longo do desenvolvimento para manter o alinhamento entre modelo e código.

---

## ✨ Funcionalidades Principais

- 🔐 **Autenticação:** cadastro prévio e login obrigatório (usuário e senha) para clientes e agentes.
- 👤 **CRUD de Cliente:** dados de identificação (RG, CPF, nome, endereço), profissão, entidades empregadoras e rendimentos auferidos (máximo 3).
- 📝 **Pedidos de Aluguel:** clientes criam, modificam, consultam, cancelam e acompanham o status dos seus pedidos.
- 🏦 **Avaliação de Pedidos:** agentes modificam e analisam financeiramente os pedidos, emitindo parecer.
- 📄 **Contratos:** execução do contrato após parecer positivo, com possível associação a um contrato de crédito bancário.
- 🚘 **Automóveis:** registro de matrícula, ano, marca, modelo e placa, com propriedade de cliente, empresa ou banco.

---

## 🛠 Tecnologias Utilizadas

### 💻 Front-end

* **Biblioteca:** React
* **Build Tool:** Vite

### 🖥️ Back-end

* **Linguagem:** Java (JDK 25)
* **Framework:** Spring Boot 4.0.x
* **Build e dependências:** Apache Maven
* **Banco de Dados:** Supabase (PostgreSQL)
* **ORM:** Spring Data JPA / Hibernate
* **Autenticação:** login com usuário e senha

---

## 🏗 Arquitetura

O sistema segue o modelo **cliente-servidor**: um servidor central conectado, via Internet, aos computadores locais dos clientes e dos agentes. Ele é dividido em dois subsistemas:

- **Gestão de pedidos e contratos** — back-end em **Spring Boot**, organizado em arquitetura **MVC**:
  - **Model:** entidades de domínio (Cliente, Agente, Pedido, Contrato, Automóvel etc.) persistidas no Supabase via JPA.
  - **Controller:** endpoints REST que recebem as requisições do front-end.
  - **Service / Repository:** regras de negócio e acesso a dados.
- **Construção dinâmica das páginas web** — front-end em **React + Vite**, responsável pela camada de visualização (View) e pelo consumo da API REST.

**Fluxo de dados:** `React (View)` → `Controller` → `Service` → `Repository` → `Supabase (PostgreSQL)`.

### Modelagem (UML)

Os modelos do sistema ficam versionados em [`docs/`](docs/) e são atualizados conforme a evolução do projeto:

- Diagrama de Casos de Uso
- Histórias de Usuário
- Diagrama de Classes
- Diagrama de Pacotes (Visão Lógica)

---

## 🔧 Instalação e Execução

### Pré-requisitos

* **Java JDK:** versão **25**
* **Apache Maven**
* **Node.js:** versão LTS e **npm**
* **Projeto no Supabase** com banco PostgreSQL

---

### 🔑 Variáveis de Ambiente

#### 1 Back-end (Spring Boot)

Configure como variáveis de ambiente do sistema ou em `backend/src/main/resources/application.properties`.

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | URL JDBC do banco no Supabase. | `jdbc:postgresql://db.<project-ref>.supabase.co:5432/postgres` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco. | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco. | `sua-senha` |

#### 2 Front-end (React, Vite)

Crie um arquivo **`.env`** na pasta `frontend`:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `VITE_API_URL` | URL base da API Spring Boot. | `http://localhost:8080/api` |

---

### 📦 Instalação de Dependências

```bash
git clone https://github.com/FelipeFonseca2/DIAW-Lab02.git
cd DIAW-Lab02
```

#### Front-end (React)

```bash
cd frontend
npm install
```

#### Back-end (Spring Boot)

```bash
cd backend
mvn clean install
```

---

### ⚡ Como Executar a Aplicação

Execute a aplicação em **dois terminais separados**.

#### Terminal 1: Back-end (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```
🚀 *O Back-end estará disponível em **http://localhost:8080**.*

#### Terminal 2: Front-end (React, Vite)

```bash
cd frontend
npm run dev
```
🎨 *O Front-end estará disponível em **http://localhost:5173**.*

---

## 📂 Estrutura de Pastas

```
.
├── README.md                    # 📘 Documentação principal do projeto.
├── /docs                        # 📚 Modelos UML (casos de uso, histórias, classes, pacotes) e imagens.
│
├── /frontend                    # 📁 Aplicação React + Vite
│   ├── /src
│   │   ├── /components          # 🧱 Componentes reutilizáveis.
│   │   ├── /pages               # 📄 Páginas (login, pedidos, clientes...).
│   │   └── /services            # 🔌 Chamadas HTTP à API.
│   └── package.json
│
└── /backend                     # 📁 Aplicação Spring Boot (MVC)
    ├── /src/main/java
    │   └── /.../aluguel
    │       ├── /controller      # 🎮 Endpoints REST.
    │       ├── /service         # ⚙️ Regras de negócio.
    │       ├── /repository      # 🗄️ Repositórios JPA.
    │       ├── /model           # 🧬 Entidades.
    │       └── /dto             # ✉️ Data Transfer Objects.
    ├── /src/main/resources      # ⚙️ application.properties
    └── pom.xml                  # 🛠️ Build e dependências (Maven).
```

---

## 👥 Autores

| 👤 Nome | :octocat: GitHub |
|---------|-----------------|
| Felipe Fonseca | <div align="center"><a href="https://github.com/FelipeFonseca2"><img src="https://joaopauloaramuni.github.io/image/github6.png" width="50px" height="50px"></a></div> |
