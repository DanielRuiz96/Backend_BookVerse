# Imagen base con JDK 17
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/ms-api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del gateway (8080)
EXPOSE 8080

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
