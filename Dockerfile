
# docker images




# =========================
# Stage 1: Build
# =========================
# gradle --version 
FROM gradle:jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copiar el JAR generado
COPY --from=build /app/build/libs/*.jar app.jar

# Puerto estándar para contenedores / Render
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
