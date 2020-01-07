FROM openjdk:8
COPY target1/com.michaelcgood-0.0.1.jar /com.michaelcgood-0.0.1.jar
EXPOSE 8081
CMD ["java", "-jar", "com.michaelcgood-0.0.1.jar"]
#FROM openjdk:8-windowsservercore
#ADD target/policies.jar policies.jar
#ENTRYPOINT ["java", "jar", "policies.jar"]