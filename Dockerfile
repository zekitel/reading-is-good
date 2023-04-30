FROM openjdk:17-oracle
ARG JAR_FILE=target/reading-is-good.jar
WORKDIR /opt/app
COPY target/readingisgood-0.0.1-SNAPSHOT.jar /reading-is-good-docker.jar
ENTRYPOINT ["java","-jar","/reading-is-good-docker.jar"]