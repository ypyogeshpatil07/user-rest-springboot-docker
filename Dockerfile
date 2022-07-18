FROM openjdk:8
ADD build/libs/userapi-docker-0.0.1-SNAPSHOT.jar userapi-docker-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "userapi-docker-0.0.1-SNAPSHOT.jar"]