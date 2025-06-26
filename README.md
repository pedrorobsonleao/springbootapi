# Springboot Api

## Índice
* [Build and Run](#build-and-run)
    * [Run Application](#run-application)
        * [Newman Test](#newman-test)
        * [Postman Test](#postman-test)
* [Local Build](#local-build)
    * [Run Tests](#run-tests)
        * [Jacoco Report](#jacoco-report)
* [Swagger](#swagger)
* [Team](#team)

---
Este repositório é um fork de [wladimilson/springbootapi] escrito por [Wladimilson M. Nascimento].

Inclui os arquivos [Dockerfile] e [docker-compose], além deste documento que mostra como construir e implantar uma aplicação Java.

Leia os comentários nos arquivos [Dockerfile] e [docker-compose] para mais informações.

---

* [Criando uma API REST com o Spring Boot]

## Sobre o Projeto

Esta aplicação é uma API REST desenvolvida com Spring Boot, utilizando autenticação JWT, documentação automática com Swagger/OpenAPI, testes automatizados (JUnit, Mockito) e relatório de cobertura de testes com Jacoco. O projeto está pronto para execução em ambiente local ou via Docker.

### Principais Funcionalidades
- CRUD de pessoas (entidade Pessoa)
- Autenticação JWT
- Documentação Swagger
- Testes automatizados e cobertura Jacoco
- Pronto para Docker

## Build and Run

[![asciicast]](https://asciinema.org/a/694412)

Você precisa apenas do [docker] em sua máquina.
Não é necessário `javac` (JAVA JDK®) ou `java` (JAVA Runtime®) para buildar ou rodar a aplicação.

### Run Application

Para rodar a aplicação com Docker:

```sh
docker-compose up --build
```

A aplicação estará disponível em http://localhost:8080

### Newman Test

Testes automatizados podem ser executados com o Newman (CLI do Postman):

```sh
docker-compose run --rm newman
```

Os relatórios serão gerados na pasta `newman/tests/newman/`.

### Postman Test

Você pode importar a coleção Postman localizada em `newman/tests/simple_spring_boot_rest_api.postman_collection.json` e os ambientes para testar manualmente.

## Local Build

Para buildar localmente:

```sh
./mvnw clean package
```

### Run Tests

Para rodar os testes automatizados:

```sh
./mvnw test
```

#### Jacoco Report

Após rodar os testes, o relatório de cobertura Jacoco estará disponível em:

```
target/site/jacoco/index.html
```

Veja a imagem de exemplo em `img/jacoco-report.png`.

## Swagger

A documentação interativa da API está disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

Veja a imagem de exemplo em `img/swagger.png`.

## Team

- [Wladimilson M. Nascimento] (autor original)
- [Pedro Robson Leão] (atualizações e manutenção)

---

> Para dúvidas, sugestões ou contribuições, abra uma issue ou envie um pull request.


[sdkman]:https://sdkman.io/
[Pedro Robson Leão]:mailto:pedro.leao@gmail.com
[Diego Schmidt]:dceschmidt@gmail.com
[Newman]:https://learning.postman.com/docs/collections/using-newman-cli/newman-options/
[wladimilson/springbootapi]:https://github.com/wladimilson/springbootapi
[Wladimilson M. Nascimento]:https://www.treinaweb.com.br/blog/autor/wladimilson-m-nascimento
[Dockerfile]:Dockerfile
[docker-compose]:docker-compose.yml
[Criando uma API REST com o Spring Boot]:https://www.treinaweb.com.br/blog/criando-uma-api-rest-com-o-spring-boot
[asciicast]:https://asciinema.org/a/694412.svg
[docker]:https://docs.docker.com/get-docker/
[stack]:./img/docker-compose.png
[/newman/test]:./newman/test/
[Postman]:https://www.postman.com/downloads/
[Test APIs and write scripts in Postman]:https://learning.postman.com/docs/tests-and-scripts/tests-and-scripts/
[jacoco]:https://www.jacoco.org/jacoco/
[local build]:#local-build
[run tests]:#run-tests
[target/site/jacoco/]:.target/site/jacoco/
[jacoco-report]:./img/jacoco-report.png
[newman-report]:./img/newman-report.png
[postman-report]:./img/postman-report.png
[swagger]:./img/swagger.png