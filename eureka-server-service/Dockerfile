FROM openjdk:17
EXPOSE 8761
ADD target/eureka-server-service.jar eureka-server-service.jar
ENTRYPOINT ["java","-jar","eureka-server-service.jar"]