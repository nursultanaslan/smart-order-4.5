FROM maven:3.9-eclipse-temurin-21 AS builder

#proje -> run
WORKDIR /build

COPY pom.xml ./

RUN mvn -DskipTests dependency:purge-local-repository

COPY . .

RUN mvn -U clean package -DskipTests

FROM eclipse-temurin:21-jre AS runtime

COPY --from=builder /build/target/*-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]