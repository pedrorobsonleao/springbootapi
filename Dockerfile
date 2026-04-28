# origin image to build application
FROM eclipse-temurin:25-jdk AS builder

# copy source to docker build environment
COPY . /app

# create app dir to build application
WORKDIR /app

# run commands
RUN jlink \
    --verbose \
    --add-modules java.base,java.compiler,java.desktop,java.instrument,java.management,java.naming,java.prefs,java.rmi,java.scripting,java.security.jgss,java.sql,jdk.jcmd,jdk.jdwp.agent,jdk.unsupported \
    --compress=2 \
    --no-header-files \
    --no-man-pages \
    --strip-debug \
    --output /minimal-jre; \
    sed -i 's/\r$//' mvnw; \
    ./mvnw clean package -DskipTests

# origin image to runtime application image
FROM ubuntu:24.04 AS runtime

# maintainer name
LABEL authors="Pedro Robson Leão <pedro.leao@gmail.com>"

# environment variable with appname
ENV APP=springbootapi-0.0.1-SNAPSHOT.jar
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Add app user.
ARG USER_NAME="appuser"
ARG USER_ID="1001"
ARG GROUP_NAME="apps"
ARG GROUP_ID="1001"
# Configure work directory.
ARG APP_DIR=/app
ARG SPRING_PROFILES_ACTIVE=local

RUN groupadd --gid ${GROUP_ID} ${GROUP_NAME} && \
    useradd --no-create-home --gid ${GROUP_ID} --uid ${USER_ID} ${USER_NAME} && \
    mkdir ${APP_DIR} && \
    chown -R ${USER_NAME}:${GROUP_NAME} ${APP_DIR} && \
    echo 'echo "Java Opts=${JAVA_OPTS}" && \
      java ${JAVA_OPTS} \
      --add-opens java.base/java.time=ALL-UNNAMED \
      -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=localhost:6000 -Duser.timezone=UTC \
      -DjsonLogsEnabled=true \
      -Djava.security.egd=file:/dev/./urandom \
      -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
      -XshowSettings:vm \
      -XX:MaxRAMPercentage=90 \
      -jar application.jar' >  ${APP_DIR}/entrypoint.sh

# crate app workdir to runtime application
WORKDIR ${APP_DIR}

# Copy downsized JRE from builder image.
COPY --from=builder /minimal-jre $JAVA_HOME

# copy build jar file from build image
COPY --from=builder app/target/${APP} application.jar

# expose http access application port
EXPOSE 8080

# run application when run container
USER ${USER_NAME}:${GROUP_NAME}

ENV JSON_LOGS_ENABLED=true
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["sh", "entrypoint.sh"]