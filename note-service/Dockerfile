FROM openjdk:26-oraclelinux9

WORKDIR /app

COPY target/note-service-0.0.1-SNAPSHOT.jar /app/medilabo.jar

EXPOSE 8082

ENTRYPOINT [ "java", "-jar", "/app/medilabo.jar" ]
