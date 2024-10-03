FROM openjdk:latest
COPY ./target/seMethods-0.1.0.4-jar-with-dependencies.jar /tmp
WORKDIR /tmp
# change last parameter to 10000 if running on github actions
#if running locally and database aleady started delay can be zero
ENTRYPOINT ["java", "-jar", "seMethods-0.1.0.4-jar-with-dependencies.jar", "employee:3306", "10000"]