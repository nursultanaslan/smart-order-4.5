FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# Copy parent POM first (for better layer caching)
COPY pom.xml ./pom.xml

#copy cart-service module
COPY cart-service/pom.xml ./cart-service/pom.xml
COPY cart-service/src ./cart-service/src

# Build the application (skip tests for faster builds)
WORKDIR /build/cart-service
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Extract layered JAR (Spring Boot 2.3+)
WORKDIR /build/cart-service/target
RUN java -Djarmode=layertools -jar cart-service-*.jar extract

FROM eclipse-temurin:21-jre-alpine AS runtime

#Güvenlik için non-root user
RUN addgroup -S spring && adduser -S spring -G spring

# Çalışma klasörü
WORKDIR /app

# Layered jar extract ettiğimiz klasmanları kopyalayacagız ---
COPY --from=builder --chown=spring:spring /build/cart-service/target/dependencies/ ./
COPY --from=builder --chown=spring:spring /build/cart-service/target/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring /build/cart-service/target/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring /build/cart-service/target/application/ ./


# Switch to non-root user
USER spring:spring

# cart-service kaçtan dinliyor?
EXPOSE 8087

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
CMD wget -qO- http://localhost:8087/actuator/health || exit 1

# JVM Opts (container için en iyi ayarlar)
ENV JAVA_OPTS="\
 -XX:+UseContainerSupport \
 -XX:MaxRAMPercentage=75.0 \
 -XX:InitialRAMPercentage=50.0 \
 -XX:+ExitOnOutOfMemoryError \
 -Djava.security.egd=file:/dev/./urandom \
"

# ENTRYPOINT (JarLauncher ile çalışıyor.)
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS org.springframework.boot.loader.launch.JarLauncher"]