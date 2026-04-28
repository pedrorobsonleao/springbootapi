# Springboot API 🚀

Este projeto é uma **API REST moderna e de alto desempenho**, escrita em **Java 25 (LTS)** utilizando o ecossistema **Spring Boot 4**. O objetivo desta API é demonstrar as melhores práticas em desenvolvimento back-end, incluindo autenticação baseada em JWT, testes robustos automatizados com relatórios de cobertura (JaCoCo) e deployment simplificado em contêineres Docker usando Multi-stage builds.

## Índice
* [Sobre o Projeto](#sobre-o-projeto)
* [Arquitetura](#arquitetura)
* [Como Executar (Docker)](#como-executar-docker)
* [Como Desenvolver (Local)](#como-desenvolver-local)
* [Testes de Cobertura (JaCoCo)](#testes-de-cobertura-jacoco)
* [Swagger & Automação de Requests](#swagger--automação-de-requests)
* [Equipe](#equipe)

---

## Sobre o Projeto

A API gerencia informações de `Pessoa` através de um CRUD seguro. 

**Características Principais:**
- **Tecnologia Ponta:** Java 25 (Virtual Threads ativas + `SequencedCollections`) e Spring Boot 4.
- **Segurança (JWT):** Geração e validação de tokens JWT (RSA assinados) via `AuthController` e `TokenAuthenticationService`.
- **Validação e Banco de Dados:** JPA / Hibernate para banco de dados, com validação provida pelo `hibernate-validator` (Jakarta EE).
- **Testes Abrangentes:** Testes unitários em todos os endpoints (`PessoaControllerTest`, `AuthControllerTest`), mantendo a estabilidade.
- **Docker Multi-stage Build:** Um processo isolado e seguro no Docker que compreende etapas de base, testes automáticos, construção (com um JRE minimalista via `jlink`) e imagem leve final (`runtime`).

---

## Arquitetura

O diagrama abaixo detalha o fluxo de comunicação entre os clientes (Postman/Newman), a camada de segurança da API e a interação dos componentes internos para resolução da lógica de negócio.

```mermaid
sequenceDiagram
    participant Client as Client (Browser/Postman)
    participant Auth as AuthController
    participant TokenSvc as TokenAuthenticationService
    participant PessoaCtrl as PessoaController
    participant DB as MariaDB / H2
    
    %% Fluxo de Autenticação
    Client->>Auth: POST /login (username, password)
    Auth->>Auth: Valida Credenciais
    Auth->>TokenSvc: generateToken(Authentication)
    TokenSvc-->>Auth: Retorna Token JWT (RSA)
    Auth-->>Client: Retorna Token JWT
    
    %% Fluxo de Requisição Segura
    Client->>PessoaCtrl: GET /pessoa (Bearer Token)
    PessoaCtrl->>PessoaCtrl: Security Filter (Valida JWT)
    PessoaCtrl->>DB: Executa findAll() (Via JPA Repository)
    DB-->>PessoaCtrl: Retorna SequencedCollection<Pessoa>
    PessoaCtrl-->>Client: Retorna 200 OK (JSON)
```

---

## Como Executar (Docker)

[![asciicast]](https://asciinema.org/a/694412)

Você só precisa do [Docker](https://docs.docker.com/get-docker/) instalado. Não é necessário ter o JDK localmente para construir a aplicação, pois tudo é isolado através do `Dockerfile`.

Para provisionar o banco de dados (MariaDB) e rodar a aplicação:
```sh
docker-compose up --build
```
A API estará pronta para receber requisições em `http://localhost:8080`.

*(Veja as configurações de banco em `docker-compose.yml`)*.

---

## Como Desenvolver (Local)

Para rodar a aplicação no seu ambiente (necessário Java 25 configurado, de preferência usando o [SDKMAN](https://sdkman.io/)):

1.  Baixe e compile o projeto (pulando testes no primeiro empacotamento, se preferir):
    ```sh
    ./mvnw clean package -DskipTests
    ```
2. Inicie a aplicação via Maven (ou rode o artefato `target/springbootapi-0.0.1-SNAPSHOT.jar`):
    ```sh
    ./mvnw spring-boot:run
    ```

---

## Testes e Cobertura (JaCoCo)

Os testes podem ser rodados sem depender do banco de dados na infra (utiliza-se H2 em memória). A cobertura de código atual atende as classes principais e componentes de autenticação.

Para executar localmente e gerar os relatórios do **JaCoCo**:
```sh
./mvnw clean test jacoco:report
```

O relatório em formato HTML ficará disponível no seu diretório `target`:
```
target/site/jacoco/index.html
```

*(No Docker, implementamos o **Test Stage**: O projeto possui um target chamado `test` no Dockerfile que quebra o build automaticamente se os testes falharem)*.
```sh
docker build --target test -t springbootapi-test .
```

---

## Swagger & Automação de Requests

### Swagger UI
A API descreve-se automaticamente na rota padrão do Swagger:
```
http://localhost:8080/swagger-ui/index.html
```
Na documentação você pode visualizar todas as rotas de `Pessoa` e o modelo de dados de resposta.

### Automação via Newman (Postman)
Para rodar os fluxos automáticos da coleção com o Newman no Docker e simular testes E2E sobre a API conteinerizada:
```sh
docker-compose run --rm newman
```
Os relatórios finais da execução de ponta-a-ponta ficarão registrados na pasta `newman/tests/newman/`.

---

## Equipe
- **Wladimilson M. Nascimento** *(Autor original e idealização)*
- **Pedro Robson Leão** *(Manutenção, migração e atualização arquitetural)*
