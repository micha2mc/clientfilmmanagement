FROM openjdk:21-slim
EXPOSE 9000
ADD target/clientfilmmanagement-0.0.1.jar client-service.jar
ENTRYPOINT ["java","-jar","/client-service.jar"]