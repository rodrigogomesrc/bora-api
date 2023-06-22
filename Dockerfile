FROM openjdk
WORKDIR /opt/app
COPY . .
RUN ./mvnw -ntp verify -DskipTests --batch-mode -Pdev
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/bora-0.0.1-SNAPSHOT.jar"]