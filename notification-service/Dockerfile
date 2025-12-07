FROM maven:3.9-eclipse-temurin-21-alpine AS builder

# Set working directory
WORKDIR /build

# Copy parent POM first (for better layer caching)
COPY pom.xml ./pom.xml

#copy notification-service module
COPY notification-service/pom.xml ./notification-service/pom.xml
COPY notification-service/src ./notification-service/src

# Build the application (skip tests for faster builds)
WORKDIR /build/notification-service
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Extract layered JAR (Spring Boot 2.3+)
WORKDIR /build/notification-service/target
RUN java -Djarmode=layertools -jar notification-service-*.jar extract

FROM eclipse-temurin:21-jre-alpine AS runtime

#Güvenlik için non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Çalışma klasörü
WORKDIR /app

# Layered jar extract ettiğimiz klasmanları kopyalayacagız ---
COPY --from=builder --chown=spring:spring /build/notification-service/target/dependencies/ ./
COPY --from=builder --chown=spring:spring /build/notification-service/target/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /build/notification-service/target/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /build/notification-service/target/application/ ./

# Switch to non-root user
USER spring:spring

# notification-service kaçtan dinliyor?
EXPOSE 8082

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
CMD wget -qO- http://localhost:8082/actuator/health || exit 1

# JVM Opts (container için en iyi ayarlar)
ENV JAVA_OPTS="\
 -XX:+UseContainerSupport \
 -XX:MaxRAMPercentage=75.0 \
 -XX:InitialRAMPercentage=50.0 \
 -XX:+ExitOnOutOfMemoryError \
 -Djava.security.egd=file:/dev/./urandom \
"

# ENTRYPOINT (JarLauncher ile çalışıyor. layered jar kullandıgımız icin.)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]