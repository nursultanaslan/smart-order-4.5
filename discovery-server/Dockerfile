FROM maven:3.9-eclipse-temurin-21-alpine AS builder

# Set working directory
WORKDIR /build

# Copy parent POM first (for better layer caching)
COPY pom.xml ./pom.xml

#copy discovery-server module
COPY discovery-server/pom.xml ./discovery-server/pom.xml
COPY discovery-server/src ./discovery-server/src

# Build the application (skip tests for faster builds)
WORKDIR /build/discovery-server
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Extract layered JAR (Spring Boot 2.3+)
WORKDIR /build/discovery-server/target
RUN java -Djarmode=layertools -jar discovery-server-*.jar extract

FROM eclipse-temurin:21-jre-alpine AS runtime

#Güvenlik için non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Çalışma klasörü
WORKDIR /app

# Layered jar extract ettiğimiz klasmanları kopyalayacagız ---
COPY --from=builder --chown=spring:spring /build/discovery-server/target/dependencies/ ./
COPY --from=builder --chown=spring:spring /build/discovery-server/target/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /build/discovery-server/target/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /build/discovery-server/target/application/ ./

# Switch to non-root user
USER spring:spring

# discovery-server kaçtan dinliyor?
EXPOSE 8761

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
CMD wget -qO- http://localhost:8761/actuator/health || exit 1

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