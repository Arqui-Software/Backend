# Usa una imagen base de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y las dependencias
COPY pom.xml .

# Descarga las dependencias del proyecto
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src ./src

# Compila el proyecto y empaca en un JAR
RUN mvn clean package -DskipTests

# Usa una imagen base más ligera para ejecutar la aplicación
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el JAR desde la etapa de construcción
COPY --from=build /app/target/petwellnes-backend-1.0.0.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]