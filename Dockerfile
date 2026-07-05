# ─── Stage 1: build ───────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copia pom.xml separado para cachear dependências
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copia o código e compila
COPY src ./src
RUN mvn package -DskipTests -q

# ─── Stage 2: runtime ─────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Usuário sem privilégios
RUN addgroup -S spring && adduser -S spring -G spring

# Copia o jar do stage de build (não depende de mvn local)
COPY --from=build /app/target/*.jar app.jar

# Permissão correta antes de trocar de usuário
RUN chown spring:spring app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]