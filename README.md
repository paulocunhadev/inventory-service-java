# Stock Management API (Sistema de Controle de Estoque)

API RESTful para controle de estoque desenvolvida com **Java** e **Spring Boot**. O projeto foi estruturado com arquitetura em camadas, histórico imutável de movimentações, validações de domínio e configuração por variáveis de ambiente.

## Principais Funcionalidades

* **Cadastro e Consulta de Produtos:** Criação e listagem de produtos com validação de SKU único, preço unitário, saldo atual e estoque mínimo.

* **Histórico de Movimentações:** O saldo em estoque é atualizado exclusivamente por registros de entrada (`IN`) e saída (`OUT`), garantindo rastreabilidade e auditoria de todas as operações.

* **Bloqueio de Estoque Negativo:** Regras de domínio impedem baixas de estoque com quantidade superior ao saldo disponível.

* **Alerta de Estoque Baixo:** Consulta dedicada para identificar produtos com saldo igual ou inferior ao estoque mínimo configurado.

* **Contratos Limpos com DTOs:** Uso de Java `records` com Bean Validation (`jakarta.validation`) para desacoplar os payloads da API das entidades JPA.

## Tecnologias Utilizadas

* **Linguagem:** Java 17 (LTS)

* **Framework:** Spring Boot 4.2.0 (Spring Web, Spring Data JPA, Validation)

* **Banco de Dados:** MySQL 8+

* **Gerenciador de Build:** Gradle (Groovy DSL)

## Estrutura do Projeto

```
src/main/java/com/dmplace/estoque
├── controller   # Endpoints REST (entrada e saída HTTP)
├── domain       # Entidades JPA (Product, StockMovement) e enums (MovementType)
├── dto          # Records imutáveis para requisições e respostas
├── exception    # Exceções de negócio e tratamento global de erros
├── repository   # Repositórios Spring Data JPA
└── service      # Regras de negócio transacionais

```

## Como Executar

### Pré-requisitos

* **JDK 17** ou superior

* **MySQL 8+** em execução localmente ou via container Docker

### 1. Clonar o repositório

```
git clone https://github.com/paulobcunhadev/inventory-service-java.git
cd inventory-service-java

```

### 2. Configurar as variáveis de ambiente

Utilize o arquivo `.env.example` como referência e configure as variáveis no seu sistema operacional ou nas configurações de execução (*Run/Debug Configurations*) da sua IDE:

| Variável | Descrição | Valor Padrão | 
| ----- | ----- | ----- | 
| `DB_HOST` | Host do servidor MySQL | `localhost` | 
| `DB_PORT` | Porta do servidor MySQL | `3306` | 
| `DB_NAME` | Nome do banco de dados | `estoque_db` | 
| `DB_USERNAME` | Usuário de acesso ao banco | `usuario` | 
| `DB_PASSWORD` | Senha de acesso ao banco | *(obrigatório)* | 

### 3. Executar a aplicação

Via Gradle Wrapper (Linux/macOS):

```
./gradlew bootRun

```

No Windows:

```
gradlew.bat bootRun

```

A aplicação estará disponível em `http://localhost:8080`.

## Endpoints Planejados

| Método | Rota | Descrição | 
| ----- | ----- | ----- | 
| `POST` | `/api/products` | Cadastra um novo produto | 
| `GET` | `/api/products` | Lista todos os produtos cadastrados | 
| `GET` | `/api/products/{id}` | Busca um produto pelo ID | 
| `GET` | `/api/products/low-stock` | Lista produtos com saldo abaixo ou igual ao estoque mínimo | 
| `POST` | `/api/stock-movements` | Registra uma entrada (`IN`) ou saída (`OUT`) de estoque | 
| `GET` | `/api/stock-movements/product/{productId}` | Lista o histórico de movimentações de um produto | 

## Roadmap

* \[x\] Modelagem de domínio (`Product`, `StockMovement`, `MovementType`)

* \[x\] Repositórios, DTOs e serviços transacionais

* \[x\] Configuração de variáveis de ambiente

* \[ \] Controllers REST e tratamento global de exceções (`@RestControllerAdvice`)

* \[ \] Testes unitários e de integração (JUnit 5 e Mockito)

* \[ \] Documentação interativa com OpenAPI / Swagger UI

* \[ \] Containerização com Docker e Docker Compose

## Autor

Desenvolvido por **Paulo Brancalhão Cunha** — [GitHub](https://github.com/paulocunhadev)