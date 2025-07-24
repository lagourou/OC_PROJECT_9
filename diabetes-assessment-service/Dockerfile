FROM openjdk:26-oraclelinux9

WORKDIR /app

COPY target/diabetes-assessment-service-0.0.1-SNAPSHOT.jar /app/medilabo.jar

EXPOSE 8083

ENTRYPOINT [ "java", "-jar", "/app/medilabo.jar" ]
