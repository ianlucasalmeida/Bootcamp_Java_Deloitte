# Peça Já — API de Gestão de Autopeças

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Architecture](https://img.shields.io/badge/Architecture-SOLID-blue)
![Status](https://img.shields.io/badge/status-active-success)
![JUnit](https://img.shields.io/badge/Tests-JUnit5-blue)
![Mockito](https://img.shields.io/badge/Tests-Mockito-green)

API REST desenvolvida em **Java com Spring Boot** para simular o backend de um sistema de **gestão de autopeças**.  
A aplicação permite o gerenciamento de **clientes, catálogo de produtos e vendas**, aplicando princípios de engenharia de software voltados à **modularidade, extensibilidade e organização arquitetural**.

O projeto foi estruturado aplicando conceitos de **SOLID**, com foco nos princípios:

- **SRP (Single Responsibility Principle)**
- **OCP (Open Closed Principle)**

---

# Sumário

- Visão Geral
- Arquitetura
- Estrutura do Projeto
- Princípios SOLID Aplicados
- Estratégia de Branches
- Tecnologias Utilizadas
- Como Executar
- Endpoints

---

# Visão Geral

O **Peça Já** representa a camada de backend de um sistema de vendas de autopeças.

A API permite:

- cadastro e gerenciamento de clientes
- consulta de catálogo de produtos
- registro de vendas
- validação de dados críticos

As validações foram implementadas de forma modular, permitindo que novas regras possam ser adicionadas sem modificar código existente.

---

# Arquitetura

A aplicação segue uma arquitetura em camadas típica de aplicações **Spring Boot**:

```
Controller → Service → Validation → Repository → Database
```

### Controller

Responsável pela exposição dos endpoints REST.

```
CatalogoController
ClienteController
VendaController
```

---

### Service

Camada responsável pela lógica de negócio da aplicação.

```
ClienteService
```

Coordena:

- execução de validações
- regras de negócio
- persistência de dados

---

### Repository

Camada responsável pela comunicação com o banco de dados utilizando **Spring Data JPA**.

```
ClienteRepository
ProdutoRepository
VendaRepository
```

---

### Validation

Módulo responsável pelas validações aplicadas à entidade **Cliente**.

Cada validação foi separada em uma classe específica.

```
CpfValidation
EmailValidation
EmailUnicoValidation
NomeValidation
TelefoneValidation
```

Todas implementam a interface:

```
ClienteValidation
```

---

# Estrutura do Projeto

```text
Bootcamp_Java_Deloitte
│
├── pecaja-api
│   ├── pom.xml
│   ├── mvnw
│   ├── src
│   │
│   │   ├── main/java/com/deloitte/pecaja/api
│   │   │
│   │   │   ├── controller
│   │   │   │   ├── CatalogoController.java
│   │   │   │   ├── ClienteController.java
│   │   │   │   └── VendaController.java
│   │   │   │
│   │   │   ├── email
│   │   │   │   └── EmailService.java
│   │   │   │
│   │   │   ├── model
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Peca.java
│   │   │   │   ├── Produto.java
│   │   │   │   ├── Servico.java
│   │   │   │   └── Venda.java
│   │   │   │
│   │   │   ├── repository
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── ProdutoRepository.java
│   │   │   │   └── VendaRepository.java
│   │   │   │
│   │   │   ├── service
│   │   │   │   └── ClienteService.java
│   │   │   │
│   │   │   └── validation
│   │   │       ├── ClienteValidation.java
│   │   │       ├── CpfValidation.java
│   │   │       ├── EmailUnicoValidation.java
│   │   │       ├── EmailValidation.java
│   │   │       ├── NomeValidation.java
│   │   │       └── TelefoneValidation.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test/java/com/deloitte/pecaja/api
│       └── PecajaApiApplicationTests.java
```

---

# Princípios SOLID Aplicados

## SRP — Single Responsibility Principle

Cada classe possui uma única responsabilidade.

| Classe | Responsabilidade |
|------|------|
CpfValidation | validação de CPF |
EmailValidation | validação de formato de email |
EmailUnicoValidation | verificação de unicidade do email |
NomeValidation | validação de nome |
TelefoneValidation | validação de telefone |

Essa separação torna o sistema:

- mais modular
- mais testável
- mais fácil de manter

---

## OCP — Open Closed Principle

O sistema foi projetado para permitir **extensão sem modificação do código existente**.

Isso foi implementado por meio da interface:

```
ClienteValidation
```

Novas validações podem ser adicionadas simplesmente criando novas implementações:

```
class NovaValidacao implements ClienteValidation
```

Sem necessidade de alterar as classes existentes.

---

# Estratégia de Branches

O repositório foi organizado em três branches principais que representam a evolução do desenvolvimento.

### main

Branch principal contendo a versão consolidada da aplicação com arquitetura modular e aplicação dos princípios SOLID.

---

### testes

Branch utilizada para experimentação inicial e implementação de funcionalidades básicas.

Características:

- estrutura mais simples
- menor separação de responsabilidades

---

### testes-solid

Branch dedicada à implementação e experimentação dos princípios **SOLID**.

Principais mudanças:

- criação da interface `ClienteValidation`
- separação das validações em classes independentes
- introdução da camada `Service`

---

# Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- Maven

Ferramentas de desenvolvimento:

- Git
- Postman
- VS Code / IntelliJ

---

# Como Executar

Clone o repositório:

```bash
git clone https://github.com/ianlucasalmeida/Bootcamp_Java_Deloitte.git
```

Acesse o diretório da aplicação:

```bash
cd pecaja-api
```

Execute o projeto:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada em:

```
http://localhost:8080
```

---

# Endpoints Principais

### Clientes

```
GET /clientes
POST /clientes
PUT /clientes/{id}
DELETE /clientes/{id}
```

---

### Catálogo

```
GET /catalogo
```

---

### Vendas

```
POST /vendas
GET /vendas
```
