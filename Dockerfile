FROM eclipse-temurin:17

WORKDIR /app

COPY target/student-management-*.jar app.jar

EXPOSE 8889

ENTRYPOINT ["java", "-jar", "app.jar"]
