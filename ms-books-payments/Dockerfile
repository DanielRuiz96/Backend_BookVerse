# Imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado al contenedor
COPY target/ms-books-payments-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre tu microservicio
EXPOSE 8082

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
