FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw package -DskipTests

EXPOSE 8083

CMD ["java", "-jar", "target/diabetes-assessment-service-0.0.1-SNAPSHOT.jar"]
