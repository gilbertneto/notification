FROM openjdk:23-jdk

WORKDIR /app

COPY build/libs/notification-0.0.1-SNAPSHOT.jar /app/notification.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/notification.jar"]