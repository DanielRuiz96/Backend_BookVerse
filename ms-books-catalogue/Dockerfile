# Usar imagen base con JDK 17
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/ms-books-catalogue-0.0.1-SNAPSHOT.jar app.jar

# Puerto en el que corre la app 
EXPOSE 8081

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
