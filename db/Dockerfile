## Use the latest MySQL image
#FROM mysql:8.0
## Set the working directory
#WORKDIR /tmp
## Copy all the files to the working directory of the container
#COPY test_db/*.sql /tmp/
#COPY test_db/*.dump /tmp/
## Copy the main SQL file to docker-entrypoint-initdb.d.
## Scripts and SQL files in this folder are executed on container startup.
## This is specific to MySQL.
#COPY test_db/employees.sql /docker-entrypoint-initdb.d
## Set the root password
#ENV MYSQL_ROOT_PASSWORD example

FROM mysql/mysql-server:latest
WORKDIR /tmp
COPY test_db/*.sql /tmp/
COPY test_db/*.dump /tmp/
COPY test_db/employees.sql /docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD example
#Yet another Mac Fix
ENV MYSQL_ROOT_HOST=%