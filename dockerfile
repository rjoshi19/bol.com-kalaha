FROM openjdk:21-jdk

ADD kalaha-web/target/kalaha-web-0.0.1-SNAPSHOT.jar kalaha.jar
ENTRYPOINT [ "sh", "-c", "java -jar /kalaha.jar" ]