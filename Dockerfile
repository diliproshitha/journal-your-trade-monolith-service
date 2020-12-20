# use Alpine Linux for build stage
FROM alpine:3.10.1 as build

# install build dependencies
RUN apk --no-cache add openjdk11
RUN apk --no-cache add maven

# build JDK with less modules
RUN /usr/lib/jvm/default-jvm/bin/jlink \
    --compress=2 \
    --module-path /usr/lib/jvm/default-jvm/jmods \
    --add-modules java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.instrument \
    --output /jdk-minimal

# fetch maven dependencies
WORKDIR /build
COPY pom.xml pom.xml
RUN mvn dependency:go-offline

# build
COPY src src
RUN mvn clean package -Dmaven.test.skip=true

# prepare a fresh Alpine Linux with JDK
FROM alpine:3.10.1

# get result from build stage
COPY --from=build /jdk-minimal /opt/jdk/
COPY --from=build /build/target/*.jar /app.jar
VOLUME /tmp
EXPOSE 8080
CMD /opt/jdk/bin/java -jar /app.jar --spring.config.location=file:/config/application.properties