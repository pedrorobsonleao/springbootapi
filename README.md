# Springboot Api

This repositoy is a for of [wladimilson/springbootapi] written by [Wladimilson M. Nascimento].

It includes the [Dockerfile] and [docker-compose] files, and this document that show how to build and deploy a java application.

Please read the comments in the files [Dockerfile] and [docker-compose] for more informations. 

---

* [Criando uma API REST com o Spring Boot]


## build and run


[![asciicast]](https://asciinema.org/a/694412)

You need only [docker]  in your machine.
You dont need `javac` (JAVA JDK®) or `java` (JAVA Runtime®) to build or run the application.
### run application

`docker-compose up --build --detach`

```bash
[+] Building 91.4s (18/18) FINISHED                                                                          docker:default
 => [app internal] load build definition from Dockerfile                                                               0.1s
 => => transferring dockerfile: 2.21kB                                                                                 0.0s
 => [app internal] load metadata for docker.io/library/alpine:latest                                                   1.8s
 => [app internal] load metadata for docker.io/library/eclipse-temurin:23.0.1_11-jdk-alpine                            1.8s
 => [app auth] library/alpine:pull token for registry-1.docker.io                                                      0.0s
 => [app auth] library/eclipse-temurin:pull token for registry-1.docker.io                                             0.0s
 => [app internal] load .dockerignore                                                                                  0.0s
 => => transferring context: 112B                                                                                      0.0s
 => [app internal] load build context                                                                                  0.1s
 => => transferring context: 232.19kB                                                                                  0.1s
 => [app runtime 1/5] FROM docker.io/library/alpine:latest@sha256:21dc6063fd678b478f57c0e13f47560d0ea4eeba26dfc947b2a  0.0s
 => CACHED [app builder 1/4] FROM docker.io/library/eclipse-temurin:23.0.1_11-jdk-alpine@sha256:62f73af9ec69cc3056ef9  0.0s
 => [app builder 2/4] COPY . /app                                                                                      0.2s
 => [app builder 3/4] WORKDIR /app                                                                                     0.1s
 => [app builder 4/4] RUN apk add --no-cache binutils;     jlink     --verbose     --add-modules ALL-MODULE-PATH      86.9s
 => CACHED [app runtime 2/5] RUN addgroup --gid 1000 apps &&     adduser --no-create-home --disabled-password --ingro  0.0s
 => CACHED [app runtime 3/5] WORKDIR /app                                                                              0.0s
 => CACHED [app runtime 4/5] COPY --from=builder /minimal-jre /jre                                                     0.0s
 => [app runtime 5/5] COPY --from=builder app/target/springbootapi-0.0.1-SNAPSHOT.jar application.jar                  0.4s
 => [app] exporting to image                                                                                           0.4s
 => => exporting layers                                                                                                0.4s
 => => writing image sha256:fad0b5cd93f2892877229d1a5a78170ec537d284de8c6f24e553a710925a3097                           0.0s
 => => naming to docker.io/library/springbootapi-app                                                                   0.0s
 => [app] resolving provenance for metadata file                                                                       0.0s
[+] Running 5/5
 ✔ Network springbootapi_default    Created                                                                            0.1s
 ✔ Container springbootapi-mysql-1  Healthy                                                                           17.5s
 ✔ Container java_app               Started                                                                           17.9s
 ✔ Container dbadmin                Started                                                                           17.9s
 ✔ Container api_tests              Started                                                                           18.1s
```
---

After run the `docker-compose` command the application is started in your machine.

![stack]

After the last modifications the model has changed to run the api tests when the api applications is started.

#### newman test

This application use [Newman], a postman test scripts runner and the report is generated in directory [/newman/test].

![newman-report]

#### postman test

![postman-report]

To run te scripts locally, install and import the scripts in your local [Postman].

For more know how about the tests scripts, read the document [Test APIs and write scripts in Postman].

### local build

* `sdk env install` -  use [sdkman] to set java version to build.
* `./mvnw clean install` - built the project 
```bash
pleao@DESKTOP-N310CDO:~/Downloads/springbootapi (master)$ sdk env install

java 23.0.1-tem is already installed.

Using java version 23.0.1-tem in this shell.
pleao@DESKTOP-N310CDO:~/Downloads/springbootapi (master)$ mvn clean install
```

### run tests
* `./mvnw clean org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent  verify org.jacoco:jacoco-maven-plugin:0.8.12:report` - run tests and  generate [jacoco] report.

After a [local build] or [run tests] the  [jacoco] Reports is white  in [target/site/jacoco/]

#### jacoco report
![jacoco-report]

### swagger

* http://localhost:8080/swagger-ui/index.html
<!--
## access application
* http://localhost:8080/swagger-ui/index.html
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
-->

* [Pedro Robson Leão]
* [Diego Schmidt]🙏🏼

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