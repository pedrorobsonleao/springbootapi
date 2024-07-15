# Springboot Api

This sample is based in [wladimilson/springbootapi](https://github.com/wladimilson/springbootapi) write from [Wladimilson M. Nascimento](https://www.treinaweb.com.br/blog/autor/wladimilson-m-nascimento)

I included in project the files [Dockerfile](Dockerfile) and [docker-compose](docker-compose.yml) and show in this document how to build java application and deploy the infrastructure.

Please read the comments in the files [Dockerfile](Dockerfile) and [docker-compose](docker-compose.yml) to more informations. 

---

* [Criando uma API REST com o Spring Boot](https://www.treinaweb.com.br/blog/criando-uma-api-rest-com-o-spring-boot)

## build and run
You need only [docker](https://docs.docker.com/get-docker/)  in your machine.
You dont need `javac` (JAVA JDK®) or `java` (JAVA Runtime®) to build or run the application.
### run application
`docker-compose up --build`
### run application without log
`docker-compose up --build --detach`

---

After run the `docker-compose` command the application is started in your machine.

![stack](./img/docker-compose.png)

After the last modifications the model has changed to run the api tests when the api applications is started.

I'm use [Newman](https://learning.postman.com/docs/collections/using-newman-cli/newman-options/), a postman test scripts runner and the report is generated in directory [/newman/test](/newman/test/)

To run te scripts locally, install and import the scripts in your local [Postman](https://www.postman.com/downloads/).

For more know how about the tests scripts, read the document [Test APIs and write scripts in Postman](https://learning.postman.com/docs/tests-and-scripts/tests-and-scripts/).

## access application
* http://localhost:8080/swagger-ui.html
### test api
#### get token
```bash
$ curl http://localhost:8080/login  -H 'Content-Type: application/json' -d '{"username":"admin", "password":"password"}' --silent | jq '.token' | xargs printf "Bearer %s\n"
```

#### write name
```bash
$ curl -X POST "http://localhost:8080/pessoa" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMzg4MDg1M30.nCBiANNApoRGOUTRQKc59RAHnMVPzT7krW-U9Zv_ZX9eVH9WeAoZio4gE56ceOv59MjO5OVYKsXVuAe8fVnpcA" -H "Content-Type: application/json" -d "{ \"id\": 0, \"nome\": \"Pedro\"}"
```
#### read all names
```bash
$ curl -X GET "http://localhost:8080/pessoa" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMzg4MDg1M30.nCBiANNApoRGOUTRQKc59RAHnMVPzT7krW-U9Zv_ZX9eVH9WeAoZio4gE56ceOv59MjO5OVYKsXVuAe8fVnpcA" -H "Content-Type: application/json"
```
#### read one name
```bash
curl -X GET "http://localhost:8080/pessoa/1" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMzg4MDg1M30.nCBiANNApoRGOUTRQKc59RAHnMVPzT7krW-U9Zv_ZX9eVH9WeAoZio4gE56ceOv59MjO5OVYKsXVuAe8fVnpcA" -H "Content-Type: application/json"
```
#### update name
```bash
$ curl -X PUT "http://localhost:8080/pessoa/1" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzMzg4MDg1M30.nCBiANNApoRGOUTRQKc59RAHnMVPzT7krW-U9Zv_ZX9eVH9WeAoZio4gE56ceOv59MjO5OVYKsXVuAe8fVnpcA" -H "Content-Type: application/json" -d "{ \"id\": 0, \"nome\": \"Pedro Robson Leão\"}"
```

[Pedro Robson Leão](mailto:pedro.leao@gmail.com)
