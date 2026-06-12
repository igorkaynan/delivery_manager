# Delivery Manager | Full Stack Order Management Platform

Sistema Full Stack para gerenciamento de pedidos, usuários, auditoria, monitoramento e mensageria, desenvolvido utilizando Java, Spring Boot, Oracle Database, MongoDB, RabbitMQ e Vue.js.

O projeto foi desenvolvido para demonstrar conhecimentos em arquitetura corporativa, integração entre bancos relacionais e NoSQL, autenticação JWT, mensageria assíncrona, monitoramento de aplicações e desenvolvimento de interfaces modernas.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Oracle](https://img.shields.io/badge/Oracle-Database-red)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-green)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Messaging-orange)
![Vue.js](https://img.shields.io/badge/Vue.js-Frontend-42b883)
![Docker](https://img.shields.io/badge/Docker-Containers-blue)

---

## 📋 Visão Geral

O Delivery Manager simula uma operação completa de gerenciamento de pedidos.

A aplicação permite:

* Autenticação de usuários com JWT
* Gestão de usuários
* Gestão de pedidos
* Controle de status dos pedidos
* Auditoria completa das ações do sistema
* Integração com RabbitMQ
* Persistência em Oracle Database
* Auditoria em MongoDB
* Dashboard gerencial
* Relatórios executivos
* Monitoramento em tempo real da aplicação

---

## 🏗 Arquitetura

```text
Frontend Vue.js
       │
       ▼
Spring Boot REST API
       │
 ┌─────┼─────┐
 ▼     ▼     ▼
Oracle Mongo RabbitMQ
```

---

## 🚀 Tecnologias Utilizadas

### Backend

* Java 21
* Spring Boot
* Spring MVC
* Spring Security
* JWT Authentication
* Spring Data JPA
* Hibernate
* JDBC
* Oracle Database XE
* MongoDB
* RabbitMQ
* Spring Boot Actuator

### Frontend

* Vue.js
* Vue Router
* Axios
* Bootstrap 5
* Chart.js

### Infraestrutura

* Docker
* Docker Compose
* Oracle XE
* MongoDB
* RabbitMQ Management

---

# 📸 Screenshots

## Dashboard

Visualização dos principais indicadores operacionais da aplicação.

* Total de usuários
* Total de pedidos
* Total retornado via Procedure PL/SQL
* Pedidos em processamento
* Gráfico de distribuição por status

<img width="1366" height="768" alt="dashboard" src="https://github.com/user-attachments/assets/5c33488f-b235-453b-9e83-9843d2fe74f2" />

---

## Gestão de Usuários

Tela responsável pelo gerenciamento dos usuários cadastrados no sistema.

Funcionalidades:

* Cadastro de usuários
* Listagem de usuários
* Exclusão de usuários
* Senhas criptografadas com BCrypt

<img width="1366" height="768" alt="usuarios" src="https://github.com/user-attachments/assets/4d016a97-bb44-404c-bb48-da578dba535f" />

---

## Gestão de Pedidos

Controle completo dos pedidos realizados.

Funcionalidades:

* Cadastro de pedidos
* Alteração de status
* Exclusão de pedidos
* Integração com RabbitMQ
* Controle operacional

Status disponíveis:

* CREATED
* PROCESSING
* COMPLETED
* CANCELED

<img width="1362" height="765" alt="pedidos" src="https://github.com/user-attachments/assets/676eb8e9-189d-419d-8ab8-083a40a49a84" />

---

## Auditoria

Todas as ações importantes do sistema são registradas no MongoDB.

Eventos auditados:

* Login
* Cadastro de usuário
* Exclusão de usuário
* Criação de pedido
* Alteração de status
* Exclusão de pedido
* Consumo RabbitMQ

<img width="1366" height="768" alt="auditoria" src="https://github.com/user-attachments/assets/bb9696c3-283f-43e4-86a3-303569c04ef5" />

---

## Monitoramento

Tela responsável por exibir a saúde da aplicação.

Monitoramentos disponíveis:

* Spring Boot API
* Oracle Database
* MongoDB
* RabbitMQ
* Espaço em disco
* Readiness
* Liveness

Dados obtidos através do Spring Boot Actuator.

<img width="1366" height="768" alt="monitoramento" src="https://github.com/user-attachments/assets/1737e67d-bd6e-40b8-87e8-b1dab2994986" />

---

## Relatórios Executivos

Painel consolidado de informações gerenciais.

Indicadores:

* Total de usuários
* Total de pedidos
* Total concluídos
* Total via PL/SQL
* Resumo por status

<img width="1366" height="768" alt="relatorios" src="https://github.com/user-attachments/assets/078909ea-8d46-47bc-aec6-88c2a6ffd3c3" />

---

# ⭐ Diferenciais Técnicos

* Arquitetura Full Stack completa
* Oracle Database + MongoDB
* RabbitMQ para processamento assíncrono
* Autenticação JWT
* Auditoria centralizada
* Relatórios com JDBC e PL/SQL
* Monitoramento com Spring Actuator
* Dashboard operacional
* Docker Compose para ambiente local

---

# 🔐 Autenticação

A aplicação utiliza JWT (JSON Web Token) para autenticação e autorização.

Fluxo:

1. Usuário realiza login
2. API gera token JWT
3. Frontend armazena o token
4. Requisições autenticadas utilizam Bearer Token
5. Rotas protegidas validam o token

---

# 📨 RabbitMQ

Ao criar um pedido:

1. Pedido é salvo no Oracle
2. Evento é enviado para RabbitMQ
3. Consumidor recebe a mensagem
4. Auditoria registra o processamento

---

# 🗄 Banco de Dados

## Oracle Database

Responsável por armazenar:

* Usuários
* Pedidos
* Relatórios

## MongoDB

Responsável por armazenar:

* Logs de auditoria
* Eventos do sistema

---

# ⚙ Como Executar

## Backend

```bash
cd ordermanager
docker compose up -d
mvn spring-boot:run
```

API:

```text
http://localhost:8080
```

## Frontend

```bash
cd ordermanager-frontend
npm install
npm run dev
```

Frontend:

```text
http://localhost:5173
```

---

# 📡 Principais Endpoints

### Login

```http
POST /auth/login
```

### Usuários

```http
GET /users
POST /users
DELETE /users/{id}
```

### Pedidos

```http
GET /orders
POST /orders
PATCH /orders/{id}/status
DELETE /orders/{id}
```

### Auditoria

```http
GET /audit-logs
```

### Relatórios

```http
GET /reports/summary
```

### Monitoramento

```http
GET /actuator/health
```

---

# ✅ Funcionalidades Implementadas

* JWT Authentication
* CRUD de Usuários
* CRUD de Pedidos
* Oracle Database
* MongoDB
* RabbitMQ
* Auditoria
* Dashboard
* Relatórios
* PL/SQL
* Monitoramento
* Docker
* Spring Actuator

---

# 👨‍💻 Autor

**Igor Kaynan**

Desenvolvedor Full Stack com foco em aplicações corporativas utilizando Java, Spring Boot, Oracle Database, MongoDB, RabbitMQ e Vue.js.

Projeto desenvolvido para demonstração de competências em arquitetura de software, integração de sistemas e desenvolvimento de aplicações escaláveis.
