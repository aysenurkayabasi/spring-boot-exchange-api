FROM openjdk:21-jdk

COPY target/openpayd-exchange-app.jar /openpayd-exchange-app.jar

ENTRYPOINT ["java", "-jar", "openpayd-exchange-app.jar"]