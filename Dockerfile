FROM gradle:9.1.1-jdk23 as build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

FROM openjdk:23-jdk

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/notification.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/notification.jar"]