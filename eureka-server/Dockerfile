# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk

# Crea un directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR compilado desde el contexto al contenedor
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto del servidor Eureka
EXPOSE 8761

# Comando que ejecuta el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
