FROM openjdk:26-oraclelinux9

WORKDIR /app

COPY target/patient-service-0.0.1-SNAPSHOT.jar /app/medilabo.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "/app/medilabo.jar" ]

